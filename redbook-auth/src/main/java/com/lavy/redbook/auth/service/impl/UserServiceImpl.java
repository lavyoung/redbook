package com.lavy.redbook.auth.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.lavy.redbook.auth.constant.RedisKeyConstants;
import com.lavy.redbook.auth.constant.RoleConstants;
import com.lavy.redbook.auth.domain.dataobject.RoleDO;
import com.lavy.redbook.auth.domain.dataobject.UserDO;
import com.lavy.redbook.auth.domain.dataobject.UserRoleRelDO;
import com.lavy.redbook.auth.domain.mapper.RoleDOMapper;
import com.lavy.redbook.auth.domain.mapper.UserDOMapper;
import com.lavy.redbook.auth.domain.mapper.UserRoleRelDOMapper;
import com.lavy.redbook.auth.enums.LoginTypeEnum;
import com.lavy.redbook.auth.enums.ResponseCodeEnum;
import com.lavy.redbook.auth.model.vo.user.UpdatePasswordReqVO;
import com.lavy.redbook.auth.model.vo.user.UserLoginReqVO;
import com.lavy.redbook.auth.service.UserService;
import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.eumns.DeletedEnum;
import com.lavy.redbook.framework.common.eumns.StatusEnum;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.framework.common.util.JsonUtils;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDOMapper, UserDO> implements UserService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserRoleRelDOMapper userRoleRelDOMapper;
    @Resource
    private RoleDOMapper roleDOMapper;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;
    /**
     * 用户登录/注册
     *
     * @param reqVO 请求参数
     * @return 响应结果
     */
    @Override
    public Response<?> doLogin(UserLoginReqVO reqVO) {
        log.info("用户登录开始，入参：{}", reqVO);
        String phone = reqVO.getPhone();
        Long userId = null;
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getByValue(reqVO.getType());
        Assert.notNull(loginTypeEnum, "登录类型不存在");
        switch (loginTypeEnum) {
            case PHONE -> {
                String voCode = reqVO.getCode();
                Preconditions.checkArgument(!StringUtils.isBlank(voCode),
                        ResponseCodeEnum.PARAM_NOT_VALID.getErrorMessage());
                String key = RedisKeyConstants.buildVerificationCodeKey(phone);
                Object object = redisTemplate.opsForValue().get(key);
                if (object == null) {
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_NOT_EXIST);
                }
                String code = object.toString();
                if (!code.equals(reqVO.getCode())) {
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }
                UserDO userDO = this.baseMapper.selectByPhone(phone);

                log.info("用户信息： 手机号：{}， 用户：{}", phone, userDO);
                if (userDO == null) {
                    // 自动注册
                    userId = this.register(phone);
                } else {
                    // 登录
                    this.pushUserRoles(userDO.getId());
                    userId = userDO.getId();
                }
                redisTemplate.delete(key);
                break;
            }
            case PASSWORD -> {
                String password = reqVO.getPassword();
                UserDO userDO = this.baseMapper.selectByPhone(phone);
                // 手机号是否注册
                if (userDO == null) {
                    return Response.fail(ResponseCodeEnum.USER_NOT_EXIST);
                }
                boolean matches = passwordEncoder.matches(password, userDO.getPassword());
                // 密码是否正确
                if (!matches) {
                    return Response.fail(ResponseCodeEnum.USER_PASSWORD_ERROR);
                }
                userId = userDO.getId();
            }
            default -> throw new BizException(ResponseCodeEnum.PARAM_NOT_VALID);
        }
        // SaToken 登录
        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 返回 token
        return Response.success(tokenInfo.tokenValue);
    }

    @Override
    public Response<?> logout() {
        StpUtil.logout(LoginUserContextHolder.getUserId());
        return Response.success();
    }

    @Override
    public Response<?> updatePassword(UpdatePasswordReqVO reqVO) {
        Long userId = LoginUserContextHolder.getUserId();
        String newPassword = reqVO.getNewPassword();
        String encode = passwordEncoder.encode(newPassword);
        this.baseMapper.updateById(
                UserDO.builder().id(userId).password(encode).updateTime(LocalDateTime.now()).build());
        return Response.success();
    }

    /**
     * 添加用户角色到Redis
     *
     * @param userId 用户 ID
     */
    private void pushUserRoles(Long userId) {
        String userRoleKey = RedisKeyConstants.buildUserRoleKey(userId);
        if (!redisTemplate.hasKey(userRoleKey)) {
            List<UserRoleRelDO> userRoleRelDOS = this.userRoleRelDOMapper.selectListByUserId(userId);
            if (userRoleRelDOS != null && !userRoleRelDOS.isEmpty()) {
                List<Long> roleIds = userRoleRelDOS.stream().map(UserRoleRelDO::getRoleId).toList();
                List<RoleDO> roleDOList = this.roleDOMapper.selectByIds(roleIds);
                if (roleDOList != null && !roleDOList.isEmpty()) {
                    List<String> roleKeys = roleDOList.stream().map(RoleDO::getRoleKey).toList();
                    redisTemplate.opsForValue().set(userRoleKey, JsonUtils.toJsonString(roleKeys));
                }
            }
        }
    }

    /**
     * 用户注册
     *
     * @param phone 手机号
     * @return 注册成功后的用户 ID
     */
    public Long register(String phone) {
        return transactionTemplate.execute(status -> {
            try {
                Long redbookUserId = redisTemplate.opsForValue().increment(RedisKeyConstants.REDBOOK_ID_GENERATOR_KEY);
                UserDO userDO = UserDO.builder()
                        .redbookId(String.valueOf(redbookUserId))
                        .phone(phone)
                        .nickname("momo" + redbookUserId)
                        .status(StatusEnum.ENABLED.getValue())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .isDeleted(DeletedEnum.NO.getValue())
                        .build();
                // 插入用户
                this.baseMapper.insert(userDO);
                // 插入用户角色关系
                UserRoleRelDO roleRelDO = UserRoleRelDO.builder()
                        .userId(userDO.getId())
                        .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .isDeleted(DeletedEnum.NO.getValue())
                        .build();
                this.userRoleRelDOMapper.insert(roleRelDO);
                RoleDO roleDO = roleDOMapper.selectByPrimaryKey(RoleConstants.COMMON_USER_ROLE_ID);

                // 添加用户角色到Redis
                List<String> roles = new ArrayList<>(1);
                roles.add(roleDO.getRoleKey());
                String userRoleKey = RedisKeyConstants.buildUserRoleKey(userDO.getId());
                redisTemplate.opsForValue().set(userRoleKey, JsonUtils.toJsonString(roles));
                return userDO.getId();
            } catch (Exception e) {
                log.error("系统注册用户异常", e);
                status.setRollbackOnly();
                return null;
            }
        });
    }


}

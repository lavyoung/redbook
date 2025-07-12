package com.lavy.redbook.auth.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.lavy.redbook.auth.constant.RedisKeyConstants;
import com.lavy.redbook.auth.constant.RoleConstants;
import com.lavy.redbook.auth.domain.dataobject.UserDO;
import com.lavy.redbook.auth.domain.dataobject.UserRoleRelDO;
import com.lavy.redbook.auth.domain.mapper.UserDOMapper;
import com.lavy.redbook.auth.domain.mapper.UserRoleRelDOMapper;
import com.lavy.redbook.auth.enums.LoginTypeEnum;
import com.lavy.redbook.auth.enums.ResponseCodeEnum;
import com.lavy.redbook.auth.model.vo.user.UserLoginReqVO;
import com.lavy.redbook.auth.service.UserService;
import com.lavy.redbook.framework.common.eumns.DeletedEnum;
import com.lavy.redbook.framework.common.eumns.StatusEnum;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.framework.common.util.JsonUtils;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDOMapper, UserDO> implements UserService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRoleRelDOMapper userRoleRelDOMapper;

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
                if (StringUtils.isBlank(voCode)) {
                    return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID);
                }
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
                    userId = userDO.getId();
                }
                redisTemplate.delete(key);
                break;
            }
            case PASSWORD -> {
                // todo
            }
            default -> {
                throw new BizException(ResponseCodeEnum.PARAM_NOT_VALID);
            }
        }
        // SaToken 登录
        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 返回 token
        return Response.success(tokenInfo.tokenValue);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long register(String phone) {
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

        // 添加用户角色到Redis
        List<Long> roles = Lists.newArrayList();
        roles.add(RoleConstants.COMMON_USER_ROLE_ID);
        String userRoleKey = RedisKeyConstants.buildUserRoleKey(phone);
        redisTemplate.opsForValue().set(userRoleKey, JsonUtils.toJsonString(roles));
        return userDO.getId();
    }

}

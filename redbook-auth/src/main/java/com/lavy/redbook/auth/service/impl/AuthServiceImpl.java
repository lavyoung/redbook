package com.lavy.redbook.auth.service.impl;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.lavy.redbook.auth.constant.RedisKeyConstants;
import com.lavy.redbook.auth.domain.dataobject.UserDO;
import com.lavy.redbook.auth.domain.mapper.UserDOMapper;
import com.lavy.redbook.auth.enums.LoginTypeEnum;
import com.lavy.redbook.auth.enums.ResponseCodeEnum;
import com.lavy.redbook.auth.model.vo.user.UpdatePasswordReqVO;
import com.lavy.redbook.auth.model.vo.user.UserLoginReqVO;
import com.lavy.redbook.auth.rpc.UserRpcService;
import com.lavy.redbook.auth.service.AuthService;
import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.user.api.dto.resp.FindUserByPhoneRspDTO;

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
public class AuthServiceImpl extends ServiceImpl<UserDOMapper, UserDO> implements AuthService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRpcService userRpcService;

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
                    userId = userRpcService.registerUser(phone);
                    // 若调用用户服务，返回的用户 ID 为空，则提示登录失败
                    if (Objects.isNull(userId)) {
                        throw new BizException(ResponseCodeEnum.LOGIN_FAIL);
                    }
                } else {
                    // 登录
                    userRpcService.pushUserRoles(userDO.getId());
                    userId = userDO.getId();
                }
                redisTemplate.delete(key);
                break;
            }
            case PASSWORD -> {
                String password = reqVO.getPassword();
                FindUserByPhoneRspDTO user = userRpcService.findUserByPhone(phone);
                // 手机号是否注册
                Preconditions.checkArgument(user != null, ResponseCodeEnum.USER_NOT_EXIST.getErrorMessage());
                boolean matches = passwordEncoder.matches(password, user.getPassword());
                // 密码是否正确
                if (!matches) {
                    return Response.fail(ResponseCodeEnum.USER_PASSWORD_ERROR);
                }
                userId = user.getId();
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
        userRpcService.updatePassword(reqVO.getNewPassword());
        return Response.success();
    }



}

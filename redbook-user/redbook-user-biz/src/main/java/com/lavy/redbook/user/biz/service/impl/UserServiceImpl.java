package com.lavy.redbook.user.biz.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.framework.common.util.ParamUtils;
import com.lavy.redbook.user.biz.domain.dataobject.UserDO;
import com.lavy.redbook.user.biz.domain.mapper.UserDOMapper;
import com.lavy.redbook.user.biz.enums.ResponseCodeEnum;
import com.lavy.redbook.user.biz.enums.SexEnum;
import com.lavy.redbook.user.biz.model.vo.UpdateUserInfoReqVO;
import com.lavy.redbook.user.biz.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDOMapper, UserDO> implements UserService {


    /**
     * 更新用户信息
     *
     * @param updateUserInfoReqVO 更新用户信息请求参数
     * @return 更新用户信息结果
     */
    @Override
    public Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO) {
        UserDO userDO = new UserDO();
        userDO.setId(LoginUserContextHolder.getUserId());
        boolean needUpdate = false;

        // 头像
        MultipartFile avatar = updateUserInfoReqVO.getAvatar();
        if (avatar != null) {
            // todo 上传头像
        }
        // 昵称
        String nickname = updateUserInfoReqVO.getNickname();
        if (StringUtils.isNotBlank(nickname)) {
            Preconditions.checkArgument(ParamUtils.checkNickname(nickname),
                    ResponseCodeEnum.NICK_NAME_VALID_FAIL.getErrorMessage());
            userDO.setNickname(nickname);
            needUpdate = true;
        }
        // 书号
        String redbookId = updateUserInfoReqVO.getRedbookId();
        if (StringUtils.isNotBlank(redbookId)) {
            Preconditions.checkArgument(ParamUtils.isValidRedbookId(redbookId),
                    ResponseCodeEnum.REDBOOK_ID_VALID_FAIL.getErrorMessage());
            userDO.setRedbookId(redbookId);
            needUpdate = true;
        }
        // 性别
        Integer sex = updateUserInfoReqVO.getSex();
        if (sex != null) {
            Preconditions.checkArgument(SexEnum.isValid(sex),
                    ResponseCodeEnum.INTRODUCTION_VALID_FAIL.getErrorMessage());
            userDO.setSex(sex);
            needUpdate = true;
        }
        LocalDate birthday = updateUserInfoReqVO.getBirthday();
        if (birthday != null) {
            userDO.setBirthday(birthday);
            needUpdate = true;
        }
        // 个人简介
        String introduction = updateUserInfoReqVO.getIntroduction();
        if (StringUtils.isNotBlank(introduction)) {
            Preconditions.checkArgument(ParamUtils.checkStringLength(introduction, 0, 100),
                    ResponseCodeEnum.INTRODUCTION_VALID_FAIL.getErrorMessage());
            userDO.setIntroduction(introduction);
            needUpdate = true;
        }
        if (needUpdate) {
            // 更新用户信息
            userDO.setUpdateTime(LocalDateTime.now());
            this.baseMapper.updateByPrimaryKeySelective(userDO);
        }
        return Response.success();
    }
}

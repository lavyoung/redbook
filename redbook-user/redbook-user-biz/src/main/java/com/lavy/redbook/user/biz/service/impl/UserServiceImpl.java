package com.lavy.redbook.user.biz.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Preconditions;
import com.lavy.redbook.framework.biz.context.holder.LoginUserContextHolder;
import com.lavy.redbook.framework.common.eumns.DeletedEnum;
import com.lavy.redbook.framework.common.eumns.StatusEnum;
import com.lavy.redbook.framework.common.exception.BizException;
import com.lavy.redbook.framework.common.response.Response;
import com.lavy.redbook.framework.common.util.JsonUtils;
import com.lavy.redbook.framework.common.util.ParamUtils;
import com.lavy.redbook.user.api.dto.req.FindUserByIdReqDTO;
import com.lavy.redbook.user.api.dto.req.FindUserByPhoneReqDTO;
import com.lavy.redbook.user.api.dto.req.RegisterUserReqDTO;
import com.lavy.redbook.user.api.dto.req.UpdateUserPasswordReqDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByIdRspDTO;
import com.lavy.redbook.user.api.dto.resp.FindUserByPhoneRspDTO;
import com.lavy.redbook.user.biz.constant.RedisKeyConstants;
import com.lavy.redbook.user.biz.constant.RoleConstants;
import com.lavy.redbook.user.biz.domain.dataobject.RoleDO;
import com.lavy.redbook.user.biz.domain.dataobject.UserDO;
import com.lavy.redbook.user.biz.domain.dataobject.UserRoleRelDO;
import com.lavy.redbook.user.biz.domain.mapper.RoleDOMapper;
import com.lavy.redbook.user.biz.domain.mapper.UserDOMapper;
import com.lavy.redbook.user.biz.domain.mapper.UserRoleRelDOMapper;
import com.lavy.redbook.user.biz.enums.ResponseCodeEnum;
import com.lavy.redbook.user.biz.enums.SexEnum;
import com.lavy.redbook.user.biz.model.vo.UpdateUserInfoReqVO;
import com.lavy.redbook.user.biz.rpc.DistributedIdGeneratorRpcService;
import com.lavy.redbook.user.biz.rpc.OssRpcService;
import com.lavy.redbook.user.biz.service.UserService;

import jakarta.annotation.Resource;
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

    @Resource
    private OssRpcService ossRpcService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserRoleRelDOMapper userRoleRelDOMapper;
    @Resource
    private RoleDOMapper roleDOMapper;
    @Resource(name = "taskExecutor")
    private Executor taskExecutor;
    @Resource
    private DistributedIdGeneratorRpcService distributedIdGeneratorRpcService;

    /**
     * 本地缓存
     */
    private static Cache<Long, FindUserByIdRspDTO> LOCAL_CACHE = Caffeine.newBuilder()
            .initialCapacity(10000)
            .maximumSize(10000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();


    /**
     * 更新用户信息
     *
     * @param updateUserInfoReqVO 更新用户信息请求参数
     * @return 更新用户信息结果
     */
    @Override
    public Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO) {
        UserDO userDO = new UserDO();
        Long userId = LoginUserContextHolder.getUserId();
        if (userId == null) {
            return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
        }
        userDO.setId(userId);
        boolean needUpdate = false;

        // 头像
        MultipartFile avatar = updateUserInfoReqVO.getAvatar();
        if (avatar != null) {
            String url = ossRpcService.uploadFile(avatar);
            log.info("==> 调用 oss 服务成功，上传头像，url：{}", avatar);
            if (StringUtils.isBlank(url)) {
                throw new BizException(ResponseCodeEnum.UPLOAD_AVATAR_FAIL);
            }
            userDO.setAvatar(url);
            needUpdate = true;
        }
        // 背景图
        MultipartFile backgroundImg = updateUserInfoReqVO.getBackgroundImg();
        if (backgroundImg != null) {
            String url = ossRpcService.uploadFile(backgroundImg);
            log.info("==> 调用 oss 服务成功，上传背景图，url：{}", backgroundImg);
            if (StringUtils.isBlank(url)) {
                throw new BizException(ResponseCodeEnum.UPLOAD_BACKGROUND_IMG_FAIL);
            }
            userDO.setBackgroundImg(url);
            needUpdate = true;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Long> register(RegisterUserReqDTO reqVO) {
        String phone = reqVO.getPhone();

        // 先判断该手机号是否已被注册
        UserDO userDO1 = this.baseMapper.selectByPhone(phone);

        log.info("==> 用户是否注册, phone: {}, userDO: {}", phone, JsonUtils.toJsonString(userDO1));

        // 若已注册，则直接返回用户 ID
        if (Objects.nonNull(userDO1)) {
            return Response.success(userDO1.getId());
        }

        // 否则注册新用户
        // 获取全局自增的小哈书 ID
        String xiaohashuId = distributedIdGeneratorRpcService.getRBSegmentId();
        log.debug("==> 获取全局自增的小哈书 ID: {}", xiaohashuId);
        // 用户ID
        String userSegmentId = distributedIdGeneratorRpcService.getUserSegmentId();
        log.debug("==> 获取全局自增的用户 ID: {}", userSegmentId);

        UserDO userDO = UserDO.builder()
                .id(Long.valueOf(userSegmentId))
                .phone(phone)
                .redbookId(xiaohashuId)
                .nickname("小红薯" + xiaohashuId)
                .status(StatusEnum.ENABLED.getValue())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeletedEnum.NO.getValue())
                .build();

        // 添加入库
        this.baseMapper.insert(userDO);

        // 获取刚刚添加入库的用户 ID
        Long userId = userDO.getId();

        // 给该用户分配一个默认角色
        UserRoleRelDO userRoleDO = UserRoleRelDO.builder()
                .userId(userId)
                .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeletedEnum.NO.getValue())
                .build();
        userRoleRelDOMapper.insert(userRoleDO);

        RoleDO roleDO = roleDOMapper.selectByPrimaryKey(RoleConstants.COMMON_USER_ROLE_ID);

        // 将该用户的角色 ID 存入 Redis 中
        List<String> roles = new ArrayList<>(1);
        roles.add(roleDO.getRoleKey());

        String userRolesKey = RedisKeyConstants.buildUserRoleKey(userId);
        redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(roles));

        return Response.success(userId);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param findUserByPhoneReqDTO 查询条件
     * @return 用户信息
     */
    @Override
    public Response<FindUserByPhoneRspDTO> findByPhone(FindUserByPhoneReqDTO findUserByPhoneReqDTO) {
        String phone = findUserByPhoneReqDTO.getPhone();
        // 根据手机号查询用户信息
        UserDO userDO = this.baseMapper.selectByPhone(phone);
        Preconditions.checkArgument(userDO != null, ResponseCodeEnum.USER_NOT_FOUND.getErrorMessage());
        // 构建返参
        FindUserByPhoneRspDTO findUserByPhoneRspDTO = FindUserByPhoneRspDTO.builder()
                .id(userDO.getId())
                .password(userDO.getPassword())
                .build();
        return Response.success(findUserByPhoneRspDTO);
    }

    @Override
    public Response<?> updatePassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO) {
        // 获取当前请求对应的用户 ID
        Long userId = LoginUserContextHolder.getUserId();

        UserDO userDO = UserDO.builder()
                .id(userId)
                .password(updateUserPasswordReqDTO.getEncodePassword())
                .updateTime(LocalDateTime.now())
                .build();
        // 更新密码
        this.baseMapper.updateByPrimaryKeySelective(userDO);

        return Response.success();
    }

    @Override
    public Response<?> pushUserRoles(Long userId) {
        Long uid = LoginUserContextHolder.getUserId();
        if (uid != null) {
            userId = uid;
        }
        if (userId != null) {
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
        return Response.success();

    }

    @Override
    public Response<FindUserByIdRspDTO> findById(FindUserByIdReqDTO findUserByIdReqDTO) {
        Long userId = findUserByIdReqDTO.getId();
        if (userId == null) {
            return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID);
        }
        FindUserByIdRspDTO dto = LOCAL_CACHE.getIfPresent(userId);
        if (dto != null) {
            log.debug("==> 从缓存中获取用户信息：{}", userId);
            return Response.success(dto);
        }
        String userInfoKey = RedisKeyConstants.buildUserInfoKey(userId);
        String userInfoRedis = (String) redisTemplate.opsForValue().get(userInfoKey);

        // 缓存中存在该用户信息
        if (StringUtils.isNotBlank(userInfoRedis)) {
            FindUserByIdRspDTO userByIdRspDTO = JsonUtils.parseObject(userInfoRedis, FindUserByIdRspDTO.class);
            taskExecutor.execute(() -> LOCAL_CACHE.put(userId, userByIdRspDTO));
            return Response.success(userByIdRspDTO);
        }

        UserDO userDO = this.baseMapper.selectById(userId);
        if (userDO != null) {
            FindUserByIdRspDTO rspDTO = FindUserByIdRspDTO.builder()
                    .id(userDO.getId())
                    .nickName(userDO.getNickname())
                    .avatar(userDO.getAvatar())
                    .build();
            taskExecutor.execute(() -> {
                long expireSecond = 60L * 60 * 24 + RandomUtils.nextInt(0, 60 * 60 * 24);
                redisTemplate.opsForValue()
                        .set(userInfoKey, JsonUtils.toJsonString(rspDTO), expireSecond, TimeUnit.SECONDS);
            });
            return Response.success(rspDTO);
        }
        // 缓存用户信息 null 防止缓存穿透
        taskExecutor.execute(() -> {
            redisTemplate.opsForValue().set(userInfoKey, "null", 60L * 60 * 24, TimeUnit.SECONDS);
        });
        throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
    }
}

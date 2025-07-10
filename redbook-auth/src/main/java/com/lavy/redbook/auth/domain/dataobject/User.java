package com.lavy.redbook.auth.domain.dataobject;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lavyo
 */
@Getter
@Setter
@Builder
@TableName("t_user")
public class User {

    private Long id;

    private String redbookId;

    private String password;

    private String nickname;

    private String avatar;

    private Date birthday;

    private String backgroundImg;

    private String phone;

    private Byte sex;

    private Byte status;

    private String introduction;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

}
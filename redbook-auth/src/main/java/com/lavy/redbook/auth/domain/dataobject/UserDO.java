package com.lavy.redbook.auth.domain.dataobject;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lavyo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {
    private Long id;

    private String username;

    private Date createTime;

    private Date updateTime;

}
package com.ciyun.renshe.entity.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 管理端用户
 */
@Getter
@Setter
@ToString
public class AdminPO {

    private Integer adminId;

    /**
     * 管理员名称
     */

    private String adminName;

    /**
     * 手机号
     */

    private String mobile;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 对应的用户id
     */

    private Integer userId;

    private String ddUserId;


}
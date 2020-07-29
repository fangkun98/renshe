package com.ciyun.renshe.controller.vo.user;

import com.ciyun.renshe.controller.vo.permission.PermissionData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Date 2020/4/9 9:29
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class ReturnUserVO {

    private Integer userId;

    private String name;

    private String mobile;

    private String ddUserId;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 角色对应的权限
     */
    private String permission;

    private PermissionData permissionData;
}

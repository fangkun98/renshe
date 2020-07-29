package com.ciyun.renshe.controller.vo.user;

import com.ciyun.renshe.controller.vo.permission.PermissionData;
import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/5/20 20:18
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class FindExamineUserInfoListVO {
    private Long pageNum;
    private Long pageSize;
    private String name;
    private PermissionData permission;
}

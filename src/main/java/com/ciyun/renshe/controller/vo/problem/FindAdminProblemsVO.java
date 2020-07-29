package com.ciyun.renshe.controller.vo.problem;

import com.ciyun.renshe.controller.vo.permission.PermissionData;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Date 2020/5/28 15:52
 * @Author Admin
 * @Version 1.0
 */
@Data
public class FindAdminProblemsVO {

    private Integer pageNum;
    private Integer pageSize;
    private Integer isSolve;

    @NotNull(message = "用户id不能为空")
    private Integer userId;
    private String startDate;
    private String endDate;
    private String content;

    @NotNull(message = "权限id不能为空")
    private PermissionData permission;
}

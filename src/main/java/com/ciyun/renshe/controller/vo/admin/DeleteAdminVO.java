package com.ciyun.renshe.controller.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Date 2020/4/15 9:57
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("删除区域管理员信息")
public class DeleteAdminVO {
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("/用户id")
    private Integer userId;
    @ApiModelProperty("/权限id")
    private Integer roleId;
}

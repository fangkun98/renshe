package com.ciyun.renshe.controller.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date 2020/4/15 9:57
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("保存网格管理员信息")
public class SaveGridAdminVO {
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("/用户id")
    private Integer userId;
    @NotNull(message = "权限id 不能为空")
    @ApiModelProperty("/权限id")
    private Integer roleId;
    @ApiModelProperty("/区id")
    private Integer areaId;

    @NotNull( message = "街道id不能为空")
    @ApiModelProperty("/街道id")
    private Integer streetId;

    @NotBlank(message = "钉钉用户id不能为空")
    @ApiModelProperty("/钉钉用户id")
    private String ddUserId;

    @NotBlank(message = "网格的名称不能为空")
    @ApiModelProperty("网格的名称")
    private String gridName;

}

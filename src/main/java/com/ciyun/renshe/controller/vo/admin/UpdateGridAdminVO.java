package com.ciyun.renshe.controller.vo.admin;

import com.ciyun.renshe.controller.vo.permission.PermissionData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date 2020/4/15 11:28
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("修改对应网格员管理员和对应群的群主")
public class UpdateGridAdminVO {

    @NotNull(message = "原来的网格管理员的userId不能为空")
    @ApiModelProperty("原来的网格管理员的userId")
    private Integer beforeUserId;

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Integer userId;

    @NotBlank(message = "钉钉用户id不能为空")
    @ApiModelProperty("钉钉用户id")
    private String ddUserId;

    @NotNull(message = "网格id不能为空")
    @ApiModelProperty("网格id")
    private Integer gridId;

    @NotNull(message = "群id不能为空")
    @ApiModelProperty("群id")
    private String chatId;

    private PermissionData permission;
    //private String roleIds;

}

package com.ciyun.renshe.controller.vo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Date 2020/4/29 8:56
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("权限对象")
public class PermissionData {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;

    /**
     * 所属角色集合
     */
    @ApiModelProperty("所属角色集合")
    private List<Integer> roleIds;

    @ApiModelProperty("所管理区集合")
    private List<Integer> areaIds;

    @ApiModelProperty("所管理街道集合")
    private List<Integer> streetIds;

    @ApiModelProperty("所管理网格集合")
    private List<Integer> grids;
}

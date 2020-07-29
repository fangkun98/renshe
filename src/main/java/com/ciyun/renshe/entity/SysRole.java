package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-ciyun-renshe-entity-SysRole")
@Getter
@Setter
@ToString
@TableName(value = "sys_role")
public class SysRole {
    @TableId(value = "role_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色对应的权限
     */
    @TableField(value = "permission")
    @ApiModelProperty(value = "角色对应的权限")
    private String permission;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_ROLE_NAME = "role_name";

    public static final String COL_PERMISSION = "permission";
}
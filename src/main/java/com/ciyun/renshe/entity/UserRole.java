package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户角色中间表
 */
@ApiModel(value = "com-ciyun-renshe-entity-UserRole")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "sys_user_role")
public class UserRole {
    public static final String COL_FLAG = "flag";
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_ROLE_ID = "role_id";
}
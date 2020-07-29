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
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 角色表
 */
@ApiModel(value = "角色基础结构")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "dd_role")
public class Role {
    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    @ApiModelProperty(value = "角色id")
    private String roleId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色组id
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "角色组id")
    private String groupId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 1 有效 0 无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0 无效")
    private Integer flag;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_ROLE_NAME = "role_name";

    public static final String COL_GROUP_ID = "group_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_FLAG = "flag";
}
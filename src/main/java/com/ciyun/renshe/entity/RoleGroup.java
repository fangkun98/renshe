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

@ApiModel(value = "角色组基础结构")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "dd_role_group")
public class RoleGroup {
    /**
     * 角色组id
     */
    @TableId(value = "group_id", type = IdType.INPUT)
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

    /**
     * 角色名称
     */
    @TableField(value = "group_name")
    @ApiModelProperty(value = "角色名称")
    private String groupName;

    public static final String COL_GROUP_ID = "group_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_FLAG = "flag";
}
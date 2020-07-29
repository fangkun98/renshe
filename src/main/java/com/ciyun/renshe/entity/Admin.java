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

import java.util.Date;

/**
 * 管理端用户
 */
@ApiModel(value = "com-ciyun-renshe-entity-Admin")
@Getter
@Setter
@ToString
@TableName(value = "sys_admin")
public class Admin {
    @TableId(value = "admin_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer adminId;

    /**
     * 管理员名称
     */
    @TableField(value = "admin_name")
    @ApiModelProperty(value = "管理员名称")
    private String adminName;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 1 有效 0无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0无效")
    private Integer flag;

    /**
     * 对应的用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "对应的用户id")
    private Integer userId;

    public static final String COL_ADMIN_ID = "admin_id";

    public static final String COL_ADMIN_NAME = "admin_name";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_FLAG = "flag";

    public static final String COL_USER_ID = "user_id";
}
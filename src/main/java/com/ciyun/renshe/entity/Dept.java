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
 * 部门表
 */
@ApiModel(value = "com-ciyun-renshe-entity-Dept")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "dd_dept")
public class Dept {
    @TableId(value = "dept_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer deptId;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 父部门id，根部门id为1
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "父部门id，根部门id为1")
    private String parentId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 1有效 0 无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1有效 0 无效")
    private Integer flag;

    /**
     * 钉钉返回的 部门id
     */
    @TableField(value = "dd_dept_id")
    @ApiModelProperty(value = "钉钉返回的 部门id")
    private Long ddDeptId;
}
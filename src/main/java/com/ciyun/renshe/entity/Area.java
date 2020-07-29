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

/**
 * 各个区
 */
@ApiModel(value = "com-ciyun-renshe-entity-Area")
@Getter
@Setter
@ToString
@TableName(value = "sys_area")
public class Area {
    @TableId(value = "area_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer areaId;

    /**
     * 区的名字
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "区的名字")
    private String name;

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_NAME = "name";
}
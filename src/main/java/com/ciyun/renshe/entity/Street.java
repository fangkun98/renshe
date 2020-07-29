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

@ApiModel(value = "com-ciyun-renshe-entity-Street")
@Getter
@Setter
@ToString
@TableName(value = "sys_street")
public class Street {
    @TableId(value = "street_id", type = IdType.AUTO)
    @ApiModelProperty(value = "街道Id")
    private Integer streetId;

    /**
     * 街道名称
     */
    @TableField(value = "street_name")
    @ApiModelProperty(value = "街道名称")
    private String streetName;

    /**
     * 区id
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "区id")
    private Integer areaId;

    public static final String COL_STREET_ID = "street_id";

    public static final String COL_STREET_NAME = "street_name";

    public static final String COL_AREA_ID = "area_id";
}
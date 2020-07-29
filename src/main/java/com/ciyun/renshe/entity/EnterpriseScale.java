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

/**
 * 行业与规模群
 */
@ApiModel(value = "com-ciyun-renshe-entity-EnterpriseScale")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "sys_enterprise_scale")
public class EnterpriseScale {
    @TableId(value = "es_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer esId;

    /**
     * 规模名称
     */
    @TableField(value = "scale_name")
    @ApiModelProperty(value = "规模名称")
    private String scaleName;

    /**
     * 1 有效 0无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0无效")
    private Integer flag;

    /**
     * 1 为 规模 2为行业
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "1 为 规模 2为行业")
    private Integer state;

    /**
     * 创建者id
     */
    @TableField(value = "create_user_id")
    @ApiModelProperty(value = "创建者id")
    private Integer createUserId;


    public static final String COL_ES_ID = "es_id";

    public static final String COL_SCALE_NAME = "scale_name";

    public static final String COL_FLAG = "flag";

    public static final String COL_STATE = "state";

    public static final String COL_CREATE_USER_ID = "create_user_id";
}
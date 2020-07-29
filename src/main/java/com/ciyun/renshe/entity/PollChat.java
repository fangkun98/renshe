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
 * 群轮询表
 */
@ApiModel(value = "com-ciyun-renshe-entity-PollChat")
@Getter
@Setter
@ToString
@TableName(value = "sys_poll_chat")
public class PollChat {
    @TableId(value = "polling_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer pollingId;

    /**
     * 街道下网格的总数
     */
    @TableField(value = "grid_count")
    @ApiModelProperty(value = "街道下网格的总数")
    private Integer gridCount;

    /**
     * 上一次为总数的哪个值
     */
    @TableField(value = "previous_num")
    @ApiModelProperty(value = "上一次为总数的哪个值")
    private Integer previousNum;

    /**
     * 街道id
     */
    @TableField(value = "street_id")
    @ApiModelProperty(value = "街道id")
    private Integer streetId;

    public static final String COL_POLLING_ID = "polling_id";

    public static final String COL_GRID_COUNT = "grid_count";

    public static final String COL_PREVIOUS_NUM = "previous_num";

    public static final String COL_STREET_ID = "street_id";
}
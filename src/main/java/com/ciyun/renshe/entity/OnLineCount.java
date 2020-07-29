package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 管理员在线总时间表
 */
@ApiModel(value = "com-ciyun-renshe-entity-OnLineCount")
@Getter
@Setter
@ToString
@TableName(value = "sys_on_line_count")
public class OnLineCount {
    public static final String COL_CURRENT_DATE = "current_date";
    @TableId(value = "on_line_count_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer onLineCountId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 当天日期
     */
    @TableField(value = "count_current_date")
    @ApiModelProperty(value = "当天日期")
    private Date countCurrentDate;

    /**
     * 总计时间
     */
    @TableField(value = "count_time")
    @ApiModelProperty(value = "总计时间")
    private String countTime;

    public static final String COL_ON_LINE_COUNT_ID = "on_line_count_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_COUNT_CURRENT_DATE = "count_current_date";

    public static final String COL_COUNT_TIME = "count_time";
}
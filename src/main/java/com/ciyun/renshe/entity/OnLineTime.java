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
 * 管理员在线时间段记录表
 */
@ApiModel(value = "com-ciyun-renshe-entity-OnLineTime")
@Getter
@Setter
@ToString
@TableName(value = "sys_on_line_time")
public class OnLineTime {
    public static final String COL_CURRENT_DATE = "current_date";
    @TableId(value = "on_line_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer onLineId;

    /**
     * 上线时间
     */
    @TableField(value = "on_line_time")
    @ApiModelProperty(value = "上线时间")
    private Date onLineTime;

    /**
     * 下线时间
     */
    @TableField(value = "off_line_time")
    @ApiModelProperty(value = "下线时间")
    private Date offLineTime;

    /**
     * 管理员id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "管理员id")
    private Integer userId;

    /**
     * 当天日期
     */
    @TableField(value = "on_line_current_date")
    @ApiModelProperty(value = "当天日期")
    private String onLineCurrentDate;

    public static final String COL_ON_LINE_ID = "on_line_id";

    public static final String COL_ON_LINE_TIME = "on_line_time";

    public static final String COL_OFF_LINE_TIME = "off_line_time";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_ON_LINE_CURRENT_DATE = "on_line_current_date";
}
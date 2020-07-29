package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 通知-区域-用户-对应表
 */
@ApiModel(value = "com-ciyun-renshe-entity-NoticeArea")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "sys_notice_area")
public class NoticeArea {
    public static final String COL_ALL = "all";
    /**
     * 公告id
     */
    @TableField(value = "notice_id")
    @ApiModelProperty(value = "公告id")
    private Integer noticeId;

    /**
     * 区id
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "区id")
    private Integer areaId;

    /**
     * 街道id
     */
    @TableField(value = "street_id")
    @ApiModelProperty(value = "街道id")
    private Integer streetId;

    /**
     * 网格id
     */
    @TableField(value = "grid_id")
    @ApiModelProperty(value = "网格id")
    private Integer gridId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 1 为全部，0为具体某个区或者街道网格，成员
     */
    @TableField(value = "is_all")
    @ApiModelProperty(value = "1 为全部，0为具体某个区或者街道网格，成员")
    private Integer isAll;

    public static final String COL_NOTICE_ID = "notice_id";

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_STREET_ID = "street_id";

    public static final String COL_GRID_ID = "grid_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_IS_ALL = "is_all";
}
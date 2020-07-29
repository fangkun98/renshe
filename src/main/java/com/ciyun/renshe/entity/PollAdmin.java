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
 * 回复消息时轮询管理员的id
 */
@ApiModel(value = "com-ciyun-renshe-entity-PollAdmin")
@Getter
@Setter
@ToString
@TableName(value = "sys_poll_admin")
public class PollAdmin {
    public static final String COL_USER_ID = "user_id";
    @TableId(value = "poll_admin_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer pollAdminId;

    /**
     * 总数
     */
    @TableField(value = "poll_count")
    @ApiModelProperty(value = "总数")
    private Integer pollCount;

    /**
     * 轮循到下一个管理员位置
     */
    @TableField(value = "next_num")
    @ApiModelProperty(value = "轮循到下一个管理员位置")
    private Integer nextNum;

    /**
     * 群名称
     */
    @TableField(value = "chat_name")
    @ApiModelProperty(value = "群名称")
    private String chatName;

    /**
     * 群id
     */
    @TableField(value = "chat_id")
    @ApiModelProperty(value = "群id")
    private String chatId;

    public static final String COL_POLL_ADMIN_ID = "poll_admin_id";

    public static final String COL_POLL_COUNT = "poll_count";

    public static final String COL_NEXT_NUM = "next_num";

    public static final String COL_CHAT_NAME = "chat_name";

    public static final String COL_CHAT_ID = "chat_id";
}
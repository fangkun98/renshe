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
 * @机器人之后要存放的
 */
@ApiModel(value = "com-ciyun-renshe-entity-ChatAt")
@Getter
@Setter
@ToString
@TableName(value = "dd_chat_at")
public class ChatAt {
    @TableId(value = "at_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer atId;

    @TableField(value = "msg_id")
    @ApiModelProperty(value = "")
    private String msgId;

    /**
     * 消息内容
     */
    @TableField(value = "msg_content")
    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    /**
     * 消息的时间戳，单位ms
     */
    @TableField(value = "create_at")
    @ApiModelProperty(value = "消息的时间戳，单位ms")
    private String createAt;

    /**
     * 群名称
     */
    @TableField(value = "conversation_title")
    @ApiModelProperty(value = "	,群名称")
    private String conversationTitle;

    /**
     * 发送者昵称
     */
    @TableField(value = "sender_nick")
    @ApiModelProperty(value = "发送者昵称")
    private String senderNick;

    /**
     * 发送者在企业内的userid（钉钉userId）（企业内部群有）
     */
    @TableField(value = "sender_staff_id")
    @ApiModelProperty(value = "发送者在企业内的userid（钉钉userId）（企业内部群有）")
    private String senderStaffId;

    /**
     * 1 有效 0无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0无效")
    private Integer flag;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 消息回复时间
     */
    @TableField(value = "record_time")
    @ApiModelProperty(value = "消息回复时间")
    private Date recordTime;

    /**
     * 1 未回复 2.已回复
     */
    @TableField(value = "msg_flag")
    @ApiModelProperty(value = "1 未回复 2.已回复")
    private Integer msgFlag;

    /**
     * 1开启，2关闭
     */
    @TableField(value = "is_close")
    @ApiModelProperty(value = "1开启，2关闭")
    private Integer isClose;

    /**
     * 群消息地址
     */
    @TableField(value = "session_webhook")
    @ApiModelProperty(value = "群消息地址")
    private String sessionWebhook;

    /**
     * 群消息地址失效时间
     */
    @TableField(value = "session_webhook_expired_time")
    @ApiModelProperty(value = "群消息地址失效时间")
    private String sessionWebhookExpiredTime;

    /**
     * 接收的用户id
     */
    @TableField(value = "receiving_user_id")
    @ApiModelProperty(value = "接收的用户id")
    private String receivingUserId;

    /**
     * 1已读 2未读
     */
    @TableField(value = "is_read")
    @ApiModelProperty(value = "1已读 2未读")
    private Integer isRead;

    public static final String COL_AT_ID = "at_id";

    public static final String COL_MSG_ID = "msg_id";

    public static final String COL_MSG_CONTENT = "msg_content";

    public static final String COL_CREATE_AT = "create_at";

    public static final String COL_CONVERSATION_TITLE = "conversation_title";

    public static final String COL_SENDER_NICK = "sender_nick";

    public static final String COL_SENDER_STAFF_ID = "sender_staff_id";

    public static final String COL_FLAG = "flag";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_RECORD_TIME = "record_time";

    public static final String COL_MSG_FLAG = "msg_flag";

    public static final String COL_IS_CLOSE = "is_close";

    public static final String COL_SESSION_WEBHOOK = "session_webhook";

    public static final String COL_SESSION_WEBHOOK_EXPIRED_TIME = "session_webhook_expired_time";

    public static final String COL_RECEIVING_USER_ID = "receiving_user_id";

    public static final String COL_IS_READ = "is_read";
}
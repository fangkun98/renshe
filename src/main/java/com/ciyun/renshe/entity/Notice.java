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
 * 公告表
 */
@ApiModel(value = "com-ciyun-renshe-entity-Notice")
@Getter
@Setter
@ToString
@TableName(value = "sys_notice")
public class Notice {
    public static final String COL_MSG_ID = "msg_id";
    @TableId(value = "notice_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer noticeId;

    /**
     * 1 通知公告  2. 政策资讯
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "1 通知公告  2. 政策资讯")
    private Integer type;

    /**
     * 如果是政策资讯，分为 全部、就业创业、职业培训、人才服务、劳动维权、社会保险
     */
    @TableField(value = "type_class")
    @ApiModelProperty(value = "如果是政策资讯，分为 全部、就业创业、职业培训、人才服务、劳动维权、社会保险,")
    private String typeClass;

    /**
     * 消息的类型1.Text 2.Image 3.File 4.Link 5.ActionCard 6.BtnActionCard
     */
    @TableField(value = "msg_type")
    @ApiModelProperty(value = "消息的类型1.Text 2.Image 3.File 4.Link 5.ActionCard 6.BtnActionCard ")
    private Integer msgType;

    /**
     * 消息内容
     */
    @TableField(value = "msg_content")
    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    /**
     * 内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 1 有效 0无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0无效")
    private Integer flag;

    /**
     * 标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 点赞数
     */
    @TableField(value = "likes")
    @ApiModelProperty(value = "点赞数")
    private Integer likes;

    /**
     * 阅读数
     */
    @TableField(value = "reading_number")
    @ApiModelProperty(value = "阅读数")
    private Integer readingNumber;

    /**
     * 创建者id
     */
    @TableField(value = "create_user_id")
    @ApiModelProperty(value = "创建者id")
    private Integer createUserId;

    /**
     * url
     */
    @TableField(value = "url")
    @ApiModelProperty(value = "url")
    private String url;

    /**
     * 是否为群消息 1 是 0 否
     */
    @TableField(value = "is_chat_msg")
    @ApiModelProperty(value = "是否为群消息 1 是 0 否")
    private Integer isChatMsg;

    /**
     * 重发时间
     */
    @TableField(value = "resend_time")
    @ApiModelProperty(value = "重发时间")
    private Date resendTime;

    /**
     * 是否已经完成重新发送 1 已经完成 0 未发送
     */
    @TableField(value = "is_resend")
    @ApiModelProperty(value = "是否已经完成重新发送 1 已经完成 0 未发送")
    private Integer isResend;

    /**
     * 1 群消息 2 个人消息
     */
    @TableField(value = "chat_or_user")
    @ApiModelProperty(value = "1 群消息 2 个人消息")
    private Integer chatOrUser;

    /**
     * 1 为全部已读 0为还有未读人员
     */
    @TableField(value = "is_all_read")
    @ApiModelProperty(value = "1 为全部已读 0为还有未读人员")
    private Integer isAllRead;

    /**
     * 1 为保存不发送到钉钉 2 为保存并发送到钉钉
     */
    @TableField(value = "save_or_save_send")
    @ApiModelProperty(value = "1 为保存不发送到钉钉 2 为保存并发送到钉钉")
    private Integer saveOrSaveSend;

    /**
     * 1为正常 0为撤销
     */
    @TableField(value = "notice_cancel")
    @ApiModelProperty(value = "1为正常 0为撤销")
    private Integer noticeCancel;

    public static final String COL_NOTICE_ID = "notice_id";

    public static final String COL_TYPE = "type";

    public static final String COL_TYPE_CLASS = "type_class";

    public static final String COL_MSG_TYPE = "msg_type";

    public static final String COL_MSG_CONTENT = "msg_content";

    public static final String COL_CONTENT = "content";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_FLAG = "flag";

    public static final String COL_TITLE = "title";

    public static final String COL_LIKES = "likes";

    public static final String COL_READING_NUMBER = "reading_number";

    public static final String COL_CREATE_USER_ID = "create_user_id";

    public static final String COL_URL = "url";

    public static final String COL_IS_CHAT_MSG = "is_chat_msg";

    public static final String COL_RESEND_TIME = "resend_time";

    public static final String COL_IS_RESEND = "is_resend";

    public static final String COL_CHAT_OR_USER = "chat_or_user";

    public static final String COL_IS_ALL_READ = "is_all_read";

    public static final String COL_SAVE_OR_SAVE_SEND = "save_or_save_send";

    public static final String COL_NOTICE_CANCEL = "notice_cancel";
}
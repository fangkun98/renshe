package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-ciyun-renshe-entity-NoticeChat")
@Getter
@Setter
@ToString
@TableName(value = "sys_notice_chat")
public class NoticeChat {
    public static final String COL_USER_ID = "user_id";
    /**
     * 通知，资讯id
     */
    @TableField(value = "notice_id")
    @ApiModelProperty(value = "通知，资讯id")
    private Integer noticeId;

    /**
     * 群聊id
     */
    @TableField(value = "chat_id")
    @ApiModelProperty(value = "群聊id")
    private String chatId;

    /**
     * 用户id,这里为钉钉的userId
     */
    @TableField(value = "dd_user_id")
    @ApiModelProperty(value = "用户id,这里为钉钉的userId")
    private String ddUserId;

    /**
     * 消息id
     */
    @TableField(value = "msg_id")
    @ApiModelProperty(value = "消息id")
    private String msgId;

    /**
     * 消息是否全部已读 1为全部已读，0为还有未读
     */
    @TableField(value = "is_all_read")
    @ApiModelProperty(value = "消息是否全部已读 1为全部已读，0为还有未读")
    private Integer isAllRead;

    /**
     * 已读人员列表
     */
    @TableField(value = "read_user_list")
    @ApiModelProperty(value = "已读人员列表")
    private String readUserList;

    /**
     * 已读人数
     */
    @TableField(value = "read_num")
    @ApiModelProperty(value = "已读人数")
    private Integer readNum;

    /**
     * 未读人数
     */
    @TableField(value = "unread_num")
    @ApiModelProperty(value = "未读人数")
    private Integer unreadNum;

    public static final String COL_NOTICE_ID = "notice_id";

    public static final String COL_CHAT_ID = "chat_id";

    public static final String COL_DD_USER_ID = "dd_user_id";

    public static final String COL_MSG_ID = "msg_id";

    public static final String COL_IS_ALL_READ = "is_all_read";

    public static final String COL_READ_USER_LIST = "read_user_list";

    public static final String COL_READ_NUM = "read_num";

    public static final String COL_UNREAD_NUM = "unread_num";
}
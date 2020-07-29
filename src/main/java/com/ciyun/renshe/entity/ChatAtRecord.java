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

@ApiModel(value = "com-ciyun-renshe-entity-ChatAtRecord")
@Getter
@Setter
@ToString
@TableName(value = "dd_chat_at_record")
public class ChatAtRecord {
    @TableId(value = "record_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer recordId;

    @TableField(value = "at_id")
    @ApiModelProperty(value = "")
    private Integer atId;

    /**
     * 文本内容
     */
    @TableField(value = "message_content")
    @ApiModelProperty(value = "文本内容")
    private String messageContent;

    /**
     * 发送消息的用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "发送消息的用户id")
    private Integer userId;

    /**
     * 1 有效 0 无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0 无效")
    private Integer flag;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 1 提问者 2 应答者
     */
    @TableField(value = "question_or_answer")
    @ApiModelProperty(value = "1 提问者 2 应答者")
    private Integer questionOrAnswer;

    /**
     * 群名称
     */
    @TableField(value = "chat_name")
    @ApiModelProperty(value = "群名称")
    private String chatName;

    /**
     * 是否已读 0 未读 1已读
     */
    @TableField(value = "is_read")
    @ApiModelProperty(value = "是否已读 0 未读 1已读")
    private Integer isRead;

    public static final String COL_RECORD_ID = "record_id";

    public static final String COL_AT_ID = "at_id";

    public static final String COL_MESSAGE_CONTENT = "message_content";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_FLAG = "flag";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_QUESTION_OR_ANSWER = "question_or_answer";

    public static final String COL_CHAT_NAME = "chat_name";

    public static final String COL_IS_READ = "is_read";
}
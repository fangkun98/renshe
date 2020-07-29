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

import java.util.Date;

@ApiModel(value = "com-ciyun-renshe-entity-Message")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "dd_message")
public class Message {
    /**
     * 消息id
     */
    @TableId(value = "msg_id", type = IdType.INPUT)
    @ApiModelProperty(value = "消息id")
    private String msgId;

    /**
     * 群id
     */
    @TableField(value = "chat_id")
    @ApiModelProperty(value = "群id")
    private String chatId;

    /**
     * 消息内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "消息内容")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 1.Text 2.Image 3.File 4.Link 5.ActionCard 6.BtnActionCard
     */
    @TableField(value = "msg_type")
    @ApiModelProperty(value = "1.Text 2.Image 3.File 4.Link 5.ActionCard 6.BtnActionCard ")
    private Integer msgType;

    /**
     * 内容类型， 1 通知消息 2 群消息
     */
    @TableField(value = "content_type")
    @ApiModelProperty(value = "内容类型， 1 通知消息 2 群消息")
    private Integer contentType;

    /**
     * 钉钉媒体id
     */
    @TableField(value = "mediaId")
    @ApiModelProperty(value = "钉钉媒体id")
    private String mediaid;

    /**
     * 1 有效 0 无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0 无效")
    private Integer flag;

    /**
     * 文件路径
     */
    @TableField(value = "file_url")
    @ApiModelProperty(value = "文件路径")
    private String fileUrl;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    public static final String COL_MSG_ID = "msg_id";

    public static final String COL_CHAT_ID = "chat_id";

    public static final String COL_CONTENT = "content";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MSG_TYPE = "msg_type";

    public static final String COL_CONTENT_TYPE = "content_type";

    public static final String COL_MEDIAID = "mediaId";

    public static final String COL_FLAG = "flag";

    public static final String COL_FILE_URL = "file_url";

    public static final String COL_USER_ID = "user_id";
}
package com.ciyun.renshe.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 公告表
 */
@ApiModel(value = "com-ciyun-renshe-entity-Notice")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "sys_notice")
public class NoticePO {

    @ApiModelProperty(value = "")
    private Integer noticeId;

    //private List<NoticeChat> noticeChats;

    @ApiModelProperty(value = "1 通知公告  2. 政策资讯")
    private Integer type;

    @ApiModelProperty(value = "消息的类型1.Text 2.Image 3.File 4.Link 5.ActionCard 6.BtnActionCard ")
    private Integer msgType;

    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "1 有效 0无效")
    private Integer flag;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "点赞数")
    private Integer likes;

    @ApiModelProperty(value = "阅读数")
    private Integer readingNumber;

    @ApiModelProperty(value = "创建者id")
    private Integer createUserId;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "是否为群消息 1 是 0 否")
    private Integer isChatMsg;

    @ApiModelProperty(value = "1为正常 0为撤销")
    private Integer noticeCancel;

    /**
     * 发送信息的用户名称
     */
    private String createUserName;

}
package com.ciyun.renshe.controller.vo.message.receive;

import com.ciyun.renshe.entity.ChatAtRecord;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Date 2020/4/29 16:33
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class ReturnReceive {

    private Integer atId;

    /**
     * 消息内容
     */
    private List<ChatAtRecord> msgContent;

    /**
     * 消息的时间戳，单位ms
     */
    private String createAt;

    /**
     * 群名称
     */
    private String conversationTitle;

    /**
     * 发送者昵称
     */
    private String senderNick;

    /**
     * 发送者在企业内的userid（企业内部群有）
     */
    private String senderStaffId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 1 未回复 2.已回复
     */
    private Integer msgFlag;

    /**
     * 群消息地址
     */
    private String sessionWebhook;

    /**
     * 群消息地址失效时间
     */
    private String sessionWebhookExpiredTime;

    /**
     * 接收的用户id
     */
    private String receivingUserId;

    /**
     * 发送者名称
     */
    private String name;

    /**
     * 公司名称
     */
    private String companyName;

    private String msgId;

    private Integer isRead;
}

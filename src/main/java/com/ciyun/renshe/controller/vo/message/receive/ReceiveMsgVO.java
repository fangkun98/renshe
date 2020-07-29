package com.ciyun.renshe.controller.vo.message.receive;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Date 2020/4/28 13:28
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class ReceiveMsgVO {
    /**
     * 目前只支持text
     */
    private String msgtype;


    /**
     * 发送群消息的地址
     */
    private String sessionWebhook;

    /**
     * 群地址失效时间
     */
    private String sessionWebhookExpiredTime;

    /**
     * 消息文本
     */
    private Text text;
    /**
     * 加密的消息ID
     */
    private String msgId;
    /**
     * 消息的时间戳，单位ms
     */
    private String createAt;
    /**
     * 1-单聊、2-群聊
     */
    private String conversationType;
    /**
     * 加密的会话ID
     */
    private String conversationId;
    /**
     * 会话标题（群聊时才有）
     */
    private String conversationTitle;
    /**
     * 加密的发送者ID
     */
    private String senderId;
    /**
     * 发送者昵称
     */
    private String senderNick;
    /**
     * 发送者当前群的企业corpId（企业内部群有）
     */
    private String senderCorpId;
    /**
     * 发送者在企业内的userid（企业内部群有）
     */
    private String senderStaffId;
    /**
     * 加密的机器人ID
     */
    private String chatbotUserId;
    /**
     * 被@人的信息
     * <p>
     * dingtalkId: 加密的发送者ID
     * <p>
     * staffId: 发送者在企业内的userid（企业内部群有）
     */
    private List<AtUser> atUsers;

}

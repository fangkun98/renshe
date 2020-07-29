package com.ciyun.renshe.manager.dingding.dto.chat;

import lombok.Data;

import java.util.List;

/**
 * 机器人发送消息到群相关参数
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/9 13:50
 */
@Data
public class RobotMsgToChatParam {
    /**
     * 机器人回答的答案
     */
    private String answerContent;

    /**
     * 机器人返回的相关问题列表
     */
    private List<String> relatedQuestion;

    /**
     * 钉钉返回的群消息发送地址
     */
    private String sessionWebhook;

    /**
     * 发送人id
     */
    private String ddUserId;
}

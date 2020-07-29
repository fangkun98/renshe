package com.ciyun.renshe.manager.dingding;

import com.ciyun.renshe.manager.dingding.dto.chat.RobotMsgToChatParam;

/**
 * @author kys
 * @version 1.0
 * @date 2020/7/9 13:41
 */
public interface DingDingChatMessage {

    /**
     * 将机器人的消息发送到钉钉群中
     *
     * @param robotMsgToChatParam
     */
    void sendRobotMsgToChat(RobotMsgToChatParam robotMsgToChatParam);
}

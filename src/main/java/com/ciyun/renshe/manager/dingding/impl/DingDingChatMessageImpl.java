package com.ciyun.renshe.manager.dingding.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.manager.dingding.DingDingChatMessage;
import com.ciyun.renshe.manager.dingding.dto.chat.RobotMsgToChatParam;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kys
 * @version 1.0
 * @date 2020/7/9 13:44
 */
@Slf4j
@Service
public class DingDingChatMessageImpl implements DingDingChatMessage {

    @Value("${project.question}")
    private String url;

    /**
     * 将机器人的消息发送到钉钉群中
     *
     * @param robotMsgToChatParam
     */
    @Override
    public void sendRobotMsgToChat(RobotMsgToChatParam robotMsgToChatParam) {
        String session = robotMsgToChatParam.getSessionWebhook();
        String accessToken = DingDingUtil.ACCESS_TOKEN;
        String url = session + "&access_token=" + accessToken;
        DingTalkClient client = new DefaultDingTalkClient(url);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("actionCard");
        OapiRobotSendRequest.Actioncard actionCard = new OapiRobotSendRequest.Actioncard();

        actionCard.setTitle("社企通群助手回答了你的问题");

        boolean notEmpty = CollectionUtil.isNotEmpty(robotMsgToChatParam.getRelatedQuestion());
        String answer = getAnswer(robotMsgToChatParam.getAnswerContent(), notEmpty);
        actionCard.setText(answer);
        // 按钮竖排
        actionCard.setBtnOrientation("0");
        actionCard.setBtns(getBtns(robotMsgToChatParam.getRelatedQuestion()));

        request.setActionCard(actionCard);
        OapiRobotSendResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(response));
    }

    /**
     * 获取答案
     *
     * @return
     */
    private String getAnswer(String answer, Boolean relatedQuestionIsNotEmpty) {

        StringBuilder text = new StringBuilder("### ");

        String relatedQuestionStr = "\n #### 你也可以点击以下相关问题获取相关解答:";

        String str = "您所咨询的内容暂时不在我的知识范围内";

        // 如果没有给出相应的答案
        if (answer.contains(str)) {
            if (relatedQuestionIsNotEmpty) {
                text.append("不知您关心的是不是下列问题，可直接点击获取答案： \n");
            } else {
                text.append(str).append(" \n");
                text.append(" ##### 如需转人工回答，请 \n  @社企通群助手 人工");
            }

        } else {
            text.append(answer);
            text.append(" ##### 如需转人工回答，请 \n  @社企通群助手 人工");
            if (relatedQuestionIsNotEmpty) {
                text.append(relatedQuestionStr);
            }
        }
        return text.toString();
    }

    /**
     * 根据对应的问题列表得到按钮对象
     *
     * @param relatedQuestion
     * @return
     */
    private List<OapiRobotSendRequest.Btns> getBtns(List<String> relatedQuestion) {
        List<OapiRobotSendRequest.Btns> btnList = new ArrayList<>(3);

        for (String question : relatedQuestion) {
            OapiRobotSendRequest.Btns btn = new OapiRobotSendRequest.Btns();
            btn.setTitle(question);
            String s = url + "?question=" + question;
            btn.setActionURL(s);
            btnList.add(btn);
        }
        return btnList;
    }
}

package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.ciyun.renshe.common.dingding.message.DDSendMessageUtils;
import com.ciyun.renshe.common.dingding.message.MessageTypeConvert;
import com.ciyun.renshe.common.dingding.message.param.MessageBody;
import com.ciyun.renshe.common.dingding.message.type.ActionCard;
import com.ciyun.renshe.common.dingding.sdk.request.MessageCorpParam;
import com.ciyun.renshe.service.CallbackService;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钉钉回调实现类
 *
 * @Date 2020/5/29 9:40
 * @Author kys
 * @Version 1.0
 */
@Slf4j
@Service
public class CallbackServiceImpl implements CallbackService {

    @Value("${project.singleUrl}")
    private String singleUrl;

    /**
     * 钉钉添加用户回调执行方法，发送流程信息
     *
     * @param userIds
     */
    @Override
    public void userAdd(List<String> userIds) {
        log.info("首次添加用户，回调返回的userId为：{}", JSON.toJSONString(userIds));
        if (CollectionUtil.isNotEmpty(userIds)) {

            MessageBody messageBody = new MessageBody();
            messageBody.setMsgType(5);
            ActionCard actionCard = new ActionCard();
            actionCard.setSingleTitle("点击查看详情");
            actionCard.setTitle("青岛社企通流程说明书");
            actionCard.setSingleUrl(singleUrl);
            messageBody.setActionCard(actionCard);

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = MessageTypeConvert.messageCorpconvert(messageBody);

            String join = String.join(",", userIds);
            log.debug("userIds使用字符串分割，{}", join);
            //
            MessageCorpParam param = new MessageCorpParam();
            param.setMsg(msg);
            param.setUserIdList(join);
            OapiMessageCorpconversationAsyncsendV2Response response = null;
            try {
                response = DDSendMessageUtils.messageCorpconversationAsync(param);
                log.info("钉钉添加用户回调执行方法，添加用户发送流程信息返回结果：{}", JSON.toJSONString(response));
            } catch (ApiException e) {
                log.warn("钉钉添加用户回调执行方法,添加用户发送流程信息返回结果，要发送的用户为：{}", join);
                e.printStackTrace();
            }
        }
    }
}

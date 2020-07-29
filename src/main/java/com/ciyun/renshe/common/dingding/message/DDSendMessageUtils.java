package com.ciyun.renshe.common.dingding.message;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.sdk.request.MessageCorpParam;
import com.ciyun.renshe.common.dingding.sdk.request.SendChatMessageParam;
import com.ciyun.renshe.config.DingDingData;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiChatSendResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;

/**
 * 钉钉发送消息接口
 *
 * @Date 2020/4/10 16:37
 * @Author Admin
 * @Version 1.0
 */
public class DDSendMessageUtils {

    /**
     * 钉钉发送工作通知
     *
     * @param param
     * @return
     */
    public static OapiMessageCorpconversationAsyncsendV2Response messageCorpconversationAsync(MessageCorpParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        if (StringUtils.isNotBlank(param.getUserIdList())) {
            request.setUseridList(param.getUserIdList());
        }
        if (StringUtils.isNotBlank(param.getDeptIdList())) {
            request.setDeptIdList(param.getDeptIdList());
        }
        request.setAgentId(Long.parseLong(DingDingData.AGENT_ID.getValue()));
        // 是否设置全员发送
        request.setToAllUser(false);

        request.setMsg(param.getMsg());

        return client.execute(request, DingDingUtil.ACCESS_TOKEN);

    }

    /**
     * 发送群消息
     *
     * @return
     */
    public static OapiChatSendResponse sendChatMessage(SendChatMessageParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/send");
        OapiChatSendRequest request = new OapiChatSendRequest();
        request.setChatid(param.getChatId());
        request.setMsg(param.getMsg());
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }

}

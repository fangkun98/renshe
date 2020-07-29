package com.ciyun.renshe.common.dingding.sdk.request;

import com.dingtalk.api.request.OapiChatSendRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 发送群消息参数
 *
 * @Date 2020/4/13 11:14
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SendChatMessageParam {

    /**
     * 群会话的id，可以通过以下方式获取：
     * <p>
     * （1）调用服务端API获取。调用创建群会话接口的返回chatid字段
     * <p>
     * （2）调用前端API获取。小程序调用选择会话获取，H5微应用调用根据corpid选择会话获取。
     */
    private String chatId;

    /**
     * 消息内容，消息类型和样例可参考“消息类型与数据格式”文档
     */
    private OapiChatSendRequest.Msg msg;
}

package com.ciyun.renshe.common.dingding.sdk.request;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 发送工作通知消息接收参数
 *
 * @Date 2020/4/10 16:03
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class MessageCorpParam {

    /**
     * 接收者的用户userid列表，最大用户列表长度：100
     */
    public String userIdList;

    /**
     * 接收者的部门id列表，最大列表长度：20,  接收者是部门id下(包括子部门下)的所有用户
     */
    public String deptIdList;

    /**
     * 是否发送给企业全部用户
     */
    public Boolean toAllUser;

    /**
     * 消息内容 最长不超过2048个字节
     */
    public OapiMessageCorpconversationAsyncsendV2Request.Msg msg;

}

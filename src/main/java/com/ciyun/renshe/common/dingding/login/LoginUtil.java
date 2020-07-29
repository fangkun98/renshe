package com.ciyun.renshe.common.dingding.login;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;

/**
 * @Date 2020/4/14 14:33
 * @Author Admin
 * @Version 1.0
 */
public class LoginUtil {

    /**
     * 企业内部应用免登录
     * 1. 获取登陆授权码
     * 2. 获取用 userid
     * <p>
     * URL： https://ding-doc.dingtalk.com/doc#/serverapi2/clotub
     *
     * @param code
     * @return
     * @throws ApiException
     */
    public static OapiUserGetuserinfoResponse loginUser(String code) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }
}

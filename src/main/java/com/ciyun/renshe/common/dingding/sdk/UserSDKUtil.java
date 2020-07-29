package com.ciyun.renshe.common.dingding.sdk;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.sdk.request.GetUserParam;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.taobao.api.ApiException;

/**
 * 用户相关 SDK
 *
 * @Date 2020/4/11 15:15
 * @Author Admin
 * @Version 1.0
 */
public class UserSDKUtil {

    /**
     * 获取部门用户
     *
     * @return
     */
    public static OapiUserSimplelistResponse userSimpleList(GetUserParam param) throws ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(param.getDepartmentId());
        request.setOffset(param.getOffset());
        request.setSize(param.getSize());
        request.setHttpMethod("GET");
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 根据手机号获取 userid
     *
     * @param mobile
     * @return
     */
    public static OapiUserGetByMobileResponse getUserByMobile(String mobile) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
        OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
        request.setMobile(mobile);
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }
}

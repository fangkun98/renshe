package com.ciyun.renshe.common.dingding;

import com.ciyun.renshe.config.DingDingData;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

/**
 * 钉钉相关工具类
 *
 * @Date 2020/4/2 9:35
 * @Author kong
 * @Version 1.0
 */
@Slf4j
public class DingDingUtil {

    /**
     * 钉钉 AccessToken
     */
    public static String ACCESS_TOKEN = "";

    /**
     * 获取钉钉 access_token
     * 成功返回 accessToken
     * 失败返回 ""
     * 有效期为7200秒
     *
     * @return
     */
    public static String getDingDingAccessToken() {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(DingDingData.APP_KEY.getValue());
        request.setAppsecret(DingDingData.APP_SECRET.getValue());
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response = client.execute(request);
            if (response.getErrcode() == 0) {
                DingDingUtil.ACCESS_TOKEN = response.getAccessToken();
                log.info("AccessToken：" + DingDingUtil.ACCESS_TOKEN);
                return response.getAccessToken();
            } else {
                return "";
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return "";
        }
    }
}

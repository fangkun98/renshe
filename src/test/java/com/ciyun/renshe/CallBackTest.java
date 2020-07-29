package com.ciyun.renshe;

import com.alibaba.fastjson.JSON;
import com.ciyun.renshe.config.Constant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackGetCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackGetCallBackResponse;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @Date 2020/5/29 10:14
 * @Author kys
 * @Version 1.0
 */
@Slf4j
@SpringBootTest
public class CallBackTest {

    /**
     * 注册回调事件
     */
    @Test
    public void callBackRegister() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
        request.setUrl("https://video.1dang5.com/renshe/callback/user/add");
        request.setAesKey(Constant.ENCODING_AES_KEY);
        request.setToken(Constant.TOKEN);
        request.setCallBackTag(Arrays.asList("user_add_org", "user_modify_org", "user_leave_org"));
        OapiCallBackRegisterCallBackResponse response = client.execute(request, "75e687009f893f839b4083b73bfcb824");
        log.info("response为：{}", JSON.toJSONString(response));
    }

    /**
     * 查询事件回调接口
     *
     * @throws ApiException
     */
    @Test
    public void getCallBackList() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back");
        OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
        request.setHttpMethod("GET");
        OapiCallBackGetCallBackResponse response = client.execute(request, "75e687009f893f839b4083b73bfcb824");
        log.info("查询事件回调接口:{}",JSON.toJSONString(response));
    }
}

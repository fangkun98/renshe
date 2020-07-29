package com.ciyun.renshe.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.config.Constant;
import com.ciyun.renshe.controller.vo.callback.RegisterCallBackVO;
import com.ciyun.renshe.controller.vo.callback.UpdateCallBackVO;
import com.ciyun.renshe.manager.dingding.DingDingUser;
import com.ciyun.renshe.service.CallbackService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackGetCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.request.OapiCallBackUpdateCallBackRequest;
import com.dingtalk.api.response.OapiCallBackDeleteCallBackResponse;
import com.dingtalk.api.response.OapiCallBackGetCallBackResponse;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.api.response.OapiCallBackUpdateCallBackResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.dingtalk.oapi.lib.aes.Utils;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020/5/19 15:11
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Api("/回调")
@CrossOrigin
@RestController
@RequestMapping("/callback")
public class CallbackController {

    private final CallbackService callbackService;
    private final DingDingUser dingDingUser;

    @Autowired
    public CallbackController(CallbackService callbackService, DingDingUser dingDingUser) {
        this.callbackService = callbackService;
        this.dingDingUser = dingDingUser;
    }

    @Value("${dingding.corpId}")
    private String corpId;

    @Value("${dingding.deptId}")
    private String deptId;

    /**
     * 相应钉钉回调时的值
     */
    private static final String CALLBACK_RESPONSE_SUCCESS = "success";

    @PostMapping("/user/add")
    public Map<String, String> userAddCallback(@RequestParam(value = "signature", required = false) String signature,
                                               @RequestParam(value = "timestamp", required = false) String timestamp,
                                               @RequestParam(value = "nonce", required = false) String nonce,
                                               @RequestBody(required = false) JSONObject json) {
        String params = " signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;
        try {
            log.info("begin /callback: " + params);

            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(Constant.TOKEN,
                    Constant.ENCODING_AES_KEY, corpId);

            //从post请求的body中获取回调信息的加密数据进行解密处理
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
            log.info("plainText: {}", plainText);
            JSONObject obj = JSON.parseObject(plainText);
            log.info("钉钉回调推送的数据：{}", JSON.toJSONString(obj));
            //根据回调数据类型做不同的业务处理
            String eventType = obj.getString("EventType");

            log.info("成功，{}", JSON.toJSONString(eventType));

            //Long deptId = 374797302L;

            // 如果是新增员工
            if ("user_add_org".equals(obj.getString("EventType"))) {
                List<String> userIds = (List<String>) obj.get("UserId");
                log.info("新增加的员工 userId为：{}", JSON.toJSONString(userIds));
                for (String userId : userIds) {
                    dingDingUser.updateUserDept(userId, Long.parseLong(deptId));
                }
            }
            // 返回success的加密信息表示回调处理成功
            Map<String, String> encryptedMap = dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS, System.currentTimeMillis(), Utils.getRandomStr(8));
            log.info("解析后的值：{}", JSON.toJSONString(encryptedMap));
            return encryptedMap;

        } catch (Exception e) {
            //失败的情况，应用的开发者应该通过告警感知，并干预修复
            log.error("process callback failed！" + params, e);
            return null;
        }

    }

    /**
     * 群功能相关回调
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param json
     * @return
     */
    @PostMapping("/chat")
    public Map<String, String> chatCallback(@RequestParam(value = "signature", required = false) String signature,
                                            @RequestParam(value = "timestamp", required = false) String timestamp,
                                            @RequestParam(value = "nonce", required = false) String nonce,
                                            @RequestBody(required = false) JSONObject json) {
        log.info("群回调执行");
        String params = " signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;
        try {
            log.info("begin /callback: " + params);

            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(Constant.TOKEN,
                    Constant.ENCODING_AES_KEY, corpId);

            //从post请求的body中获取回调信息的加密数据进行解密处理
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
            log.info("plainText: {}", plainText);
            JSONObject obj = JSON.parseObject(plainText);
            log.info("钉钉回调推送的数据：{}", JSON.toJSONString(obj));
            //根据回调数据类型做不同的业务处理
            String eventType = obj.getString("EventType");

            log.info("成功，{}", JSON.toJSONString(eventType));

            // 返回success的加密信息表示回调处理成功
            Map<String, String> encryptedMap = dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS,
                    System.currentTimeMillis(), Utils.getRandomStr(8));
            log.info("解析后的值：{}", JSON.toJSONString(encryptedMap));
            return encryptedMap;

        } catch (Exception e) {
            //失败的情况，应用的开发者应该通过告警感知，并干预修复
            log.error("process callback failed！" + params, e);
            return null;
        }

    }

    /**
     * 注册回调
     *
     * @param registerCallBackVO
     * @return
     */
    @ApiOperation("注册事件回调")
    @PostMapping("/registerCallBack")
    public Result<String> registerCallBack(@RequestBody @Valid RegisterCallBackVO registerCallBackVO) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
        request.setUrl(registerCallBackVO.getUrl());
        request.setAesKey(registerCallBackVO.getAesKey());
        request.setToken(registerCallBackVO.getToken());
        request.setCallBackTag(registerCallBackVO.getCallBackTag());
        OapiCallBackRegisterCallBackResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        return new Result<>(true, StatusCode.OK, MessageInfo.ADD_INFO.getInfo(), JSON.toJSONString(response));
    }

    /**
     * 查询时间回调接口
     */
    @ApiOperation("更新回调")
    @PostMapping("/updateCallBack")
    public Result<String> updateCallBack(@RequestBody @Valid UpdateCallBackVO updateCallBackVO) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/update_call_back");
        OapiCallBackUpdateCallBackRequest request = new OapiCallBackUpdateCallBackRequest();
        if (StringUtils.isNotBlank(updateCallBackVO.getUrl())) {
            request.setUrl(updateCallBackVO.getUrl());
        }
        if (StringUtils.isNotBlank(updateCallBackVO.getAesKey())) {
            request.setAesKey(updateCallBackVO.getAesKey());
        }
        if (StringUtils.isNotBlank(updateCallBackVO.getToken())) {
            request.setToken(updateCallBackVO.getToken());
        }
        if (CollectionUtil.isNotEmpty(updateCallBackVO.getCallBackTag())) {
            request.setCallBackTag(updateCallBackVO.getCallBackTag());
        }
        OapiCallBackUpdateCallBackResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        return new Result<>(true, StatusCode.OK, MessageInfo.ADD_INFO.getInfo(), JSON.toJSONString(response));
    }

    /**
     * 查询事件回调接口
     *
     * @return
     */
    @GetMapping("/getCallBack")
    @ApiOperation("查询事件回调接口")
    public Result getCallBack() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back");
        OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
        request.setHttpMethod("GET");
        OapiCallBackGetCallBackResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        return new Result(MessageInfo.ADD_INFO.getInfo()).setData(JSON.toJSONString(response));
    }

    /**
     * 删除事件回调接口
     *
     * @return
     */
    @GetMapping("/deleteCallBack")
    @ApiOperation("删除事件回调接口")
    public Result deleteCallBack() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/delete_call_back");
        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
        request.setHttpMethod("GET");
        OapiCallBackDeleteCallBackResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        return new Result(MessageInfo.ADD_INFO.getInfo()).setData(JSON.toJSONString(request));
    }

}

package com.ciyun.renshe.controller.vo.callback;

import lombok.Data;

import java.util.List;

/**
 * 更新事件回调接口
 *
 * @Date 2020/6/1 16:12
 * @Author kys
 * @Version 1.0
 */
@Data
public class UpdateCallBackVO {
    /**
     * 需要监听的事件类型
     */
    private List<String> callBackTag;

    /**
     * 加解密需要用到的token
     */
    private String token;

    /**
     * 数据加密秘钥
     */
    private String aesKey;

    /**
     * 接收事件回调的url，必须是公网可以访问的url地址
     */
    private String url;
}

package com.ciyun.renshe.controller.vo.callback;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 注册业务事件回调接口
 *
 * @Date 2020/6/1 15:59
 * @Author kys
 * @Version 1.0
 */
@Data
public class RegisterCallBackVO {

    /**
     * 需要监听的事件类型
     */
    @NotEmpty(message = "callBackTag 不能为空")
    private List<String> callBackTag;

    /**
     * 加解密需要用到的token
     */
    @NotBlank(message = "token 不能为空")
    private String token;

    /**
     * 数据加密秘钥
     */
    @NotBlank(message = "aesKey 不能为空")
    private String aesKey;

    /**
     * 接收事件回调的url，必须是公网可以访问的url地址
     */
    @NotBlank(message = "url 不能为空")
    private String url;
}

package com.ciyun.renshe.common.dingding;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;

/**
 * 钉钉返回错误信息包装类
 *
 * @Date 2020/4/8 11:32
 * @Author Admin
 * @Version 1.0
 */
public class DDErrResult {

    /**
     * 因数据填写错误导致的错误
     *
     * @param result
     * @return
     */
    public static Result dataError(Result result, String msgInfo) {
        return result.setMessage(msgInfo).setFlag(false).setCode(StatusCode.DD_ERROR);
    }

    /**
     * 连接 钉钉接口出现问题
     *
     * @param result
     * @return
     */
    public static Result connException(Result result) {
        return result.setMessage(MessageInfo.DD_REEOR.getInfo()).setFlag(false).setCode(StatusCode.DD_ERROR);
    }
}

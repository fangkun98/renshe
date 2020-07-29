package com.ciyun.renshe.common;

/**
 * 状态码
 *
 * @Date 2019/8/20 17:16
 * @Author Admin
 * @Version 1.0
 */
public class StatusCode {
    /**
     * 成功返回码
     */
    public static final int OK = 0;
    /**
     * 失败返回码
     */
    public static final int ERROR = 20001;
    /**
     * 权限不足
     */
    public static final int ACCESSREEOR = 20002;
    /**
     * 系统异常
     */
    public static final int SYSTEMERROR = 20003;

    /**
     * 请求钉钉接口错误
     */
    public static final int DD_ERROR = 20004;

}

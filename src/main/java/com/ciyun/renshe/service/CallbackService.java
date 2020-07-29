package com.ciyun.renshe.service;

import java.util.List;

/**
 * 回调Service
 *
 * @author: KYS
 * @create: 2020-05-29 09:39
 **/
public interface CallbackService {
    /**
     * 钉钉添加用户回调执行方法，发送流程信息
     * @param userIds
     */
    void userAdd(List<String> userIds);
}

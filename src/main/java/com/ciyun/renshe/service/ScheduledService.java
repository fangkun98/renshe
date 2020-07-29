package com.ciyun.renshe.service;

import com.taobao.api.ApiException;

public interface ScheduledService {

    /**
     * 给用户在不同群中的人员发送换群通知
     */
    void changeChatUser() throws ApiException;

    /**
     * 定时统计各个消息的已读未读人数
     */
    void selectMsgReadNumAndUnReadNum();
}

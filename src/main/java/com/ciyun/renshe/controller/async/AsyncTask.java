package com.ciyun.renshe.controller.async;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.ciyun.renshe.controller.vo.message.NoticeVO;
import com.ciyun.renshe.controller.vo.message.SendChatMessageVo;
import com.ciyun.renshe.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务类
 *
 * @Date 2020/4/22 10:56
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class AsyncTask {

    private final MessageService messageService;

    /**
     * 异步发送群消息
     *
     * @param messageVo
     */
    @Async
    public void sendChatMessage(SendChatMessageVo messageVo) {
        messageService.sendChatMessage(messageVo);
        log.info("{}群消息发送执行完成，完成时间{}", JSON.toJSONString(messageVo.getChatIds()), DateUtil.now());
    }

    /**
     * 发送工作通知消息
     *
     * @param noticeVO
     */
    @Async
    public void sendNotice(NoticeVO noticeVO) {
        messageService.sendNotice(noticeVO);
    }
}

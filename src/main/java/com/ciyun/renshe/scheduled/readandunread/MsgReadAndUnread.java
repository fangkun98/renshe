package com.ciyun.renshe.scheduled.readandunread;

import com.ciyun.renshe.service.ScheduledService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务统计消息已读未读
 *
 * @Date 2020/5/22 13:51
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Async
@Component
@AllArgsConstructor
public class MsgReadAndUnread {

    private final ScheduledService scheduledService;

    /**
     * 定时任务统计消息已读未读
     * 每天凌晨1点执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void selectMsgReadAndUnread() {
        log.info("定时任务统计消息已读未读");
        scheduledService.selectMsgReadNumAndUnReadNum();
    }
}

package com.ciyun.renshe.scheduled.changechat;

import com.ciyun.renshe.service.ScheduledService;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Date 2020/5/18 10:32
 * @Author Admin
 * @Version 1.0
 */
@Async
@Component
@AllArgsConstructor
public class ChangeChatUserTask {

    private final ScheduledService scheduledService;

    /**
     * 给用户在不同群中的人员发送换群通知
     * 周一上午 12点 30 分执行
     */
    @Scheduled(cron = "0 30 12 ? * MON")
    //@Scheduled(initialDelay = 1000, fixedRate = 3600000)
    public void changeChatUser() throws ApiException {
        scheduledService.changeChatUser();
    }
}

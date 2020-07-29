package com.ciyun.renshe.scheduled;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务
 *
 * @Date 2020-4-2 10:03:03
 * @Author Kong
 * @Version 1.0
 */
@Slf4j
@Component
public class DingDingCodeTask {

    /**
     * 每隔 1 小时执行一次, fixedRate 为毫秒数
     */
    //@Scheduled(cron = "0 0/30 * * * ?")
    @Scheduled(fixedRate = 3600000)
    //@Scheduled(fixedRate = 60000)
    public void getAccessTokenTask() {
        String dingDingAccessToken = DingDingUtil.getDingDingAccessToken();
        log.info("定时获取 AccessToken 时间为：{} AccessToken为：{}", LocalDateTime.now(), dingDingAccessToken);
        //log.info("执行了定时任务");
    }





}

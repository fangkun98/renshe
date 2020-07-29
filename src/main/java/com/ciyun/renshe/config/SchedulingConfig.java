package com.ciyun.renshe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @Date 2020/5/18 10:56
 * @Author Admin
 * @Version 1.0
 */
public class SchedulingConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 定时任务执行线程池核心线程数
        taskScheduler.setPoolSize(4);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("TaskSchedulerThreadPool-");
        return taskScheduler;
    }
}

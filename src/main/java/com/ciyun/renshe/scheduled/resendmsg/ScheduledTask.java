package com.ciyun.renshe.scheduled.resendmsg;

import java.util.concurrent.ScheduledFuture;

/**
 * ScheduledFuture的包装类。
 * ScheduledFuture是ScheduledExecutorService定时任务线程池的执行结果。
 *
 * @Date 2020/5/18 10:59
 * @Author Admin
 * @Version 1.0
 */
public final class ScheduledTask {

    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}

package com.ciyun.renshe.scheduled.resendmsg;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务注册类，用来增加、删除定时任务。
 *
 * @Date 2020/5/18 11:11
 * @Author Admin
 * @Version 1.0
 */
@Component
@AllArgsConstructor
public class CronTaskRegistrar implements DisposableBean {

    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    private final TaskScheduler taskScheduler;

    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }

    public void addCronTask(Runnable task, String cronExpression) {
        addCronTask(new CronTask(task, cronExpression));
    }

    public void addCronTask(CronTask cronTask) {
        if (cronTask != null) {
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(task)) {
                removeCronTask(task);
            }
            this.scheduledTasks.put(task, scheduleCronTask(cronTask));
        }
    }

    public void removeCronTask(Runnable task) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null)
            scheduledTask.cancel();
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());

        return scheduledTask;
    }

    @Override
    public void destroy() throws Exception {
        for (ScheduledTask task : this.scheduledTasks.values()) {
            task.cancel();
        }
        this.scheduledTasks.clear();
    }
}

package com.ciyun.renshe.scheduled.resendmsg;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ciyun.renshe.entity.Job;
import com.ciyun.renshe.mapper.JobMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Date 2020/5/18 13:08
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final JobMapper jobMapper;
    private final CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) throws Exception {

        //
        log.info("启动定时任务成功");

        // 初始加载数据库里状态为正常的定时任务
        List<Job> jobs = jobMapper.selectList(Wrappers.<Job>lambdaQuery()
                .ge(Job::getExecuteTime, new Date())
                .eq(Job::getJobStatus, 1));
        if (CollectionUtil.isNotEmpty(jobs)) {
            for (Job job : jobs) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }
            log.info("定时任务执行了");
        }

    }
}

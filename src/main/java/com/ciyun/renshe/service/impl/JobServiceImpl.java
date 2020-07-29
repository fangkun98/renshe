package com.ciyun.renshe.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.entity.Job;
import com.ciyun.renshe.mapper.JobMapper;
import com.ciyun.renshe.service.JobService;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

}



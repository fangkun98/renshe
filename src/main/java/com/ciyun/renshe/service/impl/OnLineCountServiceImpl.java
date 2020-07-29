package com.ciyun.renshe.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.mapper.OnLineCountMapper;
import com.ciyun.renshe.entity.OnLineCount;
import com.ciyun.renshe.service.OnLineCountService;

@Service
public class OnLineCountServiceImpl extends ServiceImpl<OnLineCountMapper, OnLineCount> implements OnLineCountService {

}



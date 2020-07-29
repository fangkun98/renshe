package com.ciyun.renshe.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.entity.PollChat;
import com.ciyun.renshe.mapper.PollChatMapper;
import com.ciyun.renshe.service.PollChatService;
@Service
public class PollChatServiceImpl extends ServiceImpl<PollChatMapper, PollChat> implements PollChatService{

}

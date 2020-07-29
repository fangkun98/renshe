package com.ciyun.renshe.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.entity.UserChat;
import com.ciyun.renshe.mapper.UserChatMapper;
import com.ciyun.renshe.service.UserChatService;

@Service
public class UserChatServiceImpl extends ServiceImpl<UserChatMapper, UserChat> implements UserChatService {

}



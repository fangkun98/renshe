package com.ciyun.renshe.manager.robot.impl;

import com.alibaba.fastjson.JSON;
import com.ciyun.renshe.manager.robot.ChatRobot;
import com.ciyun.renshe.wsdl.cxf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kys
 * @version 1.0
 * @date 2020/7/9 13:28
 */
@Slf4j
@Service
public class ChatRobotImpl implements ChatRobot {

    /**
     * 向机器人发送相关问题，返回结果
     *
     * @param problem
     * @param userId
     * @return
     */
    @Override
    public String sendMessage(String problem, String userId) {

        URL wsdlURL = RobotServiceEx_Service.WSDL_LOCATION;
        RobotServiceEx_Service ss = new RobotServiceEx_Service(wsdlURL);
        RobotServiceEx port = ss.getDefaultRobotServiceExPort();

        RobotRequest robotRequest = new RobotRequest();
        List<UserAttribute> userAttributes = new ArrayList<>(3);
        userAttributes.add(new UserAttribute("location", "青岛"));
        userAttributes.add(new UserAttribute("platform", "web"));
        userAttributes.add(new UserAttribute("brand", "社企通"));
        robotRequest.setAttributes(userAttributes);
        robotRequest.setMaxReturn(3);
        robotRequest.setQuestion(problem);
        robotRequest.setUserId(userId);
        robotRequest.setSessionId(userId);

        RobotResponse deliver = port.deliver(robotRequest);
        return JSON.toJSONString(deliver);
    }
}

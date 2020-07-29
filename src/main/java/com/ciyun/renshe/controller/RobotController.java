package com.ciyun.renshe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.manager.robot.ChatRobot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author kys
 * @version 1.0
 * @date 2020/7/9 17:53
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/robot")
public class RobotController {

    @Autowired
    private ChatRobot chatRobot;

    /**
     * 根据问题查询答案
     *
     * @return
     */
    @GetMapping("/answer")
    private Result findAnswer(@RequestParam String problem) {
        String s = chatRobot.sendMessage(problem, UUID.randomUUID().toString());
        JSONObject jsonObject = JSON.parseObject(s);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),jsonObject.getString("content") );
    }
}

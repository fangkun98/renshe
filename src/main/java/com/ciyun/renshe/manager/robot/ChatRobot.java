package com.ciyun.renshe.manager.robot;

/**
 * 人设群机器人
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/9 13:25
 */
public interface ChatRobot {

    /**
     * 向机器人发送相关问题，返回结果
     *
     * @param problem 提出的问题
     * @param userId
     * @return
     */
    String sendMessage(String problem,String userId);
}

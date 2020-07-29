package com.ciyun.renshe.websocket;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ciyun.renshe.controller.vo.message.receive.ReturnReceive;
import com.ciyun.renshe.entity.OnLineCount;
import com.ciyun.renshe.entity.OnLineTime;
import com.ciyun.renshe.entity.User;
import com.ciyun.renshe.mapper.ChatAtMapper;
import com.ciyun.renshe.mapper.OnLineCountMapper;
import com.ciyun.renshe.mapper.OnLineTimeMapper;
import com.ciyun.renshe.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date 2020/4/28 11:18
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Component
@CrossOrigin
@ServerEndpoint("/imserver/{userId}")
public class WebSocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    /*private static int onlineCount = 0;*/
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";

    private static ChatAtMapper chatAtMapper;
    private static UserMapper userMapper;
    private static OnLineCountMapper onLineCountMapper;
    private static OnLineTimeMapper onLineTimeMapper;

    @Autowired
    public void setChatAtMapper(ChatAtMapper chatAtMapper, UserMapper userMapper) {
        WebSocketServer.chatAtMapper = chatAtMapper;
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setCountTimeMapper(OnLineCountMapper onLineCountMapper, OnLineTimeMapper onLineTimeMapper) {
        WebSocketServer.onLineCountMapper = onLineCountMapper;
        WebSocketServer.onLineTimeMapper = onLineTimeMapper;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        // 设置连接时长1小时
        session.setMaxIdleTimeout(3600000L);

        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
            //加入set中
        } else {
            webSocketMap.put(userId, this);
            //加入set中
            // addOnlineCount();
            //在线数加1
        }

        log.info("用户连接:" + userId);

        try {
            List<ReturnReceive> chatAts = chatAtMapper.findChatAtByUserId(Integer.parseInt(userId));

            sendMessage("连接成功");
            sendMessage(JSON.toJSONString(chatAts));

            User user = new User();
            user.setUserId(Integer.parseInt(userId));
            // 设置为在线
            user.setIsOnLine(1);
            userMapper.updateById(user);

            // 开始记录上线时间
            /*List<OnLineTime> onLineTimes = onLineTimeMapper.selectList(Wrappers.<OnLineTime>lambdaQuery()
                    .eq(OnLineTime::getUserId, userId).eq(OnLineTime::getCurrentDate, DateUtil.today()));*/
            OnLineTime onLineTime = new OnLineTime();
            onLineTime.setOnLineCurrentDate(DateUtil.today());
            onLineTime.setUserId(Integer.parseInt(userId));
            onLineTime.setOnLineTime(new Date());
            onLineTimeMapper.insert(onLineTime);

        } catch (IOException e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            //subOnlineCount();
        }
        User user = new User();
        user.setUserId(Integer.parseInt(userId));
        // 设置为下线
        user.setIsOnLine(0);
        userMapper.updateById(user);

        // 用户关闭连接时
        // 1. 向记录表中插入关闭时间
        // 2. 向统计表中插入在线时长
        /*List<OnLineTime> onLineTimes = onLineTimeMapper.selectList(Wrappers.<OnLineTime>lambdaQuery()
                .eq(OnLineTime::getOnLineCurrentDate, DateUtil.today())
                .eq(OnLineTime::getUserId, userId)
                .isNull(OnLineTime::getOffLineTime).orderByDesc(OnLineTime::getOnLineId));*/
        String currentDate = DateUtil.today();
        OnLineTime onLineTime = onLineTimeMapper.findOnLineTimeByUserId(Integer.parseInt(userId), currentDate);

        // 如果不为空
        if (onLineTime != null) {
            //OnLineTime onLineTime = onLineTimes.get(0);
            onLineTime.setOffLineTime(new Date());
            onLineTimeMapper.updateById(onLineTime);

            // 将时长进行统计
            List<OnLineCount> onLineCounts = onLineCountMapper.selectList(Wrappers.<OnLineCount>lambdaQuery()
                    .eq(OnLineCount::getUserId, userId)
                    .eq(OnLineCount::getCountCurrentDate, DateUtil.today()));

            // 上线下线两者的时间差
            long betweenMinute = DateUtil.between(onLineTime.getOnLineTime(), onLineTime.getOffLineTime(), DateUnit.MINUTE);
            // 如果查询到不为空，进行更新
            /*if (betweenMinute == 0L) {
                betweenMinute = 1L;
            }*/

            if (CollectionUtil.isNotEmpty(onLineCounts)) {
                OnLineCount onLineCount = onLineCounts.get(0);
                String countTime = onLineCount.getCountTime();
                long beforeBetweenMinute = Long.parseLong(countTime);
                onLineCount.setCountTime(betweenMinute + beforeBetweenMinute + "");
                onLineCountMapper.updateById(onLineCount);
                //onLineCount
            } else {
                // 如果为空，进行插入
                OnLineCount onLineCount = new OnLineCount();
                onLineCount.setUserId(Integer.parseInt(userId));
                onLineCount.setCountTime(betweenMinute + "");
                onLineCount.setCountCurrentDate(new Date());
                onLineCountMapper.insert(onLineCount);
            }
        }

        log.info("用户退出:" + userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("用户Id:{}，报文:{}", userId, message);
        if (message.equals("ping")) {
            sendMessage("pong");
            log.info("响应报文:{}", "pong");
        }
        //可以群发消息
        //消息保存到数据库、redis
        /*if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {
                    log.error("请求的userId:" + toUserId + "不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());

        User user = new User();
        user.setUserId(Integer.parseInt(userId));
        // 设置为下线
        user.setIsOnLine(0);
        userMapper.updateById(user);

        // 用户关闭连接时
        // 1. 向记录表中插入关闭时间
        // 2. 向统计表中插入在线时长
        /*List<OnLineTime> onLineTimes = onLineTimeMapper.selectList(Wrappers.<OnLineTime>lambdaQuery()
                .eq(OnLineTime::getOnLineCurrentDate, DateUtil.today())
                .eq(OnLineTime::getUserId, userId)
                .isNull(OnLineTime::getOffLineTime).orderByDesc(OnLineTime::getOnLineId));*/
        String currentDate = DateUtil.today();
        OnLineTime onLineTime = onLineTimeMapper.findOnLineTimeByUserId(Integer.parseInt(userId), currentDate);

        // 如果不为空
        if (onLineTime != null) {
            //OnLineTime onLineTime = onLineTimes.get(0);
            onLineTime.setOffLineTime(new Date());
            onLineTimeMapper.updateById(onLineTime);

            // 将时长进行统计
            List<OnLineCount> onLineCounts = onLineCountMapper.selectList(Wrappers.<OnLineCount>lambdaQuery()
                    .eq(OnLineCount::getUserId, userId)
                    .eq(OnLineCount::getCountCurrentDate, DateUtil.today()));

            // 上线下线两者的时间差
            long betweenMinute = DateUtil.between(onLineTime.getOnLineTime(), onLineTime.getOffLineTime(), DateUnit.MINUTE);
            // 如果查询到不为空，进行更新
            /*if (betweenMinute == 0L) {
                betweenMinute = 1L;
            }*/

            if (CollectionUtil.isNotEmpty(onLineCounts)) {
                OnLineCount onLineCount = onLineCounts.get(0);
                String countTime = onLineCount.getCountTime();
                long beforeBetweenMinute = Long.parseLong(countTime);
                onLineCount.setCountTime(betweenMinute + beforeBetweenMinute + "");
                onLineCountMapper.updateById(onLineCount);
                //onLineCount
            } else {
                // 如果为空，进行插入
                OnLineCount onLineCount = new OnLineCount();
                onLineCount.setUserId(Integer.parseInt(userId));
                onLineCount.setCountTime(betweenMinute + "");
                onLineCount.setCountCurrentDate(new Date());
                onLineCountMapper.insert(onLineCount);
            }
        }

        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    /*public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }*/

}

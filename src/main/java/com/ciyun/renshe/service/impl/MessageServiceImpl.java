package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.*;
import com.ciyun.renshe.common.dingding.DDErrResult;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.message.DDSendMessageUtils;
import com.ciyun.renshe.common.dingding.message.MessageTypeConvert;
import com.ciyun.renshe.common.dingding.message.param.MessageBody;
import com.ciyun.renshe.common.dingding.message.type.ActionCard;
import com.ciyun.renshe.common.dingding.message.type.Text;
import com.ciyun.renshe.common.dingding.sdk.ChatSDKUtil;
import com.ciyun.renshe.common.dingding.sdk.request.MessageCorpParam;
import com.ciyun.renshe.common.dingding.sdk.request.SendChatMessageParam;
import com.ciyun.renshe.config.DingDingData;
import com.ciyun.renshe.controller.vo.message.NoticeVO;
import com.ciyun.renshe.controller.vo.message.SendChatMessageVo;
import com.ciyun.renshe.controller.vo.message.receive.ReceiveMsgVO;
import com.ciyun.renshe.controller.vo.message.receive.ReturnReceive;
import com.ciyun.renshe.controller.vo.message.receive.SendReceiveMsgVO;
import com.ciyun.renshe.entity.*;
import com.ciyun.renshe.entity.po.MessagePO;
import com.ciyun.renshe.entity.po.NoticePO;
import com.ciyun.renshe.manager.dingding.DingDingChatMessage;
import com.ciyun.renshe.manager.dingding.dto.chat.RobotMsgToChatParam;
import com.ciyun.renshe.manager.robot.ChatRobot;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.scheduled.resendmsg.CronTaskRegistrar;
import com.ciyun.renshe.scheduled.resendmsg.SchedulingRunnable;
import com.ciyun.renshe.service.MessageService;
import com.ciyun.renshe.websocket.WebSocketServer;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationRecallRequest;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service(value = "messageService")
@AllArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    private final NoticeMapper noticeMapper;
    private final NoticeChatMapper noticeChatMapper;
    private final UserMapper userMapper;
    private final UserChatMapper userChatMapper;
    private final ChatAtMapper chatAtMapper;
    private final ChatAtRecordMapper chatAtRecordMapper;
    private final ChatMapper chatMapper;
    private final PollAdminMapper pollAdminMapper;
    private final JobMapper jobMapper;
    private final CronTaskRegistrar cronTaskRegistrar;
    private final ChatRobot chatRobot;
    private final DingDingChatMessage dingDingChatMessage;

    /**
     * 发送工作通知消息
     *
     * @param noticeVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendNotice(NoticeVO noticeVO) {
        if (CollectionUtil.isNotEmpty(noticeVO.getUserIdList())) {

            // 先将数据进行保存到数据库
            Notice notice = new Notice();
            // 1 通知公告  2. 政策资讯
            notice.setType(noticeVO.getType());
            // 1.Text 2.Image 5.ActionCard
            notice.setMsgType(noticeVO.getMessageBody().getMsgType());
            notice.setCreateTime(new Date());
            notice.setFlag(1);
            notice.setLikes(0);
            notice.setReadingNumber(0);
            notice.setCreateUserId(noticeVO.getPermission().getUserId());
            notice.setContent(noticeVO.getContent());
            notice.setUrl(noticeVO.getFileUrl());
            notice.setChatOrUser(2);
            notice.setIsChatMsg(0);
            notice.setTypeClass(noticeVO.getTypeClass() != null ? noticeVO.getTypeClass() : "");

            Integer msgType = noticeVO.getMessageBody().getMsgType();
            if (msgType.equals(5)) {
                notice.setTitle(noticeVO.getMessageBody().getActionCard().getTitle());
            }
            // 如果为 1
            if (msgType.equals(1)) {
                notice.setContent(noticeVO.getMessageBody().getText().getContent());
            }

            notice.setMsgContent(JSON.toJSONString(MessageTypeConvert.sendChatMessage(noticeVO.getMessageBody())));
            // 将通知进行保存
            noticeMapper.insert(notice);

            // 保存并发送钉钉的状态为2
            int saveSend = 2;
            if (noticeVO.getSaveOrSaveSend().equals(saveSend)) {
                // 如果为整体卡片，需要进行封装
                if (msgType.equals(5)) {
                    ActionCard actionCard = noticeVO.getMessageBody().getActionCard();
                    String cardUrl = noticeVO.getRequestUrl() + "/news.html?id=" + notice.getNoticeId();
                    actionCard.setSingleUrl(cardUrl);

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("# ").append(actionCard.getTitle());
                    // 换行前后加两个空格
                    stringBuilder.append("  \n  ");
                    stringBuilder.append("##### ").append(actionCard.getMarkdown());
                    actionCard.setMarkdown(stringBuilder.toString());
                    actionCard.setSingleTitle("点击查看详情");
                    noticeVO.getMessageBody().setActionCard(actionCard);
                }

                if (noticeVO.getUserIdList().size() <= 99) {
                    String join = String.join(",", noticeVO.getUserIdList());
                    MessageCorpParam messageCorpParam = new MessageCorpParam();
                    messageCorpParam.setUserIdList(join);
                    OapiMessageCorpconversationAsyncsendV2Request.Msg messageCorpconvert = MessageTypeConvert.messageCorpconvert(noticeVO.getMessageBody());
                    messageCorpParam.setMsg(messageCorpconvert);
                    try {
                        OapiMessageCorpconversationAsyncsendV2Response response = DDSendMessageUtils.messageCorpconversationAsync(messageCorpParam);
                        if (response.getErrorCode().equals("0")) {

                            List<NoticeChat> noticeChats = new ArrayList<>(noticeVO.getUserIdList().size());
                            for (String ddUserId : noticeVO.getUserIdList()) {
                                // 向中间表存储数据
                                NoticeChat noticeChat = new NoticeChat();
                                noticeChat.setDdUserId(ddUserId);
                                noticeChat.setNoticeId(notice.getNoticeId());
                                noticeChat.setMsgId(response.getTaskId().toString());
                                noticeChats.add(noticeChat);
                            }
                            //noticeChatMapper.insert(noticeChat);
                            new NoticeChatServiceImpl().saveBatch(noticeChats);
                        }
                    } catch (ApiException e) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        e.printStackTrace();
                    }
                } else {
                    List<List<String>> lists = ListUtil.fixedGrouping(noticeVO.getUserIdList(), 99);
                    for (List<String> userIdList : lists) {
                        String join = String.join(",", userIdList);
                        MessageCorpParam messageCorpParam = new MessageCorpParam();
                        messageCorpParam.setUserIdList(join);
                        OapiMessageCorpconversationAsyncsendV2Request.Msg messageCorpconvert = MessageTypeConvert.messageCorpconvert(noticeVO.getMessageBody());
                        messageCorpParam.setMsg(messageCorpconvert);
                        try {
                            OapiMessageCorpconversationAsyncsendV2Response response = DDSendMessageUtils.messageCorpconversationAsync(messageCorpParam);
                            if (response.getErrorCode().equals("0")) {

                                List<NoticeChat> noticeChats = new ArrayList<>(userIdList.size());
                                for (String ddUserId : userIdList) {
                                    // 向中间表存储数据
                                    NoticeChat noticeChat = new NoticeChat();
                                    noticeChat.setDdUserId(ddUserId);
                                    noticeChat.setNoticeId(notice.getNoticeId());
                                    noticeChat.setMsgId(response.getTaskId().toString());
                                    noticeChats.add(noticeChat);
                                }
                                new NoticeChatServiceImpl().saveBatch(noticeChats);
                            }

                        } catch (ApiException e) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 发送群消息
     *
     * @param messageVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendChatMessage(SendChatMessageVo messageVo) {

        if (CollectionUtil.isNotEmpty(messageVo.getChatIds())) {

            // 先将数据进行保存到数据库
            Notice notice = new Notice();
            // 1 通知公告  2. 政策资讯
            notice.setType(messageVo.getType());
            // 1.Text 2.Image 5.ActionCard
            notice.setMsgType(messageVo.getMessageBody().getMsgType());
            notice.setCreateTime(new Date());
            notice.setLikes(0);
            notice.setFlag(1);
            notice.setReadingNumber(0);
            notice.setContent(messageVo.getContent());
            notice.setCreateUserId(messageVo.getUserId());
            notice.setUrl(messageVo.getFileUrl());
            notice.setChatOrUser(1);
            notice.setTypeClass(messageVo.getTypeClass() != null ? messageVo.getTypeClass() : "");
            // 如果重发时间不为空
            if (StringUtils.isNotBlank(messageVo.getReSendTime())) {
                notice.setIsResend(0);
                notice.setResendTime(DateUtil.parseDate(messageVo.getReSendTime()));
            }

            Integer msgType = messageVo.getMessageBody().getMsgType();
            if (msgType.equals(5)) {
                notice.setTitle(messageVo.getMessageBody().getActionCard().getTitle());
            }
            // 如果为 1
            if (msgType.equals(1)) {
                notice.setContent(messageVo.getMessageBody().getText().getContent());
            }

            // 设置为群消息
            notice.setIsChatMsg(1);
            notice.setMsgContent(JSON.toJSONString(MessageTypeConvert.sendChatMessage(messageVo.getMessageBody())));
            noticeMapper.insert(notice);

            // 如果重发时间不为空
            if (StringUtils.isNotBlank(messageVo.getReSendTime())) {
                // 将消息保存到定时任务表
                Job job = new Job();

                job.setBeanName("messageService");
                job.setMethodName("reSendMsg");
                job.setMethodParams("");

                job.setJobStatus(1);
                job.setCreateTime(new Date());

                DateTime parse = DateUtil.parse(messageVo.getReSendTime(), "yyyy-MM-dd HH:mm:ss");
                String s = CronUtils.formatDateByPattern(parse);
                job.setCronExpression(s);
                job.setExecuteTime(parse);
                job.setNoticeId(notice.getNoticeId());
                jobMapper.insert(job);

                cronTaskRegistrar.addCronTask(new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams()), job.getCronExpression());
            }

            // 如果为整体卡片，需要进行封装
            if (msgType.equals(5)) {
                ActionCard actionCard = messageVo.getMessageBody().getActionCard();
                String cardUrl = messageVo.getRequestUrl() + "/news.html?id=" + notice.getNoticeId();
                actionCard.setSingleUrl(cardUrl);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("# ").append(actionCard.getTitle());
                // 换行前后加两个空格
                stringBuilder.append("  \n  ");
                stringBuilder.append("##### ").append(actionCard.getMarkdown());
                actionCard.setMarkdown(stringBuilder.toString());
                actionCard.setSingleTitle("点击查看详情");
                messageVo.getMessageBody().setActionCard(actionCard);

                notice.setMsgContent(JSON.toJSONString(MessageTypeConvert.sendChatMessage(messageVo.getMessageBody())));
                // 将通知进行保存
                noticeMapper.updateById(notice);
            }

            int saveSend = 2;

            for (String chatId : messageVo.getChatIds()) {
                //
                SendChatMessageParam param = new SendChatMessageParam().setChatId(chatId);
                OapiChatSendRequest.Msg msg = MessageTypeConvert.sendChatMessage(messageVo.getMessageBody());
                log.info("msg:{}", JSON.toJSONString(msg));
                param.setMsg(msg);

                OapiChatSendResponse response = null;
                try {
                    if (messageVo.getSaveOrSaveSend().equals(saveSend)) {
                        response = DDSendMessageUtils.sendChatMessage(param);
                    }
                    // 向中间表存储数据
                    NoticeChat noticeChat = new NoticeChat();
                    noticeChat.setChatId(chatId);
                    noticeChat.setNoticeId(notice.getNoticeId());
                    if (response != null) {
                        noticeChat.setMsgId(response.getMessageId());
                    } else {
                        noticeChat.setMsgId("");
                    }
                    noticeChat.setIsAllRead(0);
                    noticeChatMapper.insert(noticeChat);

                } catch (ApiException e) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 查询已读人员列表
     *
     * @param messageId
     * @return
     */
    @Override
    public Result getGetReadList(Integer noticeId, String messageId) {

        Result result = new Result(MessageInfo.GET_INFO.getInfo());

        List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                .eq(NoticeChat::getNoticeId, noticeId));
        log.info("noticeChats{}", JSONObject.toJSONString(noticeChats));

        Set<User> unReadUser = new HashSet<>();
        Set<User> readUser = new HashSet<>();
        int readCount = 0;
        int unReadCount = 0;

        if (CollectionUtil.isNotEmpty(noticeChats)) {
            List<String> chatIdList = new ArrayList<>(10);
            List<String> msgIdList = new ArrayList<>(10);

            for (NoticeChat noticeChat : noticeChats) {
                chatIdList.add(noticeChat.getChatId());
                msgIdList.add(noticeChat.getMsgId());
            }

            for (int i = 0; i < chatIdList.size(); i++) {

                OapiChatGetResponse response = null;
                try {
                    response = ChatSDKUtil.getChatInfo(chatIdList.get(i));
                } catch (ApiException e) {
                    e.printStackTrace();
                }

                //log.info("");
                // 群全部的成员列表
                List<String> useridlist = response.getChatInfo().getUseridlist();

                OapiChatGetReadListResponse readList = null;
                try {
                    readList = ChatSDKUtil.getReadList(msgIdList.get(i), 0L, 100L);
                } catch (ApiException e) {

                    e.printStackTrace();
                }

                // 获取全部的已读
                List<String> readUserIdList = readList.getReadUserIdList();

                Map<String, Object> map = new HashMap<>(16);

                // 全部的已读不为空，
                if (CollectionUtil.isNotEmpty(readUserIdList)) {

                    // 所有的已读人员信息
                    List<User> alreadyRead = userMapper.selectList(Wrappers.<User>lambdaQuery().in(User::getDdUserId, readUserIdList).eq(User::getFlag, 1));
                    map.put("alreadyRead", alreadyRead);
                    readUser.addAll(alreadyRead);

                    // 将已经阅读的去掉，得到未读用户
                    useridlist.removeAll(readUserIdList);

                    if (useridlist.isEmpty()) {
                        map.put("unread", new ArrayList<>());
                        map.put("unreadCount", 0);
                    } else {
                        List<User> unread = userMapper.selectList(Wrappers.<User>lambdaQuery().in(User::getDdUserId, useridlist).eq(User::getFlag, 1));
                        map.put("unread", unread);
                        unReadUser.addAll(unread);

                        // 全部未读数量
                        Integer unreadCount = userMapper.selectCount(Wrappers.<User>lambdaQuery().in(User::getDdUserId, useridlist).eq(User::getFlag, 1));
                        map.put("unreadCount", unreadCount);

                        unReadCount = unReadCount + unreadCount;
                    }

                    // 全部已读数量
                    Integer alreadyReadCount = userMapper.selectCount(Wrappers.<User>lambdaQuery().in(User::getDdUserId, readUserIdList).eq(User::getFlag, 1));
                    map.put("alreadyReadCount", alreadyReadCount);
                    readCount = readCount + alreadyReadCount;

                } else {
                    // 如果已读为空，说明全部未读
                    List<User> unread = userMapper.selectList(Wrappers.<User>lambdaQuery().in(User::getDdUserId, useridlist).eq(User::getFlag, 1));
                    map.put("unread", unread);
                    Integer unreadCount = userMapper.selectCount(Wrappers.<User>lambdaQuery().in(User::getDdUserId, useridlist).eq(User::getFlag, 1));
                    map.put("unreadCount", unreadCount);

                    // 已读列表
                    map.put("alreadyRead", new ArrayList<>());

                    // 已读总数
                    map.put("alreadyReadCount", 0);

                    unReadUser.addAll(unread);
                    unReadCount = unReadCount + unreadCount;
                }
            }
        }

        Map<String, Object> map = new LinkedHashMap<>(10);
        map.put("unReadUser", unReadUser);
        map.put("readUser", readUser);
        map.put("readCount", readCount);
        map.put("unReadCount", unReadCount);

        result.setData(map);

        return result;
    }

    /**
     * 管理端获取消息历史
     *
     * @param page
     * @param chatOrUser
     * @param title
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    @Override
    public Result getMessageHistory(Page page, Integer chatOrUser, String title, String startDate, String endDate, Integer type) {

        if (StringUtils.isNotBlank(startDate)) {
            startDate = startDate + " 00:00:00";
        }

        if (StringUtils.isNotBlank(endDate)) {
            endDate = endDate + "23:59:59";
        }

        PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue());
        List<NoticePO> messageHistory = noticeMapper.getMessageHistory(title, chatOrUser, startDate, endDate, type);
        PageInfo<NoticePO> pageInfo = new PageInfo<>(messageHistory);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), new PageResult(pageInfo.getTotal(), pageInfo.getList()));
    }

    /**
     * 小程序查询全部历史记录
     *
     * @param page
     * @param userId
     * @param type
     * @return
     */
    @Override
    public Result getMessageHistory2DD(Page page, Integer userId, Integer type, String classType) {

        // 先得到所有的群id
        List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                .eq(UserChat::getUserId, userId));
        List<String> chatIds = new ArrayList<>(userChats.size());
        userChats.forEach(userChat -> chatIds.add(userChat.getChatId()));

        if (CollectionUtil.isNotEmpty(chatIds)) {
            // 从群中得到所有的 通知消息
            List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                    .in(NoticeChat::getChatId, chatIds));
            List<Integer> noticeIds = new ArrayList<>(noticeChats.size());
            noticeChats.forEach(noticeChat -> noticeIds.add(noticeChat.getNoticeId()));

            if (type != null && type != 3) {
                if (CollectionUtil.isNotEmpty(noticeIds)) {

                    LambdaQueryWrapper<Notice> lambda = new QueryWrapper<Notice>().lambda();
                    lambda.in(Notice::getNoticeId, noticeIds)
                            .eq(Notice::getFlag, 1)
                            .eq(Notice::getType, type);
                    // 判断是否为空
                    boolean classTypeFlag = StringUtils.isNotBlank(classType);
                    // 如果不为空并且不是全部字段
                    if (classTypeFlag && !classType.equals("全部")) {
                        lambda.eq(Notice::getTypeClass, classType);
                    }
                    lambda.orderByDesc(Notice::getCreateTime);
                    PageInfo<Object> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                            .doSelectPageInfo(() -> noticeMapper.selectList(lambda));
                    return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                            new PageResult(pageInfo.getTotal(), pageInfo.getList()));
                } else {
                    return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                            new PageResult(0L, new ArrayList<>()));
                }
            } else {

                //LambdaQueryWrapper lambda = new QueryWrapper(Notice.class).<Notice>lambda();

                LambdaQueryWrapper<Notice> lambda = new QueryWrapper<Notice>().lambda();
                lambda.eq(Notice::getFlag, 1)
                        .eq(Notice::getType, type)
                        .orderByDesc(Notice::getCreateTime);

                PageInfo<Object> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                        .doSelectPageInfo(() -> noticeMapper.selectList(lambda));
                return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                        new PageResult(pageInfo.getTotal(), pageInfo.getList()));
            }
        } else {
            return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                    new PageResult(0L, new ArrayList<>()));
        }

    }

    /**
     * 查看最新的5条数据
     *
     * @param userId
     * @return
     */
    @Override
    public Result getMessageHistory2DD(Integer userId) {

        Page page = new Page(1L, 5L);

        // 先得到所有的群id
        List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery().eq(UserChat::getUserId, userId));
        List<String> chatIds = new ArrayList<>(userChats.size());
        userChats.forEach(userChat -> chatIds.add(userChat.getChatId()));

        if (CollectionUtil.isNotEmpty(chatIds)) {
            // 从群中得到所有的 通知消息
            List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                    .in(NoticeChat::getChatId, chatIds));
            List<Integer> noticeIds = new ArrayList<>(noticeChats.size());
            noticeChats.forEach(noticeChat -> noticeIds.add(noticeChat.getNoticeId()));

            if (CollectionUtil.isNotEmpty(noticeIds)) {

                log.info("noticeIds:{}", JSON.toJSONString(noticeIds));
                PageInfo<Notice> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                        .doSelectPageInfo(() -> noticeMapper.selectList(Wrappers.<Notice>lambdaQuery()
                                .in(Notice::getNoticeId, noticeIds)
                                .eq(Notice::getFlag, 1)
                                .eq(Notice::getType, 1)
                                .in(Notice::getMsgType, 1, 5)
                                .orderByDesc(Notice::getCreateTime)));

                List<Map<String, Object>> list = new ArrayList<>(5);
                for (Notice noticeInfo : pageInfo.getList()) {
                    Map<String, Object> map = new HashMap<>(6);
                    map.put("msgType", noticeInfo.getMsgType());
                    if (noticeInfo.getMsgType().equals(1)) {
                        // 说明为 文本消息
                        map.put("noticeId", noticeInfo.getNoticeId());
                        map.put("content", noticeInfo.getContent());
                        list.add(map);
                    } else {
                        map.put("noticeId", noticeInfo.getNoticeId());
                        map.put("content", noticeInfo.getTitle());
                        list.add(map);
                    }
                }

                return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                        new PageResult(pageInfo.getTotal(), list));
            } else {
                return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                        new PageResult(0L, new ArrayList<>()));
            }

        } else {
            return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                    new PageResult(0L, new ArrayList<>()));
        }

    }

    /**
     * 接收来自钉钉推送的数据
     *
     * @param receiveMsgVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveMsg(ReceiveMsgVO receiveMsgVO) {

        // 先查询是否是机器人回答
        String content = receiveMsgVO.getText().getContent();
        if (StringUtils.isNotBlank(content)) {
            // 如果不包含人工，说明走机器人自动回复
            if (!content.contains("人工")) {
                String robotMessage = chatRobot.sendMessage(content, receiveMsgVO.getSenderStaffId());
                log.info("机器人返回的消息为：{}", robotMessage);
                JSONObject jsonObject = JSON.parseObject(robotMessage);
                RobotMsgToChatParam robotMsgToChatParam = new RobotMsgToChatParam();
                robotMsgToChatParam.setAnswerContent(jsonObject.getString("content"));
                robotMsgToChatParam.setDdUserId(receiveMsgVO.getSenderStaffId());
                robotMsgToChatParam.setRelatedQuestion(jsonObject.getObject("relatedQuestions", List.class));
                robotMsgToChatParam.setSessionWebhook(receiveMsgVO.getSessionWebhook());
                dingDingChatMessage.sendRobotMsgToChat(robotMsgToChatParam);
                return;
            }
        }

        // 得到具体将消息发送给哪个管理员
        //
        //Integer adminId = 1;
        // 查询这个群的所有管理员
        Chat chatInfo = findChatByChatName(receiveMsgVO.getConversationTitle());
        Integer adminId = pollingAdminId(chatInfo.getManagerUser(), chatInfo.getChatId());

        // 查询是否已经有过历史记录
        List<ChatAt> chatAts = chatAtMapper.selectList(Wrappers.<ChatAt>lambdaQuery()
                //.eq(ChatAt::getReceivingUserId, userByDDUserId.getUserId())
                .eq(ChatAt::getSenderStaffId, receiveMsgVO.getSenderStaffId())
                .eq(ChatAt::getConversationTitle, receiveMsgVO.getConversationTitle())
                .eq(ChatAt::getFlag, 1)
                .eq(ChatAt::getIsClose, 1));

        // 如果不为空，将接收到的信息追加到记录表，而不添加到 dd_chat_at 表
        if (CollectionUtil.isNotEmpty(chatAts)) {
            // 向记录表插入数据
            ChatAtRecord chatAtRecord = new ChatAtRecord();
            chatAtRecord.setAtId(chatAts.get(0).getAtId());
            chatAtRecord.setCreateTime(new Date());
            chatAtRecord.setFlag(1);
            chatAtRecord.setMessageContent(receiveMsgVO.getText().getContent());
            chatAtRecord.setIsRead(2);

            // 查找对应的用户
            User sendUserInfo = userMapper.findUserByDDUserId(receiveMsgVO.getSenderStaffId());

            chatAtRecord.setUserId(sendUserInfo.getUserId());
            // 1 为提问者
            chatAtRecord.setQuestionOrAnswer(1);
            chatAtRecord.setChatName(receiveMsgVO.getConversationTitle());
            chatAtRecordMapper.insert(chatAtRecord);

            // 更新一下 dd_chat_at 的webhook地址，因为有过期时间
            ChatAt chatAt = new ChatAt();
            chatAt.setAtId(chatAts.get(0).getAtId());
            chatAt.setSessionWebhook(receiveMsgVO.getSessionWebhook());
            chatAt.setIsRead(2);
            chatAt.setSessionWebhookExpiredTime(receiveMsgVO.getSessionWebhookExpiredTime());
            chatAtMapper.updateById(chatAt);
        } else {
            ChatAt chatAt = new ChatAt();
            BeanUtils.copyProperties(receiveMsgVO, chatAt);
            chatAt.setMsgContent(receiveMsgVO.getText().getContent());
            chatAt.setFlag(1);
            chatAt.setCreateTime(new Date());
            chatAt.setIsClose(1);
            chatAt.setMsgFlag(1);
            chatAt.setIsRead(2);

            // 接收此管理员的id
            chatAt.setReceivingUserId(adminId.toString());
            chatAtMapper.insert(chatAt);

            String sendUserId = receiveMsgVO.getSenderStaffId();
            // 查找对应的用户
            User sendUserInfo = userMapper.findUserByDDUserId(sendUserId);

            // 向记录表插入数据
            ChatAtRecord chatAtRecord = new ChatAtRecord();
            chatAtRecord.setAtId(chatAt.getAtId());
            chatAtRecord.setCreateTime(new Date());
            chatAtRecord.setFlag(1);
            chatAtRecord.setMessageContent(chatAt.getMsgContent());
            chatAtRecord.setUserId(sendUserInfo.getUserId());
            chatAtRecord.setIsRead(2);
            // 1 为提问者
            chatAtRecord.setQuestionOrAnswer(1);
            chatAtRecord.setChatName(chatAt.getConversationTitle());
            chatAtRecordMapper.insert(chatAtRecord);
        }

        List<ReturnReceive> chatAtList = chatAtMapper.findChatAtByUserId(adminId);

        try {
            WebSocketServer.sendInfo(JSON.toJSONString(chatAtList), adminId.toString());
        } catch (IOException e) {
            // 消息发送失败
            e.printStackTrace();
        }

    }

    /**
     * 根据群名称查询群信息
     *
     * @param chatName
     * @return
     */
    private Chat findChatByChatName(String chatName) {
        List<Chat> chatList = chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                .select(Chat::getManagerUser, Chat::getChatId)
                .eq(Chat::getName, chatName));
        if (CollectionUtil.isNotEmpty(chatList)) {
            return chatList.get(0);
        } else {
            throw new RuntimeException("未查询到对应的群信息");
        }

    }

    /**
     * 给机器人发送消息
     *
     * @param userId
     * @param content 问题
     */
    /*private String sendRobotMessage(String userId, String content) {
        URL wsdlURL = RobotServiceEx_Service.WSDL_LOCATION;
        RobotServiceEx_Service ss = new RobotServiceEx_Service(wsdlURL);
        RobotServiceEx port = ss.getDefaultRobotServiceExPort();

        RobotRequest robotRequest = new RobotRequest();
        List<UserAttribute> userAttributes = new ArrayList<>(3);
        userAttributes.add(new UserAttribute("location", "青岛"));
        userAttributes.add(new UserAttribute("platform", "web"));
        userAttributes.add(new UserAttribute("brand", "社企通"));
        robotRequest.setAttributes(userAttributes);
        robotRequest.setMaxReturn(5);
        robotRequest.setQuestion(content);
        robotRequest.setUserId(userId);
        robotRequest.setSessionId(userId);

        RobotResponse deliver = port.deliver(robotRequest);
        String robotContent = deliver.getContent();
        return robotContent;
    }*/

    /**
     * 机器人回复消息发送到群中
     */
    private void sendChatMessageOfRobot(String content, String chatId) {
        MessageBody messageBody = new MessageBody();
        messageBody.setText(new Text(content));
        messageBody.setMsgType(1);
        OapiChatSendRequest.Msg msg = MessageTypeConvert.sendChatMessage(messageBody);

        SendChatMessageParam param = new SendChatMessageParam(chatId, msg);
        try {
            DDSendMessageUtils.sendChatMessage(param);
        } catch (ApiException e) {
            log.warn("向 {} 群发送消息失败,内容为：{}", chatId, e.getErrMsg());
        }
    }

    private Integer pollingAdminId(String managerUser, String chatId) {

        Integer adminId = 0;

        List managerList = JSON.parseObject(managerUser, List.class);
        // 如果只有一个管理员直接赋值
        if (managerList.size() == 1) {
            String ddUserId = (String) managerList.get(0);
            User userByDDUserId = userMapper.findUserByDDUserId(ddUserId);
            adminId = userByDDUserId.getUserId();
        } else {
            // 如果管理员有多个
            List<PollAdmin> pollAdmins = pollAdminMapper.selectList(Wrappers.<PollAdmin>lambdaQuery()
                    .eq(PollAdmin::getChatId, chatId));

            if (CollectionUtil.isNotEmpty(pollAdmins)) {
                PollAdmin pollAdmin = pollAdmins.get(0);
                // 查询在线管理员
                List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery()
                        .in(User::getDdUserId, managerList)
                        .eq(User::getIsOnLine, 1));
                // 如果存在在线用户
                if (CollectionUtil.isNotEmpty(users)) {
                    // 如果只存在一个用户，直接将此用户赋值到 adminId
                    if (users.size() == 1) {
                        adminId = users.get(0).getUserId();
                    } else {
                        // 如果为多个用户同时在线
                        //PollAdmin pollAdmin = new PollAdmin();

                        // 设置总在线数
                        pollAdmin.setPollCount(users.size());

                        // 如果下一个数据大于总在线数，置位1
                        if (users.size() < pollAdmin.getNextNum()) {
                            pollAdmin.setNextNum(1);
                        }

                        // 得到对应的数据
                        User user = users.get(pollAdmin.getNextNum() - 1);
                        adminId = user.getUserId();

                        // 将数据加1
                        pollAdmin.setNextNum(pollAdmin.getNextNum() + 1);
                        pollAdminMapper.updateById(pollAdmin);

                    }
                } else {

                    // 查询所有该群的管理员，不论是否在线
                    List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery()
                            .in(User::getDdUserId, managerList));

                    // 如果在线人数为空的话，也是轮询给某一个用户
                    // 设置总在线数
                    pollAdmin.setPollCount(userList.size());

                    // 如果下一个数据大于总在线数，置位1
                    if (userList.size() < pollAdmin.getNextNum()) {
                        pollAdmin.setNextNum(1);
                    }

                    // 得到对应的数据
                    User user = users.get(pollAdmin.getNextNum() - 1);
                    adminId = user.getUserId();

                    // 将数据加1
                    pollAdmin.setNextNum(pollAdmin.getNextNum() + 1);
                    pollAdminMapper.updateById(pollAdmin);
                }
            } else {
                log.info("未查询到管理员轮询表中的数据");
            }

        }

        // 如果出错，直接停止
        if (adminId == 0) {
            log.warn("管理员轮询出现错误");
            throw new RuntimeException("管理员轮询出现错误");
        }

        return adminId;
    }

    /**
     * 发送群中@的消息
     *
     * @param sendReceiveMsgVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result sendReceiveMsg(SendReceiveMsgVO sendReceiveMsgVO) {

        Result<Object> result = new Result<>(MessageInfo.DD_SEND_MSG_SUCCESS.getInfo());

        String sessionWebhook = sendReceiveMsgVO.getSessionWebhook();
        // 得到消息发送地址
        String msgUrl = sessionWebhook + "&access_token=" + DingDingUtil.ACCESS_TOKEN;
        log.info("消息发送的地址为：{}" + msgUrl);
        DefaultDingTalkClient client = new DefaultDingTalkClient(msgUrl);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(sendReceiveMsgVO.getMessageContent());
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();

        // 根据 atId 查询到对应@机器人的用户的手机号
        ChatAt chatAt = chatAtMapper.selectById(sendReceiveMsgVO.getAtId());
        User user = userMapper.findUserByDDUserId(chatAt.getSenderStaffId());
        if (user != null) {
            at.setAtMobiles(Arrays.asList(user.getMobile()));
        }
        request.setAt(at);
        try {
            OapiRobotSendResponse response = client.execute(request);

            if (response.getErrcode().equals(0L)) {

                log.info("消息发送返回的结果为：{}", JSON.toJSONString(response));
                ChatAtRecord chatAtRecord = new ChatAtRecord();
                chatAtRecord.setChatName(chatAt.getConversationTitle());
                chatAtRecord.setQuestionOrAnswer(2);
                chatAtRecord.setUserId(sendReceiveMsgVO.getUserId());
                chatAtRecord.setFlag(1);
                chatAtRecord.setCreateTime(new Date());
                chatAtRecord.setIsRead(1);
                chatAtRecord.setMessageContent(sendReceiveMsgVO.getMessageContent());
                chatAtRecord.setAtId(sendReceiveMsgVO.getAtId());
                chatAtRecordMapper.insert(chatAtRecord);

                // 将消息修改为已回复
                chatAt.setMsgFlag(2);
                // 设置消息回复时间
                if (chatAt.getRecordTime() == null) {
                    chatAt.setRecordTime(new Date());
                }
                chatAtMapper.updateById(chatAt);

                return result;
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }

        } catch (ApiException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return DDErrResult.connException(result);
        }

        //List<ReturnReceive> chatAtByUserId = chatAtMapper.findChatAtByUserId(sendReceiveMsgVO.getUserId());
    }

    /**
     * 查询@机器人回复消息
     *
     * @param page
     * @param userId
     * @return
     */
    @Override
    public Result getReceiveMsgHistory(Page page, Integer userId) {
        Result<Object> result = new Result<>(MessageInfo.GET_INFO.getInfo());
        //List<ReturnReceive> chatAtByUserId = chatAtMapper.findChatAtByUserId(userId);
        // 查询全部历史数据
        PageInfo<ReturnReceive> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> chatAtMapper.findAllChatAtByUserId(userId));
        //List<ReturnReceive> chatAtByUserId = chatAtMapper.findAllChatAtByUserId(userId);

        return result.setData(new PageResult(pageInfo.getTotal(), pageInfo.getList()));
    }

    /**
     * 修改会话状态
     *
     * @param atId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setReceiveMsgClose(Integer atId) {
        ChatAt chatAt = new ChatAt();
        chatAt.setIsClose(2);
        chatAt.setAtId(atId);
        chatAtMapper.updateById(chatAt);
    }

    /**
     * 设置消息为已读
     *
     * @param atId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setMsgRead(Integer atId) {
        ChatAt chatAt = new ChatAt();
        chatAt.setAtId(atId);
        chatAt.setIsRead(1);
        chatAtMapper.updateById(chatAt);

        ChatAtRecord chatAtRecord = new ChatAtRecord();
        chatAtRecord.setIsRead(1);
        chatAtRecordMapper.update(chatAtRecord,
                Wrappers.<ChatAtRecord>lambdaQuery().eq(ChatAtRecord::getAtId, atId));
    }

    @Override
    public void reSendMsg() throws ApiException {

        log.info("执行了 reSendMsg 方法");

        // 查询出所有要定时发送的消息
        // is_resend = 0 和 resend_time != null  chat_or_user = 1
        List<Notice> notices = noticeMapper.selectList(Wrappers.<Notice>lambdaQuery()
                .ge(Notice::getResendTime, DateUtil.today())
                .eq(Notice::getIsResend, 0)
                .eq(Notice::getChatOrUser, 1)
                .isNotNull(Notice::getResendTime));
        for (Notice notice : notices) {
            if (notice.getMsgType().equals(1)
                    || notice.getMsgType().equals(5)) {
                // 查询中间表 得到群id以及消息id
                List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.lambdaQuery(NoticeChat.class)
                        .eq(NoticeChat::getNoticeId, notice.getNoticeId()));
                for (NoticeChat noticeChat : noticeChats) {

                    // 全部群成员
                    List<String> ddUserIdList = new ArrayList<>();
                    // 已读人员
                    List<String> readUserIdList = new ArrayList<>();
                    // 查询群成员
                    OapiChatGetResponse chatInfo = ChatSDKUtil.getChatInfo(noticeChat.getChatId());
                    OapiChatGetReadListResponse readList = ChatSDKUtil.getReadList(noticeChat.getMsgId(), 0L, 100L);

                    if (chatInfo != null && chatInfo.getErrcode().equals(0L))
                        // 获取到群成员信息
                        ddUserIdList = chatInfo.getChatInfo().getUseridlist();
                    if (readList != null && readList.getErrcode().equals(0L)) {
                        if (readList.getReadUserIdList() != null) {
                            // 获取到已读信息用户id
                            readUserIdList = readList.getReadUserIdList();
                        }

                    }

                    log.info("群成员列表：{}", JSON.toJSONString(ddUserIdList));
                    log.info("已读人员：{}", JSON.toJSONString(readUserIdList));
                    // 得到未读人员
                    if (CollectionUtil.isNotEmpty(ddUserIdList) && CollectionUtil.isNotEmpty(readUserIdList)) {
                        ddUserIdList.removeAll(readUserIdList);

                        log.info("未读人员{}", JSON.toJSONString(ddUserIdList));
                        // 判断未读人员列表是否还有值
                        if (CollectionUtil.isNotEmpty(ddUserIdList)) {
                            /*List<User> users = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                                    .select(User::getMobile)
                                    .in(User::getDdUserId, ddUserIdList));*/
                            /*// 得到所有未读的手机号
                            List<String> mobileList = new ArrayList<>(users.size());
                            users.forEach(user -> mobileList.add(user.getMobile()));*/

                            // 发送群消息
                            //notice.get
                            /*String url = "https://oapi.dingtalk.com/robot/send?access_token=" + DingDingUtil.ACCESS_TOKEN;
                            DingTalkClient client = new DefaultDingTalkClient(url);
                            OapiRobotSendRequest request = new OapiRobotSendRequest();*/

                            MessageCorpParam param = new MessageCorpParam();

                            String join = String.join(",", ddUserIdList);
                            param.setUserIdList(join);
                            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
                            // 如果为text
                            if (notice.getMsgType().equals(1)) {
                                msg.setMsgtype("text");
                                msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
                                msg.getText().setContent(notice.getContent());
                                param.setMsg(msg);
                            }
                            //如果为图文
                            if (notice.getMsgType().equals(5)) {
                                /*MessageBody messageBody = JSON.parseObject(notice.getMsgContent(), MessageBody.class);
                                log.info("messageBody的值为：{}", JSON.toJSONString(messageBody));
                                request.setMsgtype("markdown");
                                OapiRobotSendRequest.Actioncard actionCard = new OapiRobotSendRequest.Actioncard();
                                ActionCard ac = messageBody.getActionCard();
                                actionCard.setTitle(ac.getTitle());
                                actionCard.setText(ac.getMarkdown());
                                actionCard.setBtnOrientation("0");
                                actionCard.setSingleTitle(ac.getSingleTitle());
                                actionCard.setSingleURL(ac.getSingleUrl());*/

                                MessagePO messagePO = JSON.parseObject(notice.getMsgContent(), MessagePO.class);
                                ActionCard actionCard = messagePO.getActionCard();
                                msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
                                msg.getActionCard().setTitle(actionCard.getTitle());
                                msg.getActionCard().setMarkdown(actionCard.getMarkdown());
                                msg.getActionCard().setSingleTitle(actionCard.getSingleTitle());
                                msg.getActionCard().setSingleUrl(actionCard.getSingleUrl());
                                msg.setMsgtype("action_card");
                                param.setMsg(msg);
                            }
                            OapiMessageCorpconversationAsyncsendV2Response response = DDSendMessageUtils.messageCorpconversationAsync(param);
                            if (response.getErrcode().equals(0L)) {
                                log.info("定时重发消息成功，noticeId为：{}", notice.getNoticeId());

                                // 消息发送成功后改变状态
                                notice.setResendTime(new Date());
                                notice.setIsResend(1);
                                noticeMapper.updateById(notice);
                            }
                        }
                    }

                }
            }

        }
    }

    /**
     * 个人消息撤回
     *
     * @param
     * @return
     */
    @Override
    public Result setMsgReCall(Integer noticeId) {
        Result<Object> result = new Result<>("消息撤回成功");

        List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                .eq(NoticeChat::getNoticeId, noticeId));

        for (NoticeChat noticeChat : noticeChats) {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/recall");
            OapiMessageCorpconversationRecallRequest request = new OapiMessageCorpconversationRecallRequest();
            request.setAgentId(Long.parseLong(DingDingData.AGENT_ID.getValue()));
            request.setMsgTaskId(Long.parseLong(noticeChat.getMsgId()));
            try {
                client.execute(request, DingDingUtil.ACCESS_TOKEN);
                cancelNotice(noticeId);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 消息撤销操作
     *
     * @param noticeId
     */
    private void cancelNotice(Integer noticeId) {
        Notice notice = new Notice();
        notice.setNoticeId(noticeId);
        // 0 为撤销
        notice.setNoticeCancel(0);
        noticeMapper.updateById(notice);
    }

    /**
     * 删除通知消息
     *
     * @param noticeId
     * @return
     */
    @Override
    public Result deleteNotice(Integer noticeId) {

        Result<Object> result = new Result<>(MessageInfo.DELETE_INFO.getInfo());
        Notice notice = new Notice();
        notice.setNoticeId(noticeId);
        notice.setFlag(0);
        noticeMapper.updateById(notice);

        noticeChatMapper.delete(Wrappers.<NoticeChat>lambdaQuery()
                .eq(NoticeChat::getNoticeId, noticeId));
        return result;
    }
}



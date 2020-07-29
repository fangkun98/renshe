package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ciyun.renshe.common.dingding.message.DDSendMessageUtils;
import com.ciyun.renshe.common.dingding.sdk.ChatSDKUtil;
import com.ciyun.renshe.common.dingding.sdk.request.MessageCorpParam;
import com.ciyun.renshe.entity.Notice;
import com.ciyun.renshe.entity.NoticeChat;
import com.ciyun.renshe.entity.User;
import com.ciyun.renshe.entity.UserChat;
import com.ciyun.renshe.entity.po.ReadAndUnreadNum;
import com.ciyun.renshe.mapper.NoticeChatMapper;
import com.ciyun.renshe.mapper.NoticeMapper;
import com.ciyun.renshe.mapper.UserChatMapper;
import com.ciyun.renshe.mapper.UserMapper;
import com.ciyun.renshe.service.ScheduledService;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiChatGetResponse;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Date 2020/5/12 9:18
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class ScheduledServiceImpl implements ScheduledService {

    private final UserMapper userMapper;
    private final UserChatMapper userChatMapper;
    private final NoticeMapper noticeMapper;
    private final NoticeChatMapper noticeChatMapper;

    /**
     * 给用户在不同群中的人员发送换群通知
     */
    @Override
    public void changeChatUser() throws ApiException {
        // 查询出已经是钉钉用户不是管理员的公司
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery()
                .select(User::getCompanyName)
                .eq(User::getFlag, 1)
                .eq(User::getIsConfirm, 1)
                .eq(User::getIsAdmin, 0)
                .ne(User::getIsInnerUser,1)
                .groupBy(User::getCompanyName));

        for (User user : users) {
            String companyName = user.getCompanyName();

            // 得到所有不是管理员的用户信息
            List<User> usersInfo = userMapper.selectList(Wrappers.<User>lambdaQuery()
                    .select(User::getUserId, User::getDdUserId, User::getName)
                    .eq(User::getFlag, 1)
                    .eq(User::getIsConfirm, 1)
                    .eq(User::getIsAdmin, 0)
                    .eq(User::getCompanyName, companyName));
            // 如果企业人数大于 2
            if (CollectionUtil.isNotEmpty(usersInfo) && usersInfo.size() >= 2) {
                // 得到用户的id
                List<Integer> userIdList = new ArrayList<>(usersInfo.size());
                usersInfo.forEach(u -> userIdList.add(u.getUserId()));

                // 查询用户群聊中间表, 排除临时群
                List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                        .in(UserChat::getUserId, userIdList)
                        .isNotNull(UserChat::getStreetId));

                // 得到所有的群id
                Set<String> chatIdSet = new HashSet<>(10);
                userChats.forEach(userChat -> chatIdSet.add(userChat.getChatId()));

                // 说明不在同一个群聊中
                if (chatIdSet.size() >= 2) {
                    // 给用户发送通知
                    List<String> ddUserIdList = new ArrayList<>(usersInfo.size());
                    List<String> userName = new ArrayList<>(usersInfo.size());
                    //List<String> ddUserIdList = new ArrayList<>(usersInfo.size());
                    usersInfo.forEach(userInfo -> {
                        ddUserIdList.add(userInfo.getDdUserId());
                        userName.add(userInfo.getName());
                    });

                    for (int i = 0; i < ddUserIdList.size(); i++) {

                        List<String> uName = new ArrayList<>(userName);
                        List<String> currentUserName = new ArrayList<>(1);
                        currentUserName.add(userName.get(i));
                        uName.removeAll(currentUserName);

                        if (CollectionUtil.isNotEmpty(uName)) {
                            // 得到使用逗号分隔的用户名称
                            String userStr = String.join(",", uName);
                            //String substring = userStr.substring(0, userStr.length() - 1);
                            //log.info("用户名称：" + userStr);

                            String text = "用户" + userName.get(i) + "您好，" +
                                    "企业用户" + userStr +
                                    "和您不在一个群，请及时相互联系，更换群组";
                            log.info("拼接的字符串为：" + text);

                            MessageCorpParam param = new MessageCorpParam();
                            param.setUserIdList(ddUserIdList.get(i));
                            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
                            msg.setMsgtype("text");
                            OapiMessageCorpconversationAsyncsendV2Request.Text sendText = new OapiMessageCorpconversationAsyncsendV2Request.Text();
                            sendText.setContent(text);
                            msg.setText(sendText);
                            param.setMsg(msg);
                            DDSendMessageUtils.messageCorpconversationAsync(param);
                        }

                    }

                }
            }
        }
    }

    /**
     * 定时设置消息的已读未读人数
     */
    @Override
    public void selectMsgReadNumAndUnReadNum() {
        // 1. 先查询所有的notice
        // 2. 查询出所有的 消息发送到了哪些群组
        // 3. 查询各个消息的已读未读情况
        //     3.1 记录是否此条消息全部已读
        //     3.2 如果此条 notice 下发送到所有的群聊中的消息全部已读，将此条 notice 设置为已读

        List<Notice> notices = noticeMapper.selectList(Wrappers.<Notice>lambdaQuery()
                .select(Notice::getNoticeId)
                .eq(Notice::getFlag, 1)
                .eq(Notice::getChatOrUser, 1)
                .eq(Notice::getIsAllRead, 0));
        if (CollectionUtil.isNotEmpty(notices)) {
            List<Integer> noticeIdList = new ArrayList<>(notices.size());
            notices.forEach(notice -> noticeIdList.add(notice.getNoticeId()));

            // 将各个notice 下发送到各个群下面的消息查询出来进行查看各个已读未读数量
            for (Integer noticeId : noticeIdList) {
                // 查询此条 notice 下发送到各个群中的消息
                List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                        .eq(NoticeChat::getNoticeId, noticeId)
                        .isNotNull(NoticeChat::getChatId)
                        .eq(NoticeChat::getIsAllRead, 0));
                // 将各个群消息查看每条已读未读人员
                if (CollectionUtil.isNotEmpty(noticeChats)) {

                    // 此条 notice 下 发给了多少个群
                    int size = noticeChats.size();

                    int allReadMsgFlag = 0;

                    // 查询群信息获取群的所有用户
                    for (NoticeChat noticeChat : noticeChats) {

                        try {
                            // 群人员总数
                            OapiChatGetResponse response = ChatSDKUtil.getChatInfo(noticeChat.getChatId());
                            // 所有的群成员列表
                            List<String> useridlist = response.getChatInfo().getUseridlist();
                            if (CollectionUtil.isNotEmpty(useridlist)) {

                                ReadAndUnreadNum readAndUnreadNum = ChatSDKUtil.countMsgReadAndUnreadNum(noticeChat.getMsgId(), 100L, useridlist.size());

                                noticeChat.setReadNum(readAndUnreadNum.getReadNum());
                                noticeChat.setUnreadNum(readAndUnreadNum.getUnReadNum());
                                // 说明为全部已读
                                if (readAndUnreadNum.getUnReadNum().equals(0)) {
                                    noticeChat.setIsAllRead(1);
                                    allReadMsgFlag++;
                                }

                                // 将已读未读数进行保存
                                noticeChatMapper.update(noticeChat, Wrappers.<NoticeChat>lambdaQuery()
                                        .eq(NoticeChat::getChatId, noticeChat.getChatId())
                                        .eq(NoticeChat::getNoticeId, noticeChat.getNoticeId()));

                            }
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }

                    }
                    // 如果全部的消息数登录等于全部的已读数量，说明此 notice 全部已读
                    if (size == allReadMsgFlag) {
                        Notice notice = new Notice();
                        notice.setNoticeId(noticeId);
                        notice.setIsAllRead(1);
                        noticeMapper.updateById(notice);
                    }
                }
            }
        }
    }

}

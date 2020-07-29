package com.ciyun.renshe.common.dingding.sdk;

import cn.hutool.core.collection.CollectionUtil;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.sdk.request.SubAdminParam;
import com.ciyun.renshe.entity.po.ReadAndUnreadNum;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatGetReadListRequest;
import com.dingtalk.api.request.OapiChatGetRequest;
import com.dingtalk.api.request.OapiChatSubadminUpdateRequest;
import com.dingtalk.api.response.OapiChatGetReadListResponse;
import com.dingtalk.api.response.OapiChatGetResponse;
import com.dingtalk.api.response.OapiChatSubadminUpdateResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2020/4/16 14:07
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
public class ChatSDKUtil {

    /**
     * 获取群信息
     *
     * @param chatId
     * @return
     */
    public static OapiChatGetResponse getChatInfo(String chatId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/get");
        OapiChatGetRequest request = new OapiChatGetRequest();
        request.setHttpMethod("GET");
        request.setChatid(chatId);
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 查询群消息已读人员列表
     *
     * @param messageId 发送群消息接口返回的加密消息id(消息id中包含url特殊字符时需要encode后再使用)
     * @param cursor    分页查询的游标，第一次可以传0，后续传返回结果中的next_cursor的值。当返回结果中，没有next_cursor时，表示没有后续的数据了，可以结束调用
     * @param size      分页查询的大小，最大可以传100
     * @return
     * @throws ApiException
     */
    public static OapiChatGetReadListResponse getReadList(String messageId, Long cursor, Long size) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/getReadList");
        OapiChatGetReadListRequest request = new OapiChatGetReadListRequest();
        request.setHttpMethod("GET");
        request.setMessageId(messageId);
        request.setCursor(cursor);
        request.setSize(size);
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 设置群管理员
     *
     * @param param
     * @return
     * @throws ApiException
     */
    public static OapiChatSubadminUpdateResponse subAdmin(SubAdminParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/chat/subadmin/update");
        OapiChatSubadminUpdateRequest req = new OapiChatSubadminUpdateRequest();
        req.setChatid(param.getChatId());
        req.setUserids(param.getUserIds());
        req.setRole(param.getRole());
        return client.execute(req, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 根据消息 id 统计已读和未读人数
     *
     * @param msgId     消息id
     * @param s         每次查询多少人 最大 100
     * @param chatCount 群成员总数
     * @return
     * @throws ApiException
     */
    public static ReadAndUnreadNum countMsgReadAndUnreadNum(String msgId, Long s, Integer chatCount) {

        AtomicInteger countFlag = new AtomicInteger(0);

        boolean flag = true;
        long cursor = 0L;
        // 已读人员总数
        AtomicInteger readCount = new AtomicInteger(0);

        AtomicInteger count = new AtomicInteger(0);
        while (flag) {
            log.info("调用钉钉查询群已读人数接口，msgId为：{}", msgId);
            OapiChatGetReadListResponse readListResponse = null;
            try {
                readListResponse = ChatSDKUtil.getReadList(msgId, cursor, s);
            } catch (ApiException e) {
                int i = countFlag.getAndAdd(1);
                if (i >= 5) {
                    flag = false;
                }
                e.printStackTrace();
            }



            if (Objects.requireNonNull(readListResponse).getErrcode().equals(0L)) {

                List<String> readUserIdList = readListResponse.getReadUserIdList();

                if (CollectionUtil.isNotEmpty(readUserIdList)) {
                    // 得到已读人员总数
                    int size = readUserIdList.size();
                    //int i = chatCount - size;
                    int i = readCount.addAndGet(size);
                    readCount.set(i);
                }

                // 查看是否返回 nextCursor 字段
                if (readListResponse.getNextCursor() != null && readListResponse.getNextCursor() != 0L) {
                    // 将 NextCursor 赋值给 cursor
                    cursor = readListResponse.getNextCursor();
                } else {
                    flag = false;
                }

            }

            int andAdd = count.getAndAdd(100);
            if (andAdd > 1000) {
                flag = false;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ReadAndUnreadNum readAndUnreadNum = new ReadAndUnreadNum();

        // 已读总数
        int readNum = readCount.get();
        readAndUnreadNum.setReadNum(readNum);

        //未读总数
        if (readNum >= chatCount) {
            readAndUnreadNum.setUnReadNum(0);
        } else {
            readAndUnreadNum.setUnReadNum(chatCount - readNum);
        }

        return readAndUnreadNum;
    }
}

package com.ciyun.renshe.service;

import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.message.NoticeVO;
import com.ciyun.renshe.controller.vo.message.SendChatMessageVo;
import com.ciyun.renshe.controller.vo.message.receive.ReceiveMsgVO;
import com.ciyun.renshe.controller.vo.message.receive.SendReceiveMsgVO;
import com.ciyun.renshe.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taobao.api.ApiException;

public interface MessageService extends IService<Message> {

    /**
     * 发送工作通知消息
     *
     * @param noticeVO
     * @return
     */
    void sendNotice(NoticeVO noticeVO);

    /**
     * 发送群消息
     *
     * @param messageVo
     * @return
     */
    void sendChatMessage(SendChatMessageVo messageVo);

    /**
     * 查询已读人员列表
     *
     * @param messageId
     * @return
     */
    Result getGetReadList(Integer noticeId, String messageId);

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
    Result getMessageHistory(Page page, Integer chatOrUser, String title, String startDate, String endDate, Integer type);

    /**
     * @param page
     * @param userId
     * @param type
     * @param classType
     * @return
     */
    Result getMessageHistory2DD(Page page, Integer userId, Integer type, String classType);

    /**
     * 查看最新的5条数据
     *
     * @param userId
     * @return
     */
    Result getMessageHistory2DD(Integer userId);

    /**
     * 接收来自钉钉推送的数据
     *
     * @param receiveMsgVO
     */
    void receiveMsg(ReceiveMsgVO receiveMsgVO);

    /**
     * 发送群中
     *
     * @param sendReceiveMsgVO
     * @return
     */
    Result sendReceiveMsg(SendReceiveMsgVO sendReceiveMsgVO);

    /**
     * 查询@机器人回复消息
     *
     * @param page
     * @param userId
     * @return
     */
    Result getReceiveMsgHistory(Page page, Integer userId);

    /**
     * 修改会话状态
     *
     * @param atId
     */
    void setReceiveMsgClose(Integer atId);

    /**
     * 设置消息为已读
     *
     * @param atId
     */
    void setMsgRead(Integer atId);

    /**
     * 设置重新发送消息
     */
    void reSendMsg() throws ApiException;

    /**
     * 个人消息撤回
     *
     * @param magId
     * @return
     */
    Result setMsgReCall(Integer magId);

    /**
     * 删除通知消息
     *
     * @param noticeId
     * @return
     */
    Result deleteNotice(Integer noticeId);

}



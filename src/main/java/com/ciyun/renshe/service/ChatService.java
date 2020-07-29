package com.ciyun.renshe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.PageResult;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.chat.*;
import com.ciyun.renshe.controller.vo.permission.PermissionData;
import com.ciyun.renshe.entity.Chat;
import com.ciyun.renshe.entity.Message;
import com.ciyun.renshe.entity.po.AreaPO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ChatService extends IService<Chat> {

    /**
     * 创建会话
     *
     * @param chatVO
     * @return
     */
    Result createChat(CreateChatVO chatVO);

    /**
     * 发送群消息
     *
     * @param sendChatVO
     * @return
     */
    Result sendChat(SendChatVO sendChatVO);

    /**
     * 设置群管理员
     *
     * @param userId
     * @param roleIds
     * @param chatId
     * @param userIds
     * @param role
     * @return
     */
    Result subAdmin(Integer userId, String roleIds, String chatId, String userIds, Long role);

    /**
     * 获取全部群聊
     *
     * @param page
     * @return
     */
    Result getAllChat(Page page);

    /**
     * 根据地点查询群
     *
     * @param page
     * @param position
     * @return
     */
    Result getChatByPosition(Page page, String position);

    /**
     * 群组合并
     *
     * @param mergeChatVO
     * @return
     */
    Result mergeChat(MergeChatVO mergeChatVO);

    /**
     * 修改会话信息
     *
     * @param updateChatVO
     * @return
     */
    Result updateChat(UpdateChatVO updateChatVO);

    /**
     * 查询全部群所在地区
     *
     * @return
     */
    @Deprecated
    Result getAllPosition();

    /**
     * 查看群发送消息的历史
     *
     * @param chatId
     * @return
     */
    Result getMessageHistory(Page page, String chatId);

    /**
     * 查询对应权限的所有群聊
     *
     * @param permissionData
     * @return
     */
    Result getChatList(PermissionData permissionData);

    /**
     * 获取各个区域下面的数据
     *
     * @param page
     * @param chatInfoVO
     * @param permissionData
     * @return
     */
    Result getChatInfo(Page page, GetChatInfoVO chatInfoVO, PermissionData permissionData);

    /**
     * 根据用户id查询对应的通知公告
     *
     * @param userId
     * @param page
     * @return
     */
    PageInfo<Message> getCorpMessageByUserId(Integer userId, Page page);

    /**
     * 根据 群id 查询对应的群信息
     *
     * @param chatId
     * @return
     */
    Chat getChatInfoByChatId(String chatId);

    /**
     * 获取行业群
     *
     * @param page
     * @param name
     * @return
     */
    PageResult getIndustryChat(Page page, String name);

    /**
     * 钉钉下获取全部群
     *
     * @param userId
     * @return
     */
    PageResult getDdChat(Page page, Integer userId);

    /**
     * 根据各个id查询对应的全部群聊
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param userId
     * @param roleId
     * @return
     */
    PageResult getChatList(Page page, Integer city, Integer areaId, Integer streetId, Integer gridId, Integer userId, Integer roleId);

    /**
     * 根据 规模id 或行业id查询对应群
     *
     * @param page
     * @param esId
     * @param industryId
     * @return
     */
    PageResult getChatByEs(Page page, Integer esId, Integer industryId, Integer esAll, Integer industryIdAll);

    /**
     * 根据群id查询群下所在的用户列表
     *
     * @param chatId
     * @return
     */
    Result getUserListByChatId(String chatId);

    /**
     * 根据群名搜索群
     *
     * @param name
     * @param userId
     * @param roleId
     * @return
     */
    Result getChatInfoByName(String name, Integer userId, Integer roleId);

    /**
     * 修改或者删除群下的人员
     *
     * @param updateChatUserVO
     * @return
     */
    Result updateOrDeleteChatUser(UpdateChatUserVO updateChatUserVO);

    /**
     * 钉钉根据 userId 获取全部的群聊
     *
     * @param userId
     * @return
     */
    Result findAllChat2DD(Integer userId, Integer type);

    /**
     * 查询全部除网格群之外的群
     *
     * @param page
     * @param userId
     * @param roleId
     * @return
     */
    Result findAllChatInfo(Page page, Integer userId, Integer roleId);

    Result findAllBigChat();

    /**
     * 解散群
     *
     * @param chatDisbandedVO
     * @return
     */
    Result chatDisbanded(ChatDisbandedVO chatDisbandedVO);

    Integer findAllChat();

    List<AreaPO> areaExistMerge(List<AreaPO> areaPOS, AreaPO areaPO);

    void findArea(PermissionData permissionData);

    String findAllAreaPermission(Integer userId);
}







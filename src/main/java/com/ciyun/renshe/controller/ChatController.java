package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.*;
import com.ciyun.renshe.controller.vo.chat.*;
import com.ciyun.renshe.controller.vo.permission.PermissionData;
import com.ciyun.renshe.controller.vo.permission.PermissionVO;
import com.ciyun.renshe.entity.Chat;
import com.ciyun.renshe.entity.Message;
import com.ciyun.renshe.service.ChatService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Date 2020/4/8 16:43
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "群聊天模块")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {

    final private ChatService chatService;

    /**
     * 查询创建的所有群聊
     *
     * @param page
     * @return
     */
    /*@GetMapping
    @ApiOperation("查询全部群聊")
    public Result getAllChat(Page page) {
        return chatService.getAllChat(page);
    }*/

    /**
     * 根据 群id 查询对应的群信息
     *
     * @param chatId
     * @return
     */
    @GetMapping("/chatId")
    @ApiOperation("根据 群id 查询对应的群信息")
    @ApiImplicitParam(name = "chatId", value = "群id", required = true, paramType = "query", dataType = "String")
    public Result getChatInfoByChatId(@RequestParam String chatId) {
        Chat chat = chatService.getChatInfoByChatId(chatId);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), chat);
    }

    /**
     * 查询对应权限的所有群聊
     *
     * @param
     * @return
     */
    @PostMapping("/permission")
    @ApiOperation("查询对应权限的所有群聊")
    public Result getAllChat(@RequestBody PermissionVO permissionVO) {
        return chatService.getChatList(permissionVO.getPermission());
    }

    /**
     * 根据各个id查询对应的全部群聊
     *
     * @return
     */
    @GetMapping("/chat/list")
    @ApiOperation("根据市，区，街道，网格 id 查询对应的全部群聊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "city", value = "青岛市id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "areaId", value = "区id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "streetId", value = "街道id， ", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "gridId", value = "网格id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = false, paramType = "query", dataType = "int")
    })
    public Result getChatList(Page page, Integer city, Integer areaId, Integer streetId,
                              Integer gridId, Integer userId, Integer roleId) {
        PageResult chatList = chatService.getChatList(page, city, areaId, streetId, gridId, userId, roleId);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), chatList);
    }

    /**
     * 获取各个区域下面的数据
     *
     * @return
     */
    @GetMapping("/info")
    @ApiOperation("获取各个层级的群信息")
    public Result getChatInfo(Page page, GetChatInfoVO chatInfoVO) {
        //PermissionData permissionData = JSON.parseObject(permission, PermissionData.class);
        return chatService.getChatInfo(page, chatInfoVO, new PermissionData());
    }

    /**
     * 查询全部群所在地区
     *
     * @return
     */

    /*@GetMapping("/position/list")
    @ApiOperation("查询全部群所在地区")
    public Result getAllPosition() {
        return chatService.getAllPosition();
    }*/

    /**
     * 根据地点查询群
     *
     * @param page
     * @param position
     * @return
     */
    /*@GetMapping("/position")
    @ApiOperation("根据地点查询群")
    @ApiImplicitParam(name = "position", value = "群所在地点", required = true, paramType = "query", dataType = "String")
    public Result getChatByPosition(Page page, @RequestParam String position) {
        return chatService.getChatByPosition(page, position);
    }*/

    /**
     * 创建会话
     *
     * @param chatVO
     * @return
     */
    @PostMapping
    @ApiOperation("创建会话")
    public Result createChat(@RequestBody @Valid CreateChatVO chatVO) {
        return chatService.createChat(chatVO);
    }

    /**
     * 修改会话信息
     *
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改会话信息")
    public Result updateChat(@RequestBody @Valid UpdateChatVO updateChatVO) {
        return chatService.updateChat(updateChatVO);
    }

    /**
     * 发送群消息
     *
     * @param sendChatVO
     * @return
     */
    @PostMapping("/send")
    @ApiOperation("发送群消息")
    public Result sendChat(@RequestBody @Valid SendChatVO sendChatVO) {
        return chatService.sendChat(sendChatVO);
    }

    /**
     * 设置群管理员
     *
     * @param chatId  群会话id
     * @param userIds 群成员userid列表
     * @param role    设置2为添加为管理员，设置3为删除该管理员
     * @return
     */
    @GetMapping("/subadmin")
    @ApiOperation("设置群管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chatId", value = "群会话id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userIds", value = "群成员ddUserid列表", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "role", value = "设置2为添加为管理员，设置3为删除该管理员", required = true, paramType = "query", dataType = "int")
    })
    public Result subAdmin(@RequestParam Integer userId, @RequestParam String roleIds, @RequestParam String chatId, @RequestParam String userIds, @RequestParam Long role) {
        return chatService.subAdmin(userId, roleIds, chatId, userIds, role);
    }

    /**
     * 群组合并
     *
     * @param mergeChatVO
     * @return
     */
    @PostMapping("/merge")
    @ApiOperation("合并群组")
    public Result mergeChat(@RequestBody MergeChatVO mergeChatVO) {
        if (mergeChatVO.getChatList().size() < 2) {
            return new Result(false, StatusCode.ERROR, "合并群至少要选择两个");
        }
        return chatService.mergeChat(mergeChatVO);
    }

    /**
     * 查看群发送消息的历史
     *
     * @param chatId
     * @return
     */
    @GetMapping("/history")
    @ApiOperation("查看群发送消息的历史")
    @ApiImplicitParam(name = "chatId", value = "群会话id", required = true, paramType = "query", dataType = "String")
    public Result getMessageHistory(Page page, @RequestParam String chatId) {
        return chatService.getMessageHistory(page, chatId);
    }

    /**
     * 小程序 根据用户id查询对应的通知公告
     *
     * @return
     */
    @GetMapping("/corp")
    @ApiOperation("小程序-根据用户id查询对应的通知公告")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "int")
    public Result getCorpMessageByUserId(@RequestParam Integer userId, Page page) {
        PageInfo<Message> corpMessageByUserId = chatService.getCorpMessageByUserId(userId, page);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                new PageResult(corpMessageByUserId.getTotal(), corpMessageByUserId.getList()));
    }

    /**
     * 获取行业群
     *
     * @return
     */
    @GetMapping("/industry")
    @ApiImplicitParam(name = "name", value = "群名称", required = false, paramType = "query", dataType = "String")
    public Result getIndustryChat(Page page, String name) {
        PageResult pageResult = chatService.getIndustryChat(page, name);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), pageResult);
    }

    /**
     * 钉钉获取全部的群
     *
     * @return
     */
    @GetMapping("/dingding/getChat")
    @ApiOperation("钉钉获取全部的群")
    @ApiImplicitParam(name = "userId", value = "用户id", required = false, paramType = "query", dataType = "int")
    public Result getDdChat(Page page, Integer userId) {
        PageResult pageResult = chatService.getDdChat(page, userId);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), pageResult);
    }

    /**
     * 根据 规模id 或行业id查询对应群
     *
     * @param page
     * @param esId
     * @param industryId
     * @return
     */
    @GetMapping("/es/list")
    @ApiOperation("根据 规模id 或行业id查询对应群")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "esId", value = "规模id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "industryId", value = "行业id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "esAll", value = "规模全部", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "industryIdAll", value = "行业全部", required = false, paramType = "query", dataType = "int")
    })
    public Result getChatByEs(Page page, Integer esId, Integer industryId, Integer esAll, Integer industryIdAll) {
        PageResult result = chatService.getChatByEs(page, esId, industryId, esAll, industryIdAll);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), result);
    }

    /**
     * 根据群名搜索群
     *
     * @param name
     * @return
     */
    @GetMapping("/getChatInfoByName")
    @ApiOperation("根据群名搜索群")
    @ApiImplicitParam(name = "name", value = "群名称", required = true, paramType = "query", dataType = "String")
    public Result getChatInfoByName(@RequestParam String name, Integer userId, Integer roleId) {
        return chatService.getChatInfoByName(name, userId, roleId);
    }

    /**
     * 根据群id查询群下所在的用户列表
     *
     * @return
     */
    @GetMapping("/getUserListByChatId")
    @ApiOperation("根据群id查询群下所在的用户列表")
    @ApiImplicitParam(name = "chatId", value = "群id", required = true, paramType = "query", dataType = "String")
    public Result getUserListByChatId(@RequestParam String chatId) {
        return chatService.getUserListByChatId(chatId);
    }

    /**
     * 修改或者删除群下的人员
     *
     * @return
     */
    @PostMapping("/updateOrDeleteChatUser")
    @ApiOperation("修改或者删除群下的人员")
    public Result updateOrDeleteChatUser(@RequestBody @Valid UpdateChatUserVO updateChatUserVO) {
        return chatService.updateOrDeleteChatUser(updateChatUserVO);
    }

    /**
     * 钉钉根据 userId 获取全部的群聊
     *
     * @return
     */
    @GetMapping("/findAllChat2DD")
    @ApiOperation("钉钉根据 userId 获取全部的群聊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "类型 1 区域群 2行业群", required = true, paramType = "query", dataType = "int")
    })
    public Result findAllChat2DD(@RequestParam Integer userId, @RequestParam Integer type) {
        return chatService.findAllChat2DD(userId, type);
    }

    /**
     * 查询全部除网格群之外的群
     *
     * @param page
     * @return
     */
    @GetMapping("/findAllChatInfo")
    @ApiOperation("查询全部除网格群之外的群")
    public Result findAllChatInfo(Page page, Integer userId, Integer roleId) {
        return chatService.findAllChatInfo(page, userId, roleId);
    }

    /**
     * 设置群管理员
     *
     * @return
     */
    /*@PostMapping("/setChatAdmin")
    @ApiOperation("设置群管理员")
    public Result setChatAdmin(@RequestBody SetChatAdminVO setChatAdminVO) {
        return chatService.setChatAdmin(setChatAdminVO);
    }*/
    @GetMapping("/bigChat")
    @ApiOperation("查询大型的群")
    public Result findAllBigChat() {
        return chatService.findAllBigChat();
    }

    /**
     * 解散群
     *
     * @return
     */
    @PostMapping("/disband")
    @ApiOperation("解散群")
    public Result chatDisbanded(@RequestBody @Valid ChatDisbandedVO chatDisbandedVO) {
        return chatService.chatDisbanded(chatDisbandedVO);
    }

}

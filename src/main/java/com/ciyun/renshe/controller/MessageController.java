package com.ciyun.renshe.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.controller.async.AsyncTask;
import com.ciyun.renshe.controller.vo.message.NoticeVO;
import com.ciyun.renshe.controller.vo.message.SendChatMessageVo;
import com.ciyun.renshe.controller.vo.message.receive.ReceiveMsgVO;
import com.ciyun.renshe.controller.vo.message.receive.SendReceiveMsgVO;
import com.ciyun.renshe.service.MessageService;
import com.ciyun.renshe.websocket.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @Date 2020/4/10 17:16
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Api(tags = "消息模块")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final AsyncTask asyncTask;

    public MessageController(MessageService messageService, AsyncTask asyncTask) {
        this.messageService = messageService;
        this.asyncTask = asyncTask;
    }

    @Value("${project.newsUrl}")
    private String newsUrl;

    @GetMapping("/webSocket/text")
    @ApiOperation("webSocket发送消息测试")
    public Result sendWebSocket(String userId, String msg) throws IOException {
        WebSocketServer.sendInfo(msg, userId);
        return new Result();
    }

    /**
     * 接收来自钉钉推送的数据
     *
     * @return
     */
    @PostMapping("/receive")
    @ApiOperation("接收来自钉钉推送的数据")
    public Result receiveMsg(@RequestBody ReceiveMsgVO receiveMsgVO, HttpServletRequest request) {

        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");

        log.info("---------------began---------------------");
        log.info("------------------------------------");
        log.info("------------------------------------");
        log.info(DateUtil.now());
        log.info("receiveMsgVO{}", JSON.toJSONString(receiveMsgVO));
        log.info("------------------------------------");
        log.info("------------------------------------");
        log.info("---------------end---------------------");

        log.info("钉钉发送过来的sign:{}", sign);
        /*try {
            String verificationSign = this.verificationSign(Long.parseLong(timestamp), DingDingData.ROBOT_APPSECRET.getValue());
            // 如果相同进入下一步
            if (verificationSign.equals(sign)) {
                log.info("验证成功,解密后获取的sign为{}", verificationSign);
                messageService.receiveMsg(receiveMsgVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        messageService.receiveMsg(receiveMsgVO);
        //messageService.receiveMsg(receiveMsgVO);
        return new Result(true, StatusCode.OK, MessageInfo.DD_SEND_MSG_SUCCESS.getInfo());
    }

    /**
     * 发送群中@的消息
     *
     * @param sendReceiveMsgVO
     * @return
     */
    @PostMapping("/send/receive")
    @ApiOperation("发送群中@的消息")
    public Result sendReceiveMsg(@RequestBody SendReceiveMsgVO sendReceiveMsgVO) {
        return messageService.sendReceiveMsg(sendReceiveMsgVO);
    }

    /**
     * 发送工作通知消息
     *
     * @param noticeVO
     * @return
     */
    @PostMapping("/notice")
    @ApiOperation("发送工作通知消息")
    public Result sendNotice(@RequestBody @Valid NoticeVO noticeVO) {
        noticeVO.setRequestUrl(newsUrl);
        asyncTask.sendNotice(noticeVO);
        return new Result(true, StatusCode.OK, MessageInfo.DD_SEND_MSG_SUCCESS.getInfo());
    }

    /**
     * 发送群消息
     *
     * @param messageVo
     * @return
     */
    @PostMapping("/chat")
    @ApiOperation("发送群消息")
    public Result sendChatMessage(@RequestBody @Valid SendChatMessageVo messageVo, HttpServletRequest request) throws InterruptedException {
        messageVo.setRequestUrl(newsUrl);
        asyncTask.sendChatMessage(messageVo);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo());
    }

    /**
     * 查询已读未读消息
     *
     * @param msgId
     * @return
     */
    @GetMapping("/messageId")
    @ApiOperation("/查询已读人员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noticeId", value = "noticeId", required = true, paramType = "query", dataType = "int")
    })
    public Result getGetReadList(@RequestParam Integer noticeId, String msgId) {
        return messageService.getGetReadList(noticeId, msgId);
    }

    @GetMapping("/test")
    @ApiOperation("测试异步发送")
    public Result testSendMessage() throws InterruptedException {
        //DeferredResult<Result> result = new DeferredResult<>();
        asyncTask.sendChatMessage(new SendChatMessageVo());
        Result result1 = new Result(true, StatusCode.OK, MessageInfo.DD_SEND_MSG_SUCCESS.getInfo());
        //result.setResult(result1);
        log.info("Controller 返回时间{}", DateUtil.now());
        return result1;
    }

    /**
     * 管理端获取消息历史
     *
     * @return
     */
    @GetMapping("/getMessageHistory")
    @ApiOperation("管理端获取消息历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, paramType = "query", dataType = "int")
    })
    public Result getMessageHistory(Page page, @RequestParam Integer chatOrUser, String title, String startDate, String endDate, Integer type) {
        return messageService.getMessageHistory(page, chatOrUser, title, startDate, endDate, type);
    }

    /**
     * 钉钉用户查看用户的所有通知
     *
     * @param page
     * @param userId
     * @param type
     * @return
     */
    @GetMapping("/getMessageHistory2DD")
    @ApiOperation("钉钉用户查看用户的所有通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "1 通知公告  2. 政策资讯", required = false, paramType = "query", dataType = "String")
    })
    public Result getMessageHistory2DD(Page page, @RequestParam Integer userId,
                                       @RequestParam Integer type, String classType) {
        return messageService.getMessageHistory2DD(page, userId, type, classType);
    }

    /**
     * 查看最新的5条数据
     *
     * @param userId
     * @return
     */
    @GetMapping("/getMessageHistory2DD/5")
    @ApiOperation("查看最新的5条数据")
    public Result getMessageHistory2DD(Integer userId) {
        return messageService.getMessageHistory2DD(userId);
    }

    /**
     * 查询@机器人回复消息
     *
     * @param userId
     * @return
     */
    @GetMapping("/getReceiveMsgHistory")
    @ApiOperation("查询@机器人回复消息历史记录")
    public Result getReceiveMsgHistory(Page page, Integer userId) {
        return messageService.getReceiveMsgHistory(page, userId);
    }

    /**
     * 修改会话状态
     *
     * @param atId
     * @return
     */
    @GetMapping("/receive/close")
    @ApiOperation("修改@机器人会话消息状态")
    public Result setReceiveMsgClose(Integer atId) {
        messageService.setReceiveMsgClose(atId);
        return new Result(true, StatusCode.OK, MessageInfo.UPDATE_INFO.getInfo());
    }

    /**
     * 设置消息为已读
     *
     * @param atId
     */
    @GetMapping("/setMsgRead")
    public void setMsgRead(@RequestParam Integer atId) {
        messageService.setMsgRead(atId);
    }

    /**
     * 发送的个人消息撤回操作
     *
     * @return
     */
    @GetMapping("/msg/recall")
    @ApiOperation("发送的个人消息撤回操作")
    public Result setMsgReCall(Integer noticeId) {
        return messageService.setMsgReCall(noticeId);
    }

    /**
     * 删除通知消息
     *
     * @param noticeId
     * @return
     */
    @GetMapping("/msg/delete")
    public Result deleteNotice(Integer noticeId) {
        return messageService.deleteNotice(noticeId);
    }

    /**
     * 钉钉校验 sign
     *
     * @return
     */
    private static String verificationSign(Long timestamp, String appSecret) throws Exception {

        String stringToSign = timestamp + "\n" + appSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(signData));
    }

}

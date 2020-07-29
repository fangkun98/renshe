package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.service.CountService;
import com.ciyun.renshe.service.NoticeService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 各种统计Controller
 *
 * @Date 2020/5/7 10:36
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "统计模块")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/count")
@AllArgsConstructor
public class CountController {

    private final NoticeService noticeService;
    private final CountService countService;

    /**
     * 查询全部消息
     *
     * @param cityId
     * @param areaId
     * @param streetId
     * @param gridId
     * @return
     */
    @GetMapping("/findMessageCount")
    @ApiOperation("查询全部消息")
    public Result findMessageCount(Integer cityId, Integer areaId, Integer streetId, Integer gridId) {
        return noticeService.findMessageCount(cityId, areaId, streetId, gridId);
    }

    @GetMapping("/chatManagerCount")
    @ApiOperation("群管理报表")
    public Result chatManagerCount() {
        return countService.chatManagerCount();
    }

    @GetMapping("/maxReplyChatOwner")
    @ApiOperation("查询回复条数最多群主")
    public Result maxReplyChatOwner() {
        return countService.maxReplyChatOwner();
    }

    /*@GetMapping("/findChat")
    public Result findChat() {

    }*/

    /**
     * 问题统计
     *
     * @return
     */
    @GetMapping("/problemCount/30")
    public Result problemCount() {
        return countService.problemCount();
    }

    /**
     * 最近7天每天提交问题提交数量
     *
     * @return
     */
    @GetMapping("/problemCount/7")
    public Result problem7Count() {
        return countService.problem7Count();
    }

    /**
     * 消息统计
     *
     * @return
     */
    @GetMapping("/notice/30")
    public Result noticeCount() {
        return countService.noticeCount();
    }

    /**
     * 在线统计时长最长30条
     *
     * @return
     */
    @GetMapping("/online/30")
    public Result adminLineCount() {
        return countService.adminLineCount();
    }

}

package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.entity.Notice;
import com.ciyun.renshe.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告与政策解读
 *
 * @Date 2020/4/20 15:04
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "通知公告与政策解读")
@Validated
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/notice")
@AllArgsConstructor
public class NoticeController {

    final private NoticeService noticeService;

    /**
     * 小程序-根据类型查询通知公告或者政策解读
     *
     * @return
     */
    /*@GetMapping("/type")
    @ApiOperation("小程序-根据类型查询通知公告或者政策解读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "类型，1 通知公告  2. 政策资讯", required = true, paramType = "query", dataType = "int")
    })
    public Result findNoticeByType(Page page, @RequestParam Integer userId, @RequestParam Integer type) {
        log.info("userId:{}", userId);
        PageResult pageResult = noticeService.findNoticeByType(page, userId, type);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), pageResult);
    }*/

    /**
     * 后台根据类型查询通知公告或者政策解读
     *
     * @return
     */
    /*@GetMapping("/admin/type")
    @ApiOperation("后台-根据类型查询通知公告或者政策解读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "类型，1 通知公告  2. 政策资讯", required = true, paramType = "query", dataType = "int")
    })
    public Result findAdminNoticeByType(Page page, @RequestParam Integer userId, @RequestParam Integer roleId, @RequestParam Integer type) {
        PageResult pageResult = noticeService.findAdminNoticeByType(page, userId, roleId, type);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), pageResult);
    }*/

    /**
     * 更新点赞数或者阅读数
     *
     * @return
     */
    @GetMapping("/readAndLike")
    @ApiOperation("更新点赞数或者阅读数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noticeId", value = "政策id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "like", value = "点赞", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "readingNumber", value = "阅读数", required = false, paramType = "query", dataType = "int")
    })
    public Result readingAndLikes(@RequestParam Integer noticeId, Integer like, Integer readingNumber) {
        noticeService.readingAndLikes(noticeId, like, readingNumber);
        return new Result(true, StatusCode.OK, MessageInfo.UPDATE_INFO.getInfo());
    }

    /**
     * 根据id查询公告
     *
     * @param noticeId
     * @return
     */
    @GetMapping("/findNoticeById")
    @ApiOperation("根据id查询公告")
    @ApiImplicitParam(name = "noticeId", value = "noticeId", required = true, paramType = "query", dataType = "int")
    public Result findNoticeById(@RequestParam Integer noticeId) {
        Notice notice = noticeService.findNoticeById(noticeId);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), notice);
    }

}

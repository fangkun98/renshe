package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.service.ChatService;
import com.ciyun.renshe.service.NoticeService;
import com.ciyun.renshe.service.ProblemService;
import com.ciyun.renshe.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @Date 2020/4/3 10:52
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "用户统计模块")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/userCount")
public class UserCountController {

    private final UserService userService;
    private final ProblemService problemService;
    private final NoticeService noticeService;
    private final ChatService chatService;

    public UserCountController(UserService userService, ProblemService problemService, NoticeService noticeService, ChatService chatService) {
        this.userService = userService;
        this.problemService = problemService;
        this.noticeService = noticeService;
        this.chatService = chatService;
    }

    /**
     * 查询统计用户
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param chatId
     * @return
     */
    @GetMapping("/userCount")
    @ApiOperation("查询统计用户")
    public Result<HashMap> userCount(@RequestParam(value = "city", defaultValue = "") String city,
                                     @RequestParam(value = "areaId", defaultValue = "") String areaId,
                                     @RequestParam(value = "streetId", defaultValue = "") String streetId,
                                     @RequestParam(value = "gridId", defaultValue = "") String gridId,
                                     @RequestParam(value = "chatId", defaultValue = "") String chatId) {
        //Integer user = userService.findCountUser(city, areaId, streetId, gridId, chatId);

        Integer userCount = userService.findUserCount(city, areaId, streetId, chatId);

        //Integer companyCount = userService.findCompanyCount(city, areaId, streetId, gridId, chatId);
        Integer companyCount = userService.findCountCompany(city, areaId, streetId, gridId, chatId);

        Integer chatCount = chatService.findAllChat();

        // 消息送达率
        String messagePercentage = noticeService.messagePercentage(city, areaId, streetId, gridId, chatId);

        Integer allProblem = problemService.findCount(city, areaId, streetId, gridId, chatId, 2);// type 1 已反馈 0 未反馈 2全部
        Integer isSolve = problemService.findCount(city, areaId, streetId, gridId, chatId, 1);// type 1 已反馈 0 未反馈 2全部
        Integer notSolve = problemService.findCount(city, areaId, streetId, gridId, chatId, 0);// type 1 已反馈 0 未反馈 2全部
        //Integer messageCount = noticeService.findCount();
        HashMap<String, Object> map = new HashMap<>();
        map.put("userCount", userCount == null ? 0 : userCount);
        map.put("companyCount", companyCount == null ? 0 : companyCount);
        map.put("allProblem", allProblem == null ? 0 : allProblem);
        map.put("isSolve", isSolve == null ? 0 : isSolve);
        map.put("notSolve", notSolve == null ? 0 : notSolve);
        map.put("chatCount", chatCount);
        map.put("notSolveScale", ConvertNumber(notSolve == null ? 0 : notSolve, allProblem == null ? 0 : allProblem));
        map.put("isSolveScale", ConvertNumber(isSolve == null ? 0 : isSolve, allProblem == null ? 0 : allProblem));
        map.put("messageCount", 1);
        map.put("messagePercentage", messagePercentage);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    /**
     * 查询统计用户
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param chatId
     * @return
     */
    @GetMapping("/companyCount")
    @ApiOperation("查询统计用户")
    public Result<HashMap> companyCount(@RequestParam(value = "city", defaultValue = "") String city,
                                        @RequestParam(value = "areaId", defaultValue = "") String areaId,
                                        @RequestParam(value = "streetId", defaultValue = "") String streetId,
                                        @RequestParam(value = "gridId", defaultValue = "") String gridId,
                                        @RequestParam(value = "chatId", defaultValue = "") String chatId) {
        Integer user = userService.findCompanyCount(city, areaId, streetId, gridId, chatId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", user == null ? 0 : user);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    //两数求百分比
    public String ConvertNumber(int divnum, int num) {
        if (num == 0) {
            return "0.00%";
        }
        String d = divnum + "";
        String m = num + "";
        BigDecimal b = new BigDecimal(d);
        b = b.divide(new BigDecimal(m), 2, BigDecimal.ROUND_HALF_UP);
        b = b.multiply(new BigDecimal("100"));

        return b.toString() + "%";
    }

    public int divideNumber(int divnum, int num) {
        if (num == 0) {
            return 0;
        }
        String d = divnum + "";
        String m = num + "";
        BigDecimal b = new BigDecimal(d);
        b = b.divide(new BigDecimal(m), 2, BigDecimal.ROUND_HALF_UP);
        int i = b.intValue();
        return i;
    }

    public static int multiplyNum(int divnum, int num) {
        if (num == 0) {
            return 0;
        }
        BigDecimal b = new BigDecimal(divnum + "");
        b = b.multiply(new BigDecimal(num + "")).divide(new BigDecimal("100"), 0, BigDecimal.ROUND_HALF_UP);
        int i = b.intValue();
        return i;
    }

    public int ConvertNumber(Long divnum, Long num) {
        if (num == 0) {
            return 0;
        }
        String d = divnum.toString();
        String m = num.toString();
        BigDecimal b = new BigDecimal(d);
        b = b.divide(new BigDecimal(m), 2, BigDecimal.ROUND_HALF_UP);
        b = b.multiply(new BigDecimal("100"));
        int i = b.intValue();
        return i;
    }

    public static void main(String[] args) {
        int i = multiplyNum(3, 33);
        System.out.println(i);
    }

}

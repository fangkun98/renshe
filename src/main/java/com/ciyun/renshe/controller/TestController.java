package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.controller.vo.PeopleVO;
import com.ciyun.renshe.entity.Problem;
import com.ciyun.renshe.entity.Solve;
import com.ciyun.renshe.scheduled.resendmsg.CronTaskRegistrar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Date 2020/4/1 17:32
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("/test")
@Api(tags = "测试模块")
public class TestController {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;


    @GetMapping("/123")
    public void test123(String problemParam) {
//        SchedulingRunnable task = new SchedulingRunnable("messageService","reSendMsg");
//        cronTaskRegistrar.addCronTask(task, "*/5 * * * * ?");
        System.out.println(problemParam.toUpperCase());
    }


    @PatchMapping
    //@ApiOperation(value = "添加用户")
    //@ApiImplicitParam(name = "peopleVO", value = "添加用户json", required = true, paramType = "body",example = "peopleVO")
    public Result addUser(@RequestBody @Valid PeopleVO peopleVO) {

        return new Result(true, StatusCode.OK, MessageInfo.ADD_INFO.getInfo());
    }

    @GetMapping
    //@ApiOperation("根据 id 查询用户")
    //@ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "int")
    public Result<PeopleVO> findUser(@RequestParam Integer userId) {

        PeopleVO peopleVO = new PeopleVO().setUserName("张三").setPassword("123");
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), peopleVO);
    }

    @ApiOperation("测试接口")
    @GetMapping("/test")
    public Result find(Problem problem, Solve solve) {
        return null;
    }


}

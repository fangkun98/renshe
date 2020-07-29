package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.login.LoginVO;
import com.ciyun.renshe.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Date 2020/4/14 14:25
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Validated
@CrossOrigin
@Api(tags = "登录相关模块")
@RestController
@RequestMapping("/login")
public class LoginController {


    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 钉钉免登录
     *
     * @param loginVO
     * @return
     */
    @PostMapping
    @ApiOperation("钉钉后台免登录")
    public Result doLogin(@RequestBody @Valid LoginVO loginVO) {
        return userService.doLogin(loginVO);
    }

    /**
     * 钉钉小程序免登录
     *
     * @param loginVO
     * @return
     */
    @PostMapping("/dingding")
    @ApiOperation("钉钉小程序免登录")
    public Result ddLogin(@RequestBody @Valid LoginVO loginVO) {
        log.info("经过了接口");
        return userService.ddLogin(loginVO);
    }


}

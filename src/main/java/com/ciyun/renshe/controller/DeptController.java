package com.ciyun.renshe.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2020/4/7 15:29
 * @Author Admin
 * @Version 1.0
 */
@Validated
@CrossOrigin
@Api(tags = "部门相关模块")
@RestController
@RequestMapping("/dept")
public class DeptController {

}

package com.ciyun.renshe.service.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Date 2020/4/2 15:08
 * @Author Admin
 * @Version 1.0
 */
@Data
public class TestExcelDTO {
    @ExcelProperty("用户名")
    private String userName;
    @ExcelProperty("密码")
    private String password;
}

package com.ciyun.renshe.service.dto.excel;

import com.ciyun.renshe.common.excel.ExcelColumn;
import lombok.Data;

/**
 * @Date 2020/5/27 16:14
 * @Author Admin
 * @Version 1.0
 */
@Data
public class AdminExcelDTO {

    @ExcelColumn(value = "管理员姓名", col = 1)
    private String name;

    @ExcelColumn(value = "管理员手机号（要与钉钉绑定手机号一致）", col = 2)
    private String mobile;

    @ExcelColumn(value = "所在区", col = 3)
    private String area;

    @ExcelColumn(value = "所在街道", col = 4)
    private String street;
}

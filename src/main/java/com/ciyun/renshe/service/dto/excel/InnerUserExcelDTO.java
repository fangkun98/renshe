package com.ciyun.renshe.service.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.ciyun.renshe.common.excel.ExcelColumn;
import lombok.Getter;
import lombok.Setter;

/**
 * 人设局内部人员导入
 *
 * @Date 2020/5/22 16:48
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class InnerUserExcelDTO {

    @ExcelProperty("人员名称")
    @ExcelColumn(value = "人员名称", col = 1)
    private String name;

    @ExcelProperty("手机号")
    @ExcelColumn(value = "手机号", col = 2)
    private String mobile;

    @ExcelProperty("部门名称")
    @ExcelColumn(value = "部门名称", col = 3)
    private String deptName;

    @ExcelProperty("区")
    @ExcelColumn(value = "区", col = 4)
    private String area;

    @ExcelProperty("街道")
    @ExcelColumn(value = "街道", col = 5)
    private String street;

}

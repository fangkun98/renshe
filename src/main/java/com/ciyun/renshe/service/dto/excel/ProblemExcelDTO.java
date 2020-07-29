package com.ciyun.renshe.service.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;

/**
 * 问题 excel 导出
 *
 * @Date 2020/5/8 10:58
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class ProblemExcelDTO {
    @ExcelProperty("公司名称")
    private String name;
    @ExcelProperty("问题数量")
    private Integer problemSum;
}

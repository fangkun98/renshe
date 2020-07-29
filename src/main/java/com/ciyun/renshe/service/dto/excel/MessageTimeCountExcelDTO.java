package com.ciyun.renshe.service.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @Date 2020/5/28 9:51
 * @Author Admin
 * @Version 1.0
 */
@Data
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class MessageTimeCountExcelDTO {
    @ExcelProperty("响应时间")
    private String respTime;
    @ExcelProperty("响应次数")
    private Long respCount;
}
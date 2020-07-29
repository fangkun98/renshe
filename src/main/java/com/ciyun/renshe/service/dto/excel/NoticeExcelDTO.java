package com.ciyun.renshe.service.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @Date 2020/5/25 16:39
 * @Author Admin
 * @Version 1.0
 */
@Data
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class NoticeExcelDTO {
    @ExcelProperty("群组名称")
    private String name;

    @ExcelProperty("消息阅读率")
    private String noticeNum;
}

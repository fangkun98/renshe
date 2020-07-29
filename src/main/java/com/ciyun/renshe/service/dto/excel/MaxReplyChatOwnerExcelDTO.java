package com.ciyun.renshe.service.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @Date 2020/5/28 9:11
 * @Author Admin
 * @Version 1.0
 */
@Data
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class MaxReplyChatOwnerExcelDTO {

    /**
     * 群名称
     */
    @ExcelProperty("群名称")
    private String roomName;

    /**
     * 群主名称
     */
    @ExcelProperty("群主名称")
    private String ownerName;

    /**
     * 回复条数
     */
    @ExcelProperty("回复条数")
    private String replyTotal;

    /**
     * 回复率
     */
    @ExcelProperty("回复率")
    private String replyRate;
}

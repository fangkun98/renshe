package com.ciyun.renshe.service.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kys
 * @version 1.0
 * @date 2020/7/10 16:09
 */
@Getter
@Setter
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class RenSheNoRegisterDTO {
    @ExcelProperty("名称")
    private String name;
    @ExcelProperty("手机号")
    private String mobile;
}

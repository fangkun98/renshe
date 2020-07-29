package com.ciyun.renshe.service.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;

/**
 * 人员 Excel 报表导出
 *
 * @Date 2020/5/8 10:11
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class UserExcelDTO {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("联系电话")
    private String mobile;
    @ExcelProperty("所属企业")
    private String companyName;
    @ExcelProperty("所属区级")
    private String area;
    @ExcelProperty("所属街道")
    private String street;

    @ExcelProperty("信用代码")
    private String creditCode;

    @ExcelProperty("身份证号")
    private String idCard;
    @ExcelProperty("职务")
    private String position;
    @ExcelProperty("固定电话")
    private String fixedPhone;
}

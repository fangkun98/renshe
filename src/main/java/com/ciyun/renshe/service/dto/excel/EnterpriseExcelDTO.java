package com.ciyun.renshe.service.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;

/**
 * Excel 导出类
 *
 * @Date 2020/5/8 9:27
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class EnterpriseExcelDTO {

    @ExcelProperty("企业名称")

    private String companyName;

    @ExcelProperty("企业规模")
    private String esName;

    @ExcelProperty("所属区级")
    private String area;

    @ExcelProperty("所属街道")
    private String street;

    @ExcelProperty("所属行业")
    private String industryName;

    @ExcelProperty("固话")
    private String fixedPhone;

    @ExcelProperty("邮箱地址")
    private String email;

    @ExcelProperty("信用代码")
    private String creditCode;
}

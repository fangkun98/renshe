package com.ciyun.renshe.common.excel;

import java.lang.annotation.*;

/**
 * 自定义实体类所需要的bean
 *
 * @author Admin
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    /**
     * Excel 标题
     *
     * @return
     */
    String value() default "";

    /**
     * Excel从左往右排列位置
     *
     * @return
     */
    int col() default 0;
}

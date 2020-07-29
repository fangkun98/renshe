package com.ciyun.renshe.common.excel;

import lombok.*;

/**
 * @Date 2019/9/4 9:35
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelException extends RuntimeException {

    private static final long serialVersionUID = 7370051466993716256L;

    private Integer errorCode;

    private String code;
}

package com.ciyun.renshe.common.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2019/8/20 11:48
 * @Author Admin
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Violation {
    private String filedName;
    private String message;
}

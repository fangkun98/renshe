package com.ciyun.renshe.common.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数校验返回结构
 *
 * @Date 2019/8/20 11:47
 * @Author Admin
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();
}

package com.ciyun.renshe.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum HttpMethodEnum {

    GET("GET"),

    POST("POST"),

    PUT("PUT"),

    DELETE("DELETE");

    private final String value;

}

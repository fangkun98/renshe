package com.ciyun.renshe.manager.amap;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * 高德util
 *
 * @Date 2020/4/22 21:40
 * @Author Admin
 * @Version 1.0
 */
public class AMapUtil {

    private String URL = "https://restapi.amap.com/v3/geocode/geo?parameters";

    private static String key = "1e27cb5ada8406d52facccd1d20bf88c";

    /**
     * 获取地址详情
     *
     * @param address
     * @return
     */
    public static String getXY(String address) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate.getForObject("https://restapi.amap.com/v3/geocode/geo?address={1}&city={2}&key={3}", String.class, address, "青岛市", key);
    }

}

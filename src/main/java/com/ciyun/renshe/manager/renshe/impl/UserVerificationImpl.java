package com.ciyun.renshe.manager.renshe.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ciyun.renshe.common.security.SM4SecureUtils;
import com.ciyun.renshe.manager.renshe.UserVerification;
import com.ciyun.renshe.manager.renshe.dto.DsInfoDTO;
import com.ciyun.renshe.service.dto.verification.UnitNo;
import com.ciyun.renshe.service.dto.verification.UnitNoParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 用户校验
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/9 9:16
 */
@Slf4j
@Service
public class UserVerificationImpl implements UserVerification {

    @Value("${renshe.appid}")
    private String appId;
    @Value(("${renshe.key}"))
    private String key;
    @Value("${renshe.url}")
    private String url;
    @Value("${renshe.accessToken}")
    private String accessToken;

    private final RestTemplate restTemplate;

    @Autowired
    public UserVerificationImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 人社用户校验
     *
     * @param accountNum 社会统一信用代码
     * @param password   密码
     * @return
     */
    @Override
    public DsInfoDTO userVerification(String accountNum, String password) {
        UnitNoParam unitNoParam = new UnitNoParam();
        String s = SM4SecureUtils.generateRandomKey(8);
        unitNoParam.setLshid(s);
        log.info("调用单位编号密码校验接口,输入的账号为：{}，生成的随机码为{}", accountNum, s);
        unitNoParam.setData01(accountNum);
        unitNoParam.setData02(password);

        String returnBody = linkRenSheUrl(unitNoParam);

        JSONObject jsonObject = JSON.parseObject(returnBody);
        Map<String, Object> map = checkIsNormal(jsonObject);
        return packageParameters(map);
    }

    /**
     * 连接服务
     *
     * @param unitNoParam 连接参数
     * @return
     */
    private String linkRenSheUrl(UnitNoParam unitNoParam) {
        String unitNoParamJSON = JSON.toJSONString(unitNoParam);
        // 将参数加密
        String encryParam = SM4SecureUtils.encryStr(unitNoParamJSON, key);
        UnitNo un = new UnitNo();
        un.setAccess_token(accessToken);
        un.setAppid(appId);
        un.setParams(encryParam);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, un, String.class);
        log.info("调用单位编号密码校验接口，返回信息为：{}", JSON.toJSONString(stringResponseEntity));
        String body = stringResponseEntity.getBody();
        // 进行解密
        return SM4SecureUtils.decryStr(body, key);
    }

    /**
     * 检查是否成功调用,成功返回数据
     *
     * @param jsonObject
     */
    private Map<String, Object> checkIsNormal(JSONObject jsonObject) {

        String code = jsonObject.getString("respCode");
        String respMsg = jsonObject.getString("respMsg");

        Assert.notNull(code, "查询单位编号密码校验出错");

        // 如果 code 不等于 1或者等于 100 说明错误
        if (!(code.equals("1") || code.equals("100"))) {
            log.warn("单位编号密码校验错误，原因：{}", respMsg);
            throw new RuntimeException(respMsg);
        }

        String info = jsonObject.getString("dsinfo");
        List<Map<String, Object>> dSInfo = JSONArray.parseObject(info, List.class);
        log.info("获取到的公司信息为：{}", JSON.toJSONString(dSInfo));

        // 检查返回主信息是否为空
        if (CollectionUtil.isEmpty(dSInfo)) {
            throw new RuntimeException("查询单位编号密码校验出错");
        }

        return dSInfo.get(0);
    }

    /**
     * 将参数进行封装
     *
     * @return
     */
    private DsInfoDTO packageParameters(Map<String, Object> dSInfoMap) {
        DsInfoDTO dsInfoDTO = new DsInfoDTO();
        dsInfoDTO.setCompanyName((String) dSInfoMap.get("data04"));
        dsInfoDTO.setAddress((String) dSInfoMap.get("data06"));
        dsInfoDTO.setUnitNo((String) dSInfoMap.get("data01"));
        dsInfoDTO.setIdCard((String) dSInfoMap.get("data38"));
        dsInfoDTO.setAreaId((String) dSInfoMap.get("data25"));
        log.info(JSON.toJSONString(dsInfoDTO));
        return dsInfoDTO;
    }
}

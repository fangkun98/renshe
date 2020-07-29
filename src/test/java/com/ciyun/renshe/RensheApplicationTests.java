package com.ciyun.renshe;

import com.ciyun.renshe.common.security.SM4SecureUtils;
import com.ciyun.renshe.common.security.SM4Utils;
import com.ciyun.renshe.scheduled.resendmsg.CronTaskRegistrar;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class RensheApplicationTests {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    /**
     * 查询法人信息  - 正式
     *
     * @throws ApiException
     */
/*    @Test
    void contextLoads() throws ApiException {

        RestTemplate restTemplate = new RestTemplate();

        String secretKey = "2VDd9UMoIiBRrwTR";
        SM4Utils sm4 = new SM4Utils();
        String inputStr = "{\n" +
                "\"busiid\":\"ZQ3139\",\n" +
                "\"lshid\":\"3666372\",\n" +
                "\"data01\":\"3702338500\",\n" +
                "\"data02\":\"xxzx225\"\n" +
                "}";
        String cipherText = sm4.getEncStr(inputStr, secretKey);
        System.out.println("密文：" + cipherText);

        Test test1 = new Test1("oxxXqVgm7d", "3EHpLF32fBDFAqi11R3KPeGWUFUG3KiI", cipherText);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://12333.qingdao.gov.cn/qdaio-gett/case/saveApply",
                test1, String.class);
        String body = stringResponseEntity.getBody();
        SM4Utils sm4Utils = new SM4Utils();
        String decStr = sm4Utils.getDecStr(body, secretKey);
        System.out.println("调用接口返回参数：" + decStr);
    }*/

    /**
     * 单位信息校验  -正式
     */
    @Test
    void test5() {

        RestTemplate restTemplate = new RestTemplate();

        String str = "{\n" +
                "\"busiid\":\"ZQ3139\",\n" +
                "\"lshid\":\"1234\",\n" +
                "\"data01\":\"3703356297\",\n" +
                "\"data02\":\"3703356297ydw\"\n" +
                "}";
        String secretKey = "2VDd9UMoIiBRrwTR";
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey(secretKey);

        // 加密
        String cipherText = sm4.getEncStr(str, secretKey);

        Map<String, Object> map = new HashMap<>();
        map.put("params", cipherText);
        map.put("access_token", "3EHpLF32fBDFAqi11R3KPeGWUFUG3KiI");
        map.put("appid", "oxxXqVgm7d");

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://12333.qingdao.gov.cn/qdaio-gett/case/saveApply",
                map, String.class);
        String body = stringResponseEntity.getBody();
        log.info("body:{}", body);
        SM4Utils sm4Utils = new SM4Utils();
        String decStr = sm4Utils.getDecStr(body, secretKey);
        log.info("请求AccessToken返回数据位：{}", decStr);
    }

    /**
     * 法人信息获取  -测试
     *
     * @throws ApiException
     */
/*    @Test
    void contextLoads1() throws ApiException {

        RestTemplate restTemplate = new RestTemplate();

        String secretKey = "kZfWVgghsteA1Xbh";
        SM4Utils sm4 = new SM4Utils();
        String inputStr = "{\n" +
                "\"busiid\":\"ZQ3138\",\n" +
                "\"lshid\":\"3666372\",\n" +
                "\"data01\":\"91370202MA3CKA6X8L\",\n" +
                "\"data02\":\"北京富国东方咨询服务有限公司青岛分公司\"\n" +
                "}";
        String cipherText = sm4.getEncStr(inputStr, secretKey);
        System.out.println("密文：" + cipherText);

        Test1 test1 = new Test1("Pii0yDmFHE", "GIgOVgUVtdxsGFi8hsN4GbO0mUgYNUMF", cipherText);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://221.215.38.123:7001/qdaio-gett/case/saveApply",
                test1, String.class);
        String body = stringResponseEntity.getBody();
        SM4Utils sm4Utils = new SM4Utils();
        String decStr = sm4Utils.getDecStr(body, secretKey);
        System.out.println("调用接口返回参数：" + decStr);
    }*/

    /**
     * 单位信息校验  -测试
     */
    @Test
    void test6() {

        RestTemplate restTemplate = new RestTemplate();

        /*String str = "{\n" +
                "\"busiid\":\"ZQ3139\",\n" +
                "\"lshid\":\"1234\",\n" +
                "\"data01\":\"3703356297\",\n" +
                "\"data02\":\"3703356297ydw\"\n" +
                "}";*/
        String str = "{\n" +
                "\"busiid\":\"ZQ3139\",\n" +
                "\"lshid\":\"1234\",\n" +
                "\"data01\":\"3702924827\",\n" +
                "\"data02\":\"000000a\"\n" +
                "}";
        String secretKey = "kZfWVgghsteA1Xbh";
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey(secretKey);

        // 加密
        String cipherText = sm4.getEncStr(str, secretKey);

        Map<String, Object> map = new HashMap<>();
        map.put("params", cipherText);
        map.put("access_token", "7c007T1n8TFbFRMBR0GQmcD5sG0SqTmg");
        map.put("appid", "Pii0yDmFHE");

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://221.215.38.123:7001/qdaio-gett/case/saveApply",
                map, String.class);
        String body = stringResponseEntity.getBody();
        log.info("body:{}", body);
        SM4Utils sm4Utils = new SM4Utils();
        String decStr = sm4Utils.getDecStr(body, secretKey);
        log.info("单位信息校验：{}", decStr);
    }

    /**
     * 测试获取 access_token
     */
    @Test
    void test1() {

        String secretKey = "kZfWVgghsteA1Xbh";
        RestTemplate restTemplate = new RestTemplate();

        com.ciyun.renshe.Test test = new com.ciyun.renshe.Test("ZQ3101",
                "Pii0yDmFHE", "3703356297", "000000a");

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://221.215.38.123:7001/qdaio-gett/case/saveApply",
                test, String.class);
        String body = stringResponseEntity.getBody();
        SM4Utils sm4Utils = new SM4Utils();
        String decStr = sm4Utils.getDecStr(body, secretKey);
        log.info("请求AccessToken返回数据位：{}", decStr);
    }

    /**
     * 测试
     */
    /*@Test
    void test2() {
        RestTemplate restTemplate = new RestTemplate();

        Test2 test2 = new Test2("oxxXqVgm7d", "ZQ3101",
                "3703356297", "3703356297ydw");

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://221.215.38.123:7001/qdaio-gett/case/saveApply",
                test2, String.class);
        String body = stringResponseEntity.getBody();
        SM4Utils sm4Utils = new SM4Utils();
        String kZfWVgghsteA1Xbh = sm4Utils.getDecStr(body, "kZfWVgghsteA1Xbh");
        log.info("请求AccessToken返回数据位：{}", kZfWVgghsteA1Xbh);
    }*/

    /*@Test
    void test3() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
        request.setUrl("https://video.1dang5.com/renshe/callback/user/add");
        request.setAesKey(Constant.ENCODING_AES_KEY);
        request.setToken(Constant.TOKEN);
        request.setCallBackTag(Collections.singletonList("user_add_org"));
        OapiCallBackRegisterCallBackResponse response = client.execute(request, "75e687009f893f839b4083b73bfcb824");
        log.info("response：{}", JSON.toJSONString(response));
    }*/
    @Test
    void test4() {
        String s = SM4SecureUtils.generateRandomKey(43);
        System.out.println(s);
    }

}

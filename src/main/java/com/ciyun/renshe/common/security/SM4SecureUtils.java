package com.ciyun.renshe.common.security;

import java.util.HashMap;
import java.util.Random;

public class SM4SecureUtils {

    private static final String STRARRAY = "1234567890abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
    private static HashMap<String, SM4Utils> stringSM4UtilsHashMap = new HashMap<String, SM4Utils>();

    /**
     * 生成指定长度随机字符串
     *
     * @param length
     * @return
     */
    public static String generateRandomKey(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        while (--length >= 0) {
            sb.append(STRARRAY.charAt(random.nextInt(62)));
        }
        return sb.toString();
    }


    /**
     * 自定义加密字符
     *
     * @param clearText
     * @param key
     * @return
     */
    public static String encryStr(String clearText, String key) {
        if (clearText == null) {
            return null;
        }
        return getSM4UtilsBean(key).getEncStr(clearText, key);
    }

    /**
     * 自定义解密字符
     *
     * @param cipherText
     * @param key
     * @return
     */
    public static String decryStr(String cipherText, String key) {
        if (cipherText == null) {
            return null;
        }
        return getSM4UtilsBean(key).getDecStr(cipherText, key);
    }

    /**
     * 获取加密bean
     *
     * @param key
     * @return
     */
    public static SM4Utils getSM4UtilsBean(String key) {
        SM4Utils sm4Utils;
        if ((sm4Utils = stringSM4UtilsHashMap.get(key)) == null) {
            sm4Utils = new SM4Utils();
            stringSM4UtilsHashMap.put(key, sm4Utils);
        }
        return sm4Utils;
    }

    /**
     * 字符数组添加签名
     *
     * @param args
     * @return
     */
    /*public static String signToAttributes(String[] args, String seed) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String arg : args) {
            if (!StringUtils.isNotBlank(arg)) {
                return null;
            }
            stringBuffer.append(arg);
        }
        return signToString(stringBuffer.toString(), seed);
    }*/

    /**
     * 字符创建签名
     *
     * @param str
     * @return
     */
    /*public static String signToString(String str, String seed) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str).append(seed);
        return MD5Util.encodeMd5(stringBuffer.toString());
    }*/
}

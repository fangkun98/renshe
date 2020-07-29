package com.ciyun.renshe.common.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sm4国密加密公共类
 */
public class SM4Utils {

    private String secretKey = "";

    private static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    private String iv = "";
    private boolean hexString = false;

    public SM4Utils() {
    }

    public String encryptData_ECB(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("GBK"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_ECB(String cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public String encryptData_CBC(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("GBK"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public String decryptData_CBC(String cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密
     */
    public String getEncStr(String inputStr, String secretKey) {
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = secretKey;
        sm4.hexString = false;

        String cipherText = sm4.encryptData_ECB(inputStr);
        return cipherText;
    }

    /**
     * 解密
     */
    public String getDecStr(String inputStr, String secretKey) {
        SM4Utils sm4Util = new SM4Utils();
        sm4Util.secretKey = secretKey;
        sm4Util.hexString = false;
        String plainText = sm4Util.decryptData_ECB(inputStr);
        return plainText;
    }

    public static void main(String[] args) {
        /*String secretKey = "FOMfmeF8Us2Y99wH";
        SM4Utils sm4 = new SM4Utils();
        String cipherText = sm4.getEncStr("2813833", secretKey);
        System.out.println("密文：" + cipherText);
        String plainText = sm4.getDecStr(cipherText, secretKey);
        System.out.println("明文：" + plainText);*/


        String secretKey = "kZfWVgghsteA1Xbh";
        SM4Utils sm4 = new SM4Utils();
        String inputStr = "{" +
                "appid:Pii0yDmFHE," +
                "access_token:LVrkstGi7rCmGzB0XSQG8S0tAhR2i9gt," +
                "params:{" +
                "busiid:ZQ3138," +
                "lshid:1," +
                "data01:91370203MA3RM2LY4E," +
                "data02:山东慈云信息科技有限公司市北分公司" +
                "}" +
                "}";
        String cipherText = sm4.getEncStr(inputStr, secretKey);
        System.out.println("密文：" + cipherText);
        String plainText = sm4.getDecStr("aUZ+qEhar948+1COEjRlxLIM6bDLoHRiBm5doy1ZTACvV0cdna0u89afyXMcv0ODSzn2QUScoJZzSc03eSrBHO61BHnUczxSSsDq8RuBK6c=",
                secretKey);
        System.out.println("明文：" + plainText);
    }
}

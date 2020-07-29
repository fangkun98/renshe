package com.ciyun.renshe.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 钉钉相关数据
 *
 * @author Admin
 */
@Getter
@ToString
@AllArgsConstructor
public enum DingDingData {

    /**
     * 钉钉 公司 id
     */
    //CORP_ID("ding099e5a9f890a7a5a"),
    CORP_ID("dingef5a76b61475e225a39a90f97fcb1e09"),


    /**
     * 钉钉 应用id
     */
    //AGENT_ID("533700596"),
    AGENT_ID("769741324"),

    /**
     * 应用的唯一标识 key
     */
    //APP_KEY("dinggpyojv9khounrono"),
    APP_KEY("dingwntyvyhgdqduycdy"),

    /**
     * 应用的密钥
     */
    //APP_SECRET("hbTxEBZZGRfV6brSQS1LUrS746iFg6jiHkl-Xvb24vCoiRGs6VD0IH1nKK9xsQGY");
    APP_SECRET("Vo2EARJ7TFv_uOrlB0T2YBLVMiCRBXLD9-BoPB72Y51LcmakW3dRny4QE1ipjTow");

   /* ROBOT_AGENTID("478001"),

    ROBOT_AppKey("dingv2x33deywwpasigw"),

    ROBOT_APPSECRET("0CE83le0cUOWbRF0CkRpVS0haebUX34Sju4vbUtYZSJd612RLE-uAfraBb5GrsND");*/

    private final String value;
}

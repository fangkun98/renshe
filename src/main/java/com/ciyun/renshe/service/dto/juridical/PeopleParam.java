package com.ciyun.renshe.service.dto.juridical;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/5/20 17:12
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class PeopleParam {

    /**
     * 接口id，此处为：ZQ3138
     */
    private String busiid;

    /**
     * 交易流水号id
     * 由企业或灵活就业单位自动生成,但同一个企业或灵活就业单位，每笔提交的交易流水号id必须唯一（此流水号将用于后期问题的排查）
     */
    private String lshid;

    /**
     * 统一信用代码
     */
    private String data01;

    /**
     * 单位名称
     */
    private String data02;

}

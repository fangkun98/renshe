package com.ciyun.renshe.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 日期转换cron表达式
 *
 * @Date 2020/5/18 13:23
 * @Author Admin
 * @Version 1.0
 */
public class CronUtils {
    //"ss mm HH dd MM ? yyyy"
    private static final SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ?");

    /**
     * 功能描述：日期转换cron表达式
     *
     * @param date
     * @return
     */
    public static String formatDateByPattern(Date date) {
        String formatTimeStr = null;
        if (Objects.nonNull(date)) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * convert Date to cron, eg "0 07 10 15 1 ?"
     *
     * @param date : 时间点
     * @return
     */
    public static String getCron(Date date) {
        return formatDateByPattern(date);
    }
}

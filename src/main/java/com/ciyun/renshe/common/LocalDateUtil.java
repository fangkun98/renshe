package com.ciyun.renshe.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;

/**
 * 时间工具类
 *
 * @Date 2020-4-2 10:01:04/message/getMessageHistory
 * @Author kong
 * @Version 1.0
 */
public class LocalDateUtil {

    /**
     * 获取当前日期 格式为：yyyy-MM-dd
     *
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间 格式为：HH:mm:ss:nnn
     *
     * @return
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前时间 格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static Integer getYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static Integer getMonth() {
        return LocalDate.now().getMonthValue();
    }

    /**
     * 获取当前日
     *
     * @return
     */
    public static Integer getDay() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * 根据指定的年月日生成日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static LocalDate getLocalDate(Integer year, Integer month, Integer day) {
        return LocalDate.of(year, month, day);
    }

    /**
     * 判断两个日期是否是同相同
     *
     * @param var1
     * @param var2
     * @return
     */
    public static Boolean compareLocalDate(LocalDate var1, LocalDate var2) {
        return var1.equals(var2);
    }

    /**
     * 判断当前日期与指定日期是否相等，如判断生日等
     *
     * @return
     */
    public static Boolean compareMonthAndDay(LocalDate var1) {
        return MonthDay.of(var1.getMonth(), var1.getDayOfMonth()).equals(MonthDay.from(LocalDate.now()));
    }

    /**
     * 根据传入的时间把小时进行增加
     *
     * @param var1
     * @param hours
     * @return
     */
    public static LocalDateTime plusHours(LocalDateTime var1, Integer hours) {
        return var1.plusHours(hours);
    }

    /**
     * 根据传入的时间把天数进行增加
     *
     * @param var1
     * @param days
     * @return
     */
    public static LocalDateTime plusDays(LocalDateTime var1, Integer days) {
        return var1.plusDays(days);
    }

    /**
     * 根据传入的时间与要增加的周数。把相应的时间进行增加
     *
     * @param var1 传入的时间
     * @param var2 要增加的周数
     * @return
     */
    public static LocalDateTime plusWeeks(LocalDateTime var1, Integer var2) {
        return var1.plus(var2, ChronoUnit.WEEKS);
    }

    /**
     * 根据传入的时间把月份进行增加
     *
     * @param var1
     * @param months
     * @return
     */
    public static LocalDateTime plusMonths(LocalDateTime var1, Integer months) {
        return var1.plusMonths(months);
    }

}

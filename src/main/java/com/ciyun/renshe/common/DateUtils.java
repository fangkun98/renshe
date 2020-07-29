package com.ciyun.renshe.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间处理
 *
 * @Date 2019/8/21 10:01
 * @Author Admin
 * @Version 1.0
 */
public class DateUtils {

    private static ThreadLocal<SimpleDateFormat> dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    /**
     * 将字符串转为时间
     *
     * @param date   字符串的时间
     * @param format 转换格式
     * @return
     * @throws ParseException
     */
    public static Date str2Date(String date, String format) throws ParseException {
        DateFormat dateFormat = DateUtils.dateFormat.get();
        Date parse = dateFormat.parse(date);
        return parse;
    }

    /**
     * 获取当前时间  传入时间格式
     *
     * @return
     */
    public static String getCurrTime4jdk8(String formatter) {
        //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        //LocalDateTime now = LocalDateTime.now();
        return DateTimeFormatter.ofPattern(formatter).format(LocalDateTime.now());
    }

    /**
     * 获取当前时间 格式为 yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrTime4jdk8() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        return DateUtils.dateFormat.get().format(date);
    }

    /**
     * 时间戳转时间
     *
     * @param stamp
     * @return
     */
    public static String stampToTime(Long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(stamp);
    }

    /**
     * 将传入的时间增加 1 天
     *
     * @param date
     * @return
     */
    public static Date plusOneDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 当月第一天
     *
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String dayFirst = df.format(gcLast.getTime());
        return dayFirst + " 00:00:00";

    }

    /**
     * 当月最后一天
     *
     * @return
     */
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        //加一个月
        calendar.add(Calendar.MONTH, 1);
        //设置为该月第一天
        calendar.set(Calendar.DATE, 1);
        //再减一天即为上个月最后一天
        calendar.add(Calendar.DATE, -1);
        String dayLast = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(dayLast);
        dayLast = endStr.toString();
        StringBuffer str = new StringBuffer().append(dayLast);
        return str.toString();

    }

    /**
     * 某一个月第一天和最后一天
     *
     * @param date
     * @return
     */
    public static Map<String, String> getFirstdayLastdayMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String dayFirst = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(dayFirst);
        dayFirst = str.toString();

        //上个月最后一天  //加一个月
        calendar.add(Calendar.MONTH, 1);
        //设置为该月第一天
        calendar.set(Calendar.DATE, 1);
        //再减一天即为上个月最后一天
        calendar.add(Calendar.DATE, -1);
        String dayLast = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(dayLast);
        dayLast = endStr.toString();

        Map<String, String> map = new HashMap<>(5);
        map.put("first", dayFirst);
        map.put("last", dayLast);
        return map;
    }

    /**
     * 获取指定月份所有的天数
     *
     * @return
     */
    public static List<String> getDayListOfMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.setTime(date);
        List<String> list = new ArrayList<>(31);
        //Calendar aCalendar = Calendar.getInstance();
        //年份
        int year = aCalendar.get(Calendar.YEAR);
        //月份
        int month = aCalendar.get(Calendar.MONTH) + 1;
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate;
            if (i < 10) {
                aDate = year + "-" + month + "-" + "0" + i;
            } else {
                aDate = year + "-" + month + "-" + i;
            }

            list.add(aDate);
        }
        return list;
    }

    /**
     * JAVA获取某段时间内的所有日期
     *
     * @param dStart 开始时间
     * @param dEnd   结束时间
     * @return
     */
    public static List<String> findDatesList(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List<String> dateList = new ArrayList<>();
        //别忘了，把起始日期加上
        dateList.add(DateUtils.date2Str(dStart));
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(DateUtils.date2Str(cStart.getTime()));
        }
        return dateList;
    }

    /**
     * 计算两个日期之间的天数，如果入参为两个相同的日期，结果返回 1
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long betweenDays = ((time2 - time1) / (1000 * 3600 * 24)) + 1;
        if (betweenDays == 0L) {
            return 1;
        } else {
            return Integer.parseInt(String.valueOf(betweenDays));
        }
    }

    /**
     * 判断传入的日期是否为周末
     *
     * @param bDate
     * @return
     * @throws ParseException
     */
    public static boolean isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * 获取年
     *
     * @return
     */
    public static String getYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static String getMonday() {
        Calendar cal = Calendar.getInstance();
        int monday = cal.get(Calendar.MONDAY);
        return String.valueOf(monday + 1);
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static List<String> getPastArrayDate(int intervals) {
        List<String> pastDaysList = new ArrayList<>();
        //ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
            //fetureDaysList.add(getFetureDate(i));
        }
        Collections.reverse(pastDaysList);
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

}

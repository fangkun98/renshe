package com.ciyun.renshe.controller;

import com.alibaba.excel.util.DateUtils;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.entity.Area;
import com.ciyun.renshe.entity.EnterpriseScale;
import com.ciyun.renshe.entity.Street;
import com.ciyun.renshe.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/userReport")
public class UserReportController {

    private final UserService userService;
    private final ProblemService problemService;
    private final NoticeService noticeService;
    private final AreaService areaService;
    private final StreetService streetService;
    private final EnterpriseScaleService enterpriseScaleService;

    public UserReportController(UserService userService, ProblemService problemService, NoticeService noticeService, AreaService areaService, StreetService streetService, EnterpriseScaleService enterpriseScaleService) {
        this.userService = userService;
        this.problemService = problemService;
        this.noticeService = noticeService;
        this.areaService = areaService;
        this.streetService = streetService;
        this.enterpriseScaleService = enterpriseScaleService;
    }

    @GetMapping("/getArea")
    @ApiOperation("获取区")
    public Result<HashMap> getArea() {
        List<Area> allArea = areaService.findAllArea();
        //1 为 规模 2为行业
        List<EnterpriseScale> allScale = enterpriseScaleService.findAllEnterpriseScale(1);
        List<EnterpriseScale> allIndustry = enterpriseScaleService.findAllEnterpriseScale(2);
        HashMap<String, Object> map = new HashMap<>();
        map.put("areaList", allArea);
        map.put("scaleList", allScale);
        map.put("industryList", allIndustry);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    @GetMapping("/getStreet")
    @ApiOperation("获取街道")
    public Result<HashMap> getStreet(
            @RequestParam(value = "areaId", defaultValue = "0") Integer areaId) {
        List<Street> allStreet = streetService.findAllStreet(areaId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("streetList", allStreet);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    @GetMapping("/getEnterpriseStatistical")
    @ApiOperation("获取企业统计")
    public Result<HashMap> getEnterpriseStatistical() {
        List<Area> allArea = areaService.findAllArea();
        ArrayList<String> area = new ArrayList<>();
        ArrayList<HashMap<String, Object>> scaleList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> industryList = new ArrayList<>();
        ArrayList<Integer> areaValue = new ArrayList<>();
        for (Area a : allArea) {
            Integer companyCount = userService.findCompanyCount("", a.getAreaId() + "", "", "", "");
            area.add(a.getName());
            areaValue.add(companyCount == null ? 0 : companyCount.intValue());
        }
        //1 为 规模 2为行业
        List<EnterpriseScale> allScale = enterpriseScaleService.findAllEnterpriseScale(1);
        List<EnterpriseScale> allIndustry = enterpriseScaleService.findAllEnterpriseScale(2);
        for (EnterpriseScale e : allScale) {
            Integer companyCountByAll = userService.findCompanyCountByAll("", "", "", "", "", e.getEsId() + "", "");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", e.getScaleName());
            hashMap.put("value", companyCountByAll == null ? 0 : companyCountByAll.intValue());
            scaleList.add(hashMap);
        }
        for (EnterpriseScale e : allIndustry) {
            Integer companyCountByAll = userService.findCompanyCountByAll("", "", "", "", "", "", e.getEsId() + "");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", e.getScaleName());
            hashMap.put("value", companyCountByAll == null ? 0 : companyCountByAll.intValue());
            industryList.add(hashMap);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("area", area);
        map.put("areaValue", areaValue);
        map.put("scaleList", scaleList);
        map.put("industryList", industryList);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    /**
     * firmName:'',企业名称
     * email:'',
     * phone:'',
     * area:'',
     * street:'',街道
     * firmScale:'',规模
     * firmType:'',类型
     */
    @GetMapping("/getEnterprise")
    @ApiOperation("获取企业")
    public Result<HashMap> getEnterprise(Page page,
                                         @RequestParam(value = "area", defaultValue = "") String areaId,
                                         @RequestParam(value = "street", defaultValue = "") String streetId,
                                         @RequestParam(value = "phone", defaultValue = "") String phone,
                                         @RequestParam(value = "email", defaultValue = "") String email,
                                         @RequestParam(value = "firmName", defaultValue = "") String firmName,
                                         @RequestParam(value = "firmScale", defaultValue = "") String esId,
                                         @RequestParam(value = "firmType", defaultValue = "") String industryId) {

        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue());
        List<Map<String, Object>> company = userService.findCompany("", areaId, streetId, "", "", esId, industryId
                , firmName, phone, email);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(company);
        map.put("company", pageInfo);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }



    @GetMapping("/getUserStatistical")
    @ApiOperation("获取用户统计")
    public Result<HashMap> getUserStatistical() {
        List<Area> allArea = areaService.findAllArea();
        ArrayList<String> area = new ArrayList<>();
        ArrayList<HashMap<String, Object>> areaList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> scaleList = new ArrayList<>();
        ArrayList<Integer> areaValue = new ArrayList<>();
        for (Area a : allArea) {
            Integer companyCount = userService.findCountUser("", a.getAreaId() + "", "", "", "");
            area.add(a.getName());
            areaValue.add(companyCount == null ? 0 : companyCount.intValue());
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", a.getName());
            hashMap.put("value", companyCount == null ? 0 : companyCount.intValue());
            areaList.add(hashMap);
        }
        List<EnterpriseScale> allScale = enterpriseScaleService.findAllEnterpriseScale(1);
        for (EnterpriseScale e : allScale) {
            Integer companyCountByAll = userService.findCountUserByAll("", "", "", "", "", e.getEsId() + "", "");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", e.getScaleName());
            hashMap.put("value", companyCountByAll == null ? 0 : companyCountByAll.intValue());
            scaleList.add(hashMap);
        }

        List<EnterpriseScale> allIndustry = enterpriseScaleService.findAllEnterpriseScale(2);
        ArrayList<HashMap<String, Object>> industryList = new ArrayList<>();
        for (EnterpriseScale e : allIndustry) {
            Integer countUserByAll = userService.findCountUserByAll("", "", "", "", "", "", e.getEsId() + "");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", e.getScaleName());
            hashMap.put("value", countUserByAll == null ? 0 : countUserByAll.intValue());
            industryList.add(hashMap);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("area", area);
        map.put("areaValue", areaValue);
        map.put("areaList", areaList);
        map.put("scaleList", scaleList);
        map.put("industryList", industryList);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    @GetMapping("/getUser")
    @ApiOperation("获取人员")
    public Result<HashMap> getUser(Page page,
                                   @RequestParam(value = "area", defaultValue = "") String areaId,
                                   @RequestParam(value = "street", defaultValue = "") String streetId,
                                   @RequestParam(value = "phone", defaultValue = "") String phone,
                                   @RequestParam(value = "email", defaultValue = "") String email,
                                   @RequestParam(value = "peopleName", defaultValue = "") String peopleName,
                                   @RequestParam(value = "firmName", defaultValue = "") String firmName,
                                   @RequestParam(value = "firmScale", defaultValue = "") String esId,
                                   @RequestParam(value = "firmType", defaultValue = "") String industryId) {

        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue());
        List<Map<String, Object>> company = userService.findUser("", areaId, streetId, "", "", esId, industryId
                , firmName, phone, email, peopleName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(company);
        map.put("user", pageInfo);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    @GetMapping("/getUserStatisticalByTime")
    @ApiOperation("获取用户统计")
    public Result<HashMap> getUserStatisticalByTime() {
        String nowDate = getNowDate("yyyy-MM");
        int currentMonthDay = getCurrentMonthDay();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> datevalue = new ArrayList<>();
        for (int i = 1; i <= currentMonthDay; i++) {
            if (i < 10) {
                date.add(nowDate + "-0" + i);
            } else {
                date.add(nowDate + "-" + i);
            }
            datevalue.add(0);
        }

        List<Map<String, Object>> countUserByTime = userService.findCountUserByTime(nowDate);
        for (Map<String, Object> m : countUserByTime) {
            int time = Integer.parseInt(m.get("time").toString());
            datevalue.set(time - 1, m.get("num") == null ? 0 : Integer.parseInt(m.get("num").toString()));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", date);
        map.put("datevalue", datevalue);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    @GetMapping("/getUserStatisticalByWeek")
    @ApiOperation("获取用户统计")
    public Result<HashMap> getUserStatisticalByWeek() {
        String nowDate = getNowDate("yyyy-MM-dd");
        ArrayList<String> data = getTimeInterval(nowDate);
        ArrayList<Integer> datavalue = new ArrayList<>();
        for (String s : data) {
            List<Map<String, Object>> countUserByTime = userService.findCountUserByTime(s);
            if (countUserByTime.size() > 0) {
                datavalue.add(countUserByTime.get(0).get("num") == null ? 0 : Integer.parseInt(countUserByTime.get(0).get("num").toString()));
            } else {
                datavalue.add(0);
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("datevalue", datavalue);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    @GetMapping("/getUserStatisticalByDate")
    @ApiOperation("获取用户统计")
    public Result<HashMap> getUserStatisticalByDate() {
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> datevalue = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                date.add("0" + i + ":00");
            } else {
                date.add("" + i + ":00");
            }
            datevalue.add(0);
        }
        String nowDate = getNowDate("yyyy-MM-dd");
        List<Map<String, Object>> countUserByTime = userService.findCountUserByDate(nowDate);
        for (Map<String, Object> m : countUserByTime) {
            int time = Integer.parseInt(m.get("time").toString());
            datevalue.set(time, m.get("num") == null ? 0 : Integer.parseInt(m.get("num").toString()));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", date);
        map.put("datevalue", datevalue);
        return new Result<HashMap>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    public static String getNowDate(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取当月
     */
    public static int getCurrentMonth() {
        Calendar a = Calendar.getInstance();
        a.roll(Calendar.MONTH, 1);
        int month = a.get(Calendar.MONTH);
        return month;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;

    }

    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;
        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }

    //获取本周日期
    public static ArrayList<String> getTimeInterval(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(myDate);
            // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
            // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            // 获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if (i == 0) {
                    cal.add(Calendar.DATE, 0);
                } else {
                    cal.add(Calendar.DATE, 1);
                }
                String imptimeBegin = sdf.format(cal.getTime());
                strings.add(imptimeBegin);
            }
            return strings;
        } catch (Exception e) {
            System.out.println("错误!");
            return new ArrayList<>();
        }

    }

    public static void main(String[] args) {

        int daysByYearMonth = getDaysByYearMonth(2020, 5);
        System.out.println(daysByYearMonth);
        int currentMonthDay = getCurrentMonthDay();
        int currentMonth = getCurrentMonth();
        System.out.println(currentMonthDay);
        System.out.println(currentMonth);
        System.out.println(DateUtils.DATE_FORMAT_10);
        System.out.println(getDayOfWeekByDate("2020-05-07"));
        System.out.println(getTimeInterval("2020-05-07"));

    }
}

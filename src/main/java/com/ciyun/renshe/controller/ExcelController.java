package com.ciyun.renshe.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.service.CountService;
import com.ciyun.renshe.service.ProblemService;
import com.ciyun.renshe.service.UserService;
import com.ciyun.renshe.service.dto.TestExcelDTO;
import com.ciyun.renshe.service.dto.excel.*;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2020/4/2 15:12
 * @Author Admin
 * @Version 1.0
 */
@Controller
@CrossOrigin
@RequestMapping("/excel")
@Api("Excel导出")
@AllArgsConstructor
public class ExcelController {

    private final UserService userService;
    private final ProblemService problemService;
    private final CountService countService;

    @GetMapping
    @ApiOperation("测试")
    @ResponseExcel(name = "测试", sheet = "1")
    public List<TestExcelDTO> downExcel() {
        List<TestExcelDTO> dataList = new ArrayList<>();
        for (int i = 0; i < 100;  i++) {
            TestExcelDTO data = new TestExcelDTO();
            data.setUserName("tr1" + i);
            data.setPassword("tr2" + i);
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * 导入内部工作人员
     *
     * @return
     */
    @GetMapping("/inner/user")
    @ApiOperation("工作人员导入模板下载")
    @ResponseExcel(name = "导入内部工作人员模板", sheet = "1")
    public List<InnerUserExcelDTO> downInnerUser() {
        List<InnerUserExcelDTO> userExcelDTOS = new ArrayList<>(1);
        InnerUserExcelDTO innerUserExcelDTO = new InnerUserExcelDTO();
        userExcelDTOS.add(innerUserExcelDTO);
        return userExcelDTOS;
    }

    /**
     * 获取企业Excel报表
     *
     * @param areaId
     * @param streetId
     * @param phone
     * @param email
     * @param firmName
     * @param esId
     * @param industryId
     * @return
     */
    @GetMapping("/getEnterpriseExcel")
    @ApiOperation("下载企业Excel报表")
    @ResponseExcel(name = "企业Excel报表", sheet = "1")
    public List<EnterpriseExcelDTO> getEnterpriseExcel(
            @RequestParam(value = "area", defaultValue = "") String areaId,
            @RequestParam(value = "street", defaultValue = "") String streetId,
            @RequestParam(value = "phone", defaultValue = "") String phone,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "firmName", defaultValue = "") String firmName,
            @RequestParam(value = "firmScale", defaultValue = "") String esId,
            @RequestParam(value = "firmType", defaultValue = "") String industryId) {

        List<EnterpriseExcelDTO> companyExcel = userService.findCompanyExcel("",
                areaId, streetId, "", "", esId, industryId, firmName, phone, email);

        if (CollectionUtil.isNotEmpty(companyExcel)) {
            return companyExcel;
        } else {
            List<EnterpriseExcelDTO> enterpriseExcelDTOS = new ArrayList<>(1);
            EnterpriseExcelDTO enterpriseExcelDTO = new EnterpriseExcelDTO();
            enterpriseExcelDTOS.add(enterpriseExcelDTO);
            return enterpriseExcelDTOS;
        }

    }

    @GetMapping("/getUserExcel")
    @ApiOperation("下载人员Excel")
    @ResponseExcel(name = "人员报表", sheet = "1")
    public List<UserExcelDTO> getUserExcel(
            @RequestParam(value = "area", defaultValue = "") String areaId,
            @RequestParam(value = "street", defaultValue = "") String streetId,
            @RequestParam(value = "phone", defaultValue = "") String phone,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "peopleName", defaultValue = "") String peopleName,
            @RequestParam(value = "firmName", defaultValue = "") String firmName,
            @RequestParam(value = "firmScale", defaultValue = "") String esId,
            @RequestParam(value = "firmType", defaultValue = "") String industryId) {

        List<UserExcelDTO> userAllExcel = userService.findUserAllExcel("", areaId, streetId, "", "", esId,
                industryId, firmName, phone, email, peopleName);
        if (CollectionUtil.isNotEmpty(userAllExcel)) {
            return userAllExcel;
        } else {
            UserExcelDTO userExcelDTO = new UserExcelDTO();
            List<UserExcelDTO> userExcelDTOS = new ArrayList<>(1);
            userExcelDTOS.add(userExcelDTO);
            return userExcelDTOS;
        }

    }

    /**
     * 问题报表excel
     *
     * @return
     */
    @GetMapping("/getProblemExcel")
    @ApiOperation("下载问题Excel")
    @ResponseExcel(name = "问题报表Excel", sheet = "1")
    public List<ProblemExcelDTO> getProblemExcel() {
        /*//problemService.getProblemExcel(isSolve,  content,  startDate,  endDate);
        List<ProblemExcelDTO> problemExcel = problemService.getProblemExcel(isSolve, content, startDate, endDate);
        if (CollectionUtil.isNotEmpty(problemExcel)) {
            return problemExcel;
        } else {
            List<ProblemExcelDTO> problemExcelDTOS = new ArrayList<>(1);
            ProblemExcelDTO problemExcelDTO = new ProblemExcelDTO();
            problemExcelDTOS.add(problemExcelDTO);
            return problemExcelDTOS;
        }*/
        Result<List<Map<String, Object>>> result = countService.problemCount();
        List<Map<String, Object>> data = result.getData();
        if (CollectionUtil.isNotEmpty(data)) {
            List<ProblemExcelDTO> problemExcelDTOS = new ArrayList<>(data.size());
            for (Map<String, Object> datum : data) {
                ProblemExcelDTO problemExcelDTO = new ProblemExcelDTO();
                problemExcelDTO.setName((String) datum.get("name"));
                BigDecimal bigDecimal = (BigDecimal) datum.get("problemSum");
                problemExcelDTO.setProblemSum(bigDecimal.intValue());
                problemExcelDTOS.add(problemExcelDTO);
            }
            return problemExcelDTOS;
        } else {
            List<ProblemExcelDTO> problemExcelDTOS = new ArrayList<>(1);
            ProblemExcelDTO problemExcelDTO = new ProblemExcelDTO();
            problemExcelDTOS.add(problemExcelDTO);
            return problemExcelDTOS;
        }

    }

    /**
     * 下载消息阅读率报表
     *
     * @return
     */
    @GetMapping("/getNoticeExcel")
    @ApiOperation("下载消息阅读率报表")
    @ResponseExcel(name = "消息阅读率报表Excel", sheet = "1")
    public List<NoticeExcelDTO> getNoticeExcel() {
        Result<List<Map<String, Object>>> result = countService.noticeCount();
        List<Map<String, Object>> data = result.getData();
        if (CollectionUtil.isNotEmpty(data)) {
            List<NoticeExcelDTO> noticeExcelDTOS = new ArrayList<>(data.size());
            for (Map<String, Object> datum : data) {
                NoticeExcelDTO noticeExcelDTO = new NoticeExcelDTO();
                noticeExcelDTO.setName((String) datum.get("name"));
                noticeExcelDTO.setNoticeNum(datum.get("noticeNum") + "%");
                noticeExcelDTOS.add(noticeExcelDTO);
            }
            return noticeExcelDTOS;
        } else {
            NoticeExcelDTO noticeExcelDTO = new NoticeExcelDTO();
            List<NoticeExcelDTO> noticeExcelDTOS = new ArrayList<>(1);
            noticeExcelDTOS.add(noticeExcelDTO);
            return noticeExcelDTOS;
        }

    }

    /**
     * 获取在线时长前30名
     *
     * @return
     */
    @GetMapping("/getOnLineExcel")
    @ApiOperation("下载在线时长前三十名报表")
    @ResponseExcel(name = "在线时长报表Excel", sheet = "1")
    public List<OnLineExcelDTO> getOnLineExcel() {
        Result<List<Map<String, Object>>> listResult = countService.adminLineCount();
        List<Map<String, Object>> data = listResult.getData();
        if (CollectionUtil.isNotEmpty(data)) {
            List<OnLineExcelDTO> onLineExcelDTOS = new ArrayList<>(data.size());
            for (Map<String, Object> datum : data) {
                OnLineExcelDTO onLineExcelDTO = new OnLineExcelDTO();
                onLineExcelDTO.setName((String) datum.get("name"));
                Double countTime = (Double) datum.get("countTime");
                onLineExcelDTO.setCount(countTime.toString());
                onLineExcelDTOS.add(onLineExcelDTO);
            }
            return onLineExcelDTOS;
        } else {
            OnLineExcelDTO onLineExcelDTO = new OnLineExcelDTO();
            List<OnLineExcelDTO> onLineExcelDTOS = new ArrayList<>(1);
            onLineExcelDTOS.add(onLineExcelDTO);
            return onLineExcelDTOS;
        }

    }

    /**
     * 获取回复率导出
     *
     * @return
     */
    @GetMapping("/maxReplyChatOwnerExcel")
    @ApiOperation("消息回复率报表")
    @ResponseExcel(name = "回复率导出Excel", sheet = "1")
    public List<MaxReplyChatOwnerExcelDTO> maxReplyChatOwnerExcel() {
        Result<List<Map<String, Object>>> listResult = countService.maxReplyChatOwner();
        List<Map<String, Object>> data = listResult.getData();
        List<MaxReplyChatOwnerExcelDTO> maxReplyChatOwnerExcelDTOS = new ArrayList<>(data.size());
        if (CollectionUtil.isNotEmpty(data)) {
            for (Map<String, Object> datum : data) {
                MaxReplyChatOwnerExcelDTO maxReplyChatOwnerExcelDTO = new MaxReplyChatOwnerExcelDTO();
                maxReplyChatOwnerExcelDTO.setRoomName((String) datum.get("roomName"));
                BigDecimal replyRate = (BigDecimal) datum.get("replyRate");
                maxReplyChatOwnerExcelDTO.setReplyRate(replyRate.toString());
                maxReplyChatOwnerExcelDTO.setReplyTotal(datum.get("replyTotal").toString());
                maxReplyChatOwnerExcelDTO.setOwnerName(datum.get("ownerName").toString());

                maxReplyChatOwnerExcelDTOS.add(maxReplyChatOwnerExcelDTO);
            }
        } else {
            maxReplyChatOwnerExcelDTOS.add(new MaxReplyChatOwnerExcelDTO());
        }
        return maxReplyChatOwnerExcelDTOS;

    }

    /**
     * 群消息响应时间报表
     *
     * @return
     */
    @GetMapping("/getMessageTimeCountExcel")
    @ApiOperation("群消息响应时间报表")
    @ResponseExcel(name = "群消息响应时间报表", sheet = "1")
    public List<MessageTimeCountExcelDTO> getMessageTimeCountExcel() {
        Result<Map<String, Object>> mapResult = countService.chatManagerCount();
        Map<String, Object> data = mapResult.getData();
        List<MessageTimeCountExcelDTO> messageTimeCountExcelDTOS = (List<MessageTimeCountExcelDTO>)
                data.get("messageTimeCountExcel");

        if (CollectionUtil.isEmpty(messageTimeCountExcelDTOS)) {
            messageTimeCountExcelDTOS = new ArrayList<>(1);
            messageTimeCountExcelDTOS.add(new MessageTimeCountExcelDTO());
        }
        return messageTimeCountExcelDTOS;
    }

    /**
     *
     * @return
     * @throws ApiException
     * @throws InterruptedException
     */
    @GetMapping("/getRenSheNoRegisterUser")
    @ApiOperation("未注册人员报表")
    @ResponseExcel(name = "未注册人员", sheet = "1")
    public List<RenSheNoRegisterDTO> getRenSheNoRegisterUser() throws ApiException, InterruptedException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(375576322L);
        request.setHttpMethod("GET");

        OapiUserSimplelistResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        List<OapiUserSimplelistResponse.Userlist> userlist = response.getUserlist();
        System.out.println("大小为：" + userlist.size());

        List<String> list = new ArrayList<>(1000);
        for (OapiUserSimplelistResponse.Userlist userlist1 : userlist) {
            list.add(userlist1.getUserid());
        }


        List<RenSheNoRegisterDTO> us = new ArrayList<>(1000);
        for (String s : list) {
            RenSheNoRegisterDTO userInfo = getUserInfo(s);
            us.add(userInfo);
        }

        return us;
    }

    private RenSheNoRegisterDTO getUserInfo(String userId) throws ApiException, InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        synchronized (this) {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);

            System.out.println(JSON.toJSONString(response));
            RenSheNoRegisterDTO renSheNoRegisterDTO = new RenSheNoRegisterDTO();
            renSheNoRegisterDTO.setMobile(response.getMobile());
            renSheNoRegisterDTO.setName(response.getName());

            System.out.println(atomicInteger.getAndIncrement());
            Thread.sleep(10);
            return renSheNoRegisterDTO;
        }
    }

}

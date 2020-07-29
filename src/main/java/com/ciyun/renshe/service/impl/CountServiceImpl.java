package com.ciyun.renshe.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ciyun.renshe.common.DateUtils;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.entity.Problem;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.service.CountService;
import com.ciyun.renshe.service.dto.excel.MessageTimeCountExcelDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

/**
 * @Date 2020/5/7 13:25
 * @Author Admin
 * @Version 1.0
 */
@Service
@AllArgsConstructor
public class CountServiceImpl implements CountService {

    private final OnLineCountMapper onLineCountMapper;
    private final ChatAtRecordMapper chatAtRecordMapper;
    private final ChatAtMapper chatAtMapper;
    private final ProblemMapper problemMapper;
    private final NoticeChatMapper noticeChatMapper;

    /**
     * 统计群管理报表
     *
     * @return
     */
    @Override
    public Result<Map<String, Object>> chatManagerCount() {

        Result<Map<String, Object>> result = new Result<>(MessageInfo.GET_INFO.getInfo());
        Map<String, Object> resultMap = new LinkedHashMap<>(16);

        // 查询在线时长前十
        //List<Map<String, Object>> onLineTimeMap = onLineCountMapper.chatManagerCount();

        // 在线时长前十
        //resultMap.put("onLineTimeCount", onLineTimeMap);

        // 查询回复率最高的群主
        //List<Map<String, Object>> recordCountMap = chatAtRecordMapper.chatManagerCount();
        //resultMap.put("recordCount", recordCountMap);

        // 查询消息响应情况
        List<Map<String, Object>> messageTimeCount = chatAtMapper.chatManagerCount();
        Map<String, Object> map = messageTimeCount.get(0);
        List<Long> list = new ArrayList<>(10);
        List<MessageTimeCountExcelDTO> messageTimeCountExcelDTOS = new ArrayList<>(7);
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            list.add((Long) stringObjectEntry.getValue());

            String key = stringObjectEntry.getKey();
            MessageTimeCountExcelDTO messageTimeCountExcelDTO = new MessageTimeCountExcelDTO();
            switch (key) {
                case "count1":
                    messageTimeCountExcelDTO.setRespTime("60秒以内");
                    break;
                case "count2":
                    messageTimeCountExcelDTO.setRespTime("60-120秒");
                    break;
                case "count3":
                    messageTimeCountExcelDTO.setRespTime("120-240秒");
                    break;
                case "count4":
                    messageTimeCountExcelDTO.setRespTime("240-360秒");
                    break;
                case "count5":
                    messageTimeCountExcelDTO.setRespTime("360-480秒");
                    break;
                case "count6":
                    messageTimeCountExcelDTO.setRespTime("480-600秒");
                    break;
                case "count7":
                    messageTimeCountExcelDTO.setRespTime("600秒以上");
                    break;
            }
            messageTimeCountExcelDTO.setRespCount((Long) stringObjectEntry.getValue());
            messageTimeCountExcelDTOS.add(messageTimeCountExcelDTO);
        }
        // 前端展示
        resultMap.put("messageTimeCount", list);
        // Excel 导出
        resultMap.put("messageTimeCountExcel", messageTimeCountExcelDTOS);

        /*// 查询群主未回复消息占比
        List<Map<String, Object>> noReplyMap = chatAtMapper.noReplyMessage();
        resultMap.put("noReplyCount", noReplyMap);*/

        return result.setData(resultMap);
    }

    /**
     * 回复率最高群主前十
     *
     * @return
     */
    @Override
    public Result<List<Map<String, Object>>> maxReplyChatOwner() {
        Result<List<Map<String, Object>>> result = new Result<>(MessageInfo.GET_INFO.getInfo());
        List<Map<String, Object>> maxReplayChatOwners = chatAtRecordMapper.maxReplayChatOwner();
        return result.setData(maxReplayChatOwners);
    }

    /**
     * 问题统计
     *
     * @return
     */
    @Override
    public Result<List<Map<String, Object>>> problemCount() {
        List<Map<String, Object>> mapList = problemMapper.problemCount();
        return new Result<List<Map<String, Object>>>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), mapList);
    }

    /**
     * 最近7天每天提交问题提交数量
     *
     * @return
     */
    @Override
    public Result problem7Count() {
        List<String> pastArrayDate = DateUtils.getPastArrayDate(7);

        List<Integer> problemCount = new ArrayList<>(7);
        String prefix = "00:00:00";
        String suffix = "23:59:59";
        for (String s : pastArrayDate) {
            Integer count = problemMapper.selectCount(Wrappers.<Problem>lambdaQuery()
                    .ge(Problem::getCreateTime, s + prefix)
                    .le(Problem::getCreateTime, s + suffix));
            problemCount.add(count);
        }
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), problemCount);
    }

    /**
     * 消息统计
     *
     * @return
     */
    @Override
    public Result<List<Map<String, Object>>> noticeCount() {
        List<Map<String, Object>> mapList = noticeChatMapper.noticeCount();
        for (Map<String, Object> map : mapList) {
            BigDecimal problemSum = (BigDecimal) map.get("noticeNum");
            NumberFormat percentInstance = NumberFormat.getPercentInstance();
            // 百分比 2 位
            percentInstance.setMaximumFractionDigits(2);
            String format = percentInstance.format(problemSum);
            String substring = format.substring(0, format.length() - 1);
            map.put("noticeNum", substring);
        }
        return new Result<List<Map<String, Object>>>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), mapList);
    }

    /**
     * 在线统计时长最长30条
     *
     * @return
     */
    @Override
    public Result<List<Map<String, Object>>> adminLineCount() {
        List<Map<String, Object>> maps = noticeChatMapper.adminLineCount();
        Iterator<Map<String, Object>> iterator = maps.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            Object name = map.get("name");
            if (name == null) {
                iterator.remove();
            }
        }
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), maps);
    }

    /**
     * 行业分布
     *
     * @return
     */
    /*@Override
    public Result industryCount() {




        return null;
    }*/
}

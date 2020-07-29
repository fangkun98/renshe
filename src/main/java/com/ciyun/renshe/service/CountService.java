package com.ciyun.renshe.service;

import com.ciyun.renshe.common.Result;

import java.util.List;
import java.util.Map;

public interface CountService {
    /**
     * 统计群管理报表
     *
     * @return
     */
    Result<Map<String, Object>> chatManagerCount();

    /**
     * 回复条数最多群主
     *
     * @return
     */
    Result<List<Map<String, Object>>> maxReplyChatOwner();

    /**
     * 问题统计
     *
     * @return
     */
    Result<List<Map<String, Object>>> problemCount();

    /**
     * 消息统计
     *
     * @return
     */
    Result<List<Map<String, Object>>> noticeCount();

    /**
     * 最近7天每天提交问题提交数量
     *
     * @return
     */
    Result problem7Count();

    /**
     * 在线统计时长最长30条
     *
     * @return
     */
    Result<List<Map<String, Object>>> adminLineCount();
}

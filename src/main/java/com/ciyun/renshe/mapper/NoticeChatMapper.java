package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.NoticeChat;

import java.util.List;
import java.util.Map;

public interface NoticeChatMapper extends BaseMapper<NoticeChat> {
    List<NoticeChat> findNoticeChatByNoticeId(Integer noticeId);

    /**
     * 消息统计 前30条
     *
     * @return
     */
    List<Map<String, Object>> noticeCount();

    /**
     * 在线统计时长最长30条
     *
     * @return
     */
    List<Map<String, Object>> adminLineCount();
}
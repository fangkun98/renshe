package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.ChatAtRecord;import java.util.List;import java.util.Map;

public interface ChatAtRecordMapper extends BaseMapper<ChatAtRecord> {
    /**
     * 根据 Atid 查询出全部数据
     *
     * @param atId
     * @return
     */
    List<ChatAtRecord> findChatRecordByChatName(Integer atId);

    /**
     * 回复率最高群主前十
     *
     * @return
     */
    List<Map<String, Object>> chatManagerCount();

    /**
     * 回复条数最高群主前十
     */
    List<Map<String, Object>> maxReplayChatOwner();
}
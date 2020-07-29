package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.controller.vo.message.receive.ReturnReceive;import com.ciyun.renshe.entity.ChatAt;import org.apache.ibatis.annotations.Param;import java.util.List;import java.util.Map;

public interface ChatAtMapper extends BaseMapper<ChatAt> {
    /**
     * 根据用户id 查询出数据
     *
     * @param adminId
     * @return
     */
    List<ReturnReceive> findChatAtByUserId(@Param("adminId") Integer adminId);

    /**
     * 查询全部历史数据
     *
     * @param userId
     * @return
     */
    List<ReturnReceive> findAllChatAtByUserId(Integer userId);

    /**
     * 查询消息响应情况
     *
     * @return
     */
    List<Map<String, Object>> chatManagerCount();

    /**
     * 查询群主未回复消息占比
     *
     * @return
     */
    List<Map<String, Object>> noReplyMessage();
}
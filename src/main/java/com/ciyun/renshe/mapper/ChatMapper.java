package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.Chat;import org.apache.ibatis.annotations.Param;import org.apache.ibatis.annotations.Select;import java.util.List;import java.util.Map;

public interface ChatMapper extends BaseMapper<Chat> {
    /**
     * 查询全部群住址
     *
     * @return
     */
    @Select("SELECT position FROM dd_chat where flag = 1 GROUP BY position ")
    List<String> getAllPosition();

    /**
     * @param streetIds
     * @param gridId
     * @return
     */
    List<Map> findAllChat(@Param("streetIds") List<Integer> streetIds, @Param("gridId") List<String> gridId);

    List<Map> findAllChat2DD(@Param("userId") Integer userId, @Param("type") Integer type);

    List<Chat> findChatByESId(Integer esId);
}
package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.Problem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProblemMapper extends BaseMapper<Problem> {
    /**
     * 根据 反馈条件查询问题列表
     *
     * @param isSolve
     * @return
     */
    List<Problem> findProblems(@Param("isSolve") Integer isSolve);

    /**
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param chatId
     * @param type     1 已反馈 0 未反馈 2全部
     * @return
     */
    Integer findCount(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                      @Param("gridId") String gridId, @Param("chatId") String chatId, @Param("type") int type);

    /**
     * 统计前30条最高问题数
     *
     * @return
     */
    List<Map<String, Object>> problemCount();
}
package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.User;
import com.ciyun.renshe.entity.po.UserPO;
import com.ciyun.renshe.service.dto.excel.EnterpriseExcelDTO;
import com.ciyun.renshe.service.dto.excel.UserExcelDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据 ddUserId 查找相关用户
     *
     * @return
     */
    @Select("select * from dd_user where dd_user_id = #{ddUserId} and flag = 1")
    User findUserByDDUserId(String ddUserId);

  /**
   * 根据用户id 查询用户
   *
   * @param userId
   * @return
   */
  @Select("select user_id, name, mobile, dd_user_id from dd_user where user_id = #{userId,jdbcType=INTEGER}")
  List<UserPO> findUserByUserId(@Param("userId") Integer userId);

    /**
     * 查询对应网格下的所有人员
     *
     * @param
     * @return
     */
    List<User> getGrIdChatInfo(@Param("chatId") String chatId, @Param("mobile") String mobile,
                               @Param("name") String name);

    /**
     * 根据chatId获取总数
     *
     * @param chatId
     * @return
     */
    Integer getGrIdChatInfoCount(String chatId);

    Integer findCountUser(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                          @Param("gridId") String gridId, @Param("chatId") String chatId);

    Integer findCompanyCount(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                             @Param("gridId") String gridId, @Param("chatId") String chatId);

    Integer findCompanyCountAll(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                                @Param("gridId") String gridId, @Param("chatId") String chatId,
                                @Param("esId") String esId, @Param("industryId") String industryId);

    Integer findCountUserAll(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                             @Param("gridId") String gridId, @Param("chatId") String chatId,
                             @Param("esId") String esId, @Param("industryId") String industryId);

    List<Map<String, Object>> findCountUserBytime(@Param("time") String time);

    List<Map<String, Object>> findCountUserByDate(@Param("time") String time);

    /**
     * 企业报表
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param chatId
     * @param esId
     * @param industryId
     * @param name
     * @param phone
     * @param email
     * @return
     */
    List<Map<String, Object>> findCompanyAll(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                                             @Param("gridId") String gridId, @Param("chatId") String chatId,
                                             @Param("esId") String esId, @Param("industryId") String industryId,
                                             @Param("name") String name, @Param("phone") String phone, @Param("email") String email);

    /**
     * 企业报表导出
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param chatId
     * @param esId
     * @param industryId
     * @param name
     * @param phone
     * @param email
     * @return
     */
    List<EnterpriseExcelDTO> findCompanyAllExcel(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                                                 @Param("gridId") String gridId, @Param("chatId") String chatId,
                                                 @Param("esId") String esId, @Param("industryId") String industryId,
                                                 @Param("name") String name, @Param("phone") String phone, @Param("email") String email);

    List<Map<String, Object>> findUserAll(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                                          @Param("gridId") String gridId, @Param("chatId") String chatId,
                                          @Param("esId") String esId, @Param("industryId") String industryId,
                                          @Param("name") String name, @Param("phone") String phone, @Param("email") String email, @Param("peopleName") String peopleName);

    List<UserExcelDTO> findUserAllExcel(@Param("city") String city, @Param("areaId") String areaId, @Param("streetId") String streetId,
                                        @Param("gridId") String gridId, @Param("chatId") String chatId,
                                        @Param("esId") String esId, @Param("industryId") String industryId,
                                        @Param("name") String name, @Param("phone") String phone, @Param("email") String email, @Param("peopleName") String peopleName);

    /**
     * 根据网格查询下边所有的所属人员
     *
     * @param chatId
     * @return
     */
    List<UserPO> findUserByChatId(String chatId);
}
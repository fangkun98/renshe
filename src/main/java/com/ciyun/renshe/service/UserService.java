package com.ciyun.renshe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.PageResult;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.login.LoginVO;
import com.ciyun.renshe.controller.vo.user.*;
import com.ciyun.renshe.entity.User;
import com.ciyun.renshe.service.dto.excel.EnterpriseExcelDTO;
import com.ciyun.renshe.service.dto.excel.UserExcelDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    /**
     * 添加成员 信息
     *
     * @param userVO
     * @return
     */
    Result createUser(CreateDDUserVO userVO);

    /**
     * 修改成员信息
     *
     * @param userVO
     * @return
     */
    Result updateUser(List<UpdateUserVO> userVO);

    /**
     * 删除成员信息
     *
     * @param ddUserId
     * @return
     */
    Result deleteUser(String ddUserId);

    /**
     * 获取部门下的用户详情
     *
     * @param page
     * @param departmentId
     * @return
     */
    Result getUserDetailByDeptId(Page page, String departmentId);

    /**
     * 根据名称和手机号查询用户
     *
     * @param name
     * @param phone
     * @return
     */
    PageInfo<User> searchUser(Page page, String name, String phone);

    /**
     * 添加成员信息,向数据库添加成员
     *
     * @param userVO
     * @return
     */
    Result saveUser(CreateUserVO userVO);

    /**
     * 获取管理员列表
     *
     * @return
     */
    Result getAdminList();

    /**
     * 钉钉免登录
     *
     * @param loginVO
     * @return
     */
    Result doLogin(LoginVO loginVO);

    /**
     * 将用户保存到钉钉小程序中
     *
     * @param saveUser
     * @return
     */
    Result saveUserToDD(SaveUserToDDVO saveUser);

    /**
     * 钉钉小程序免登录
     *
     * @param loginVO
     * @return
     */
    Result ddLogin(LoginVO loginVO);

    /**
     * 根据用户ID 修改用户信息，并将用户自动分配到对应的 网格群下
     *
     * @param updateUser2DDVO
     * @return
     */
    Result updateDingDingUser(UpdateUser2DDVO updateUser2DDVO);

    /**
     * 已经成为钉钉小程序用户后，修改用户信息， 修改对应群，并通知双方群主
     *
     * @param userInfoVO
     * @return
     */
    Result updateUserInfo(UpdateUserInfoVO userInfoVO);

    /**
     * 根据用户id查询用户详细信息
     *
     * @param userId
     * @return
     */
    User findDdUserByUserId(Integer userId);

    /**
     * 根据行业 id 或者 规模id 查询对应用户
     *
     * @param esId
     * @param industryId
     * @return
     */
    PageResult findUserByEsId(Page page, Integer esId, Integer esIdAll, Integer industryId, Integer industryIdAll);

    /**
     * 统计
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param chatId
     * @return
     */
    Integer findCountUser(String city, String areaId, String streetId, String gridId, String chatId);

    Integer findCompanyCount(String city, String areaId, String streetId, String gridId, String chatId);

    Integer findCompanyCountByAll(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId);

    Integer findCountUserByAll(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId);

    //时间查询
    List<Map<String, Object>> findCountUserByTime(String time);

    List<Map<String, Object>> findCountUserByDate(String time);

    List<Map<String, Object>> findCompany(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId
            , String name, String phone, String email);

    List<EnterpriseExcelDTO> findCompanyExcel(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId
            , String name, String phone, String email);

    List<Map<String, Object>> findUser(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId
            , String name, String phone, String email, String peopleName);

    List<UserExcelDTO> findUserAllExcel(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId
            , String name, String phone, String email, String peopleName);

    /**
     * 查询对应权限的所有用户
     *
     * @param roleId
     * @param userId
     * @return
     */
    Result getAreaUserList(Integer roleId, Integer userId);

    /**
     * 审核用户信息
     *
     * @param userInfoListVO
     * @return
     */
    Result findExamineUserInfoList(FindExamineUserInfoListVO userInfoListVO);

    /**
     * 修改成功或者失败
     *
     * @param swapId
     * @param isPass
     * @param feedBack
     * @param adminId
     * @return
     */
    Result updateExamineInfo(Integer swapId, Integer isPass, String feedBack, Integer adminId);

    /**
     * 统计各个区域下的人员总数
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param chatId
     * @return
     */
    Integer findUserCount(String city, String areaId, String streetId, String chatId);

    Integer findCountCompany(String city, String areaId, String streetId, String gridId, String chatId);

    /**
     * 修改公司备注
     *
     * @param updateCompanyRemarkVO
     * @return
     */
    void updateCompanyRemark(UpdateCompanyRemarkVO updateCompanyRemarkVO);

    /**
     * 根据用户id修改网格和换群操作
     *
     * @param updateUserGridVO
     * @return
     */
    Result updateUserGrid(UpdateUserGridVO updateUserGridVO);

    /**
     * 根据标签查询对应的公司列表
     *
     * @param esId
     * @param page
     * @return
     */
    Result findUserByTag(Integer esId, Page page);

    /**
     * 修改用户公司
     *
     * @param updateUserCompany
     * @return
     */
    Result updateUserCompany(UpdateUserCompanyVO updateUserCompany);
}

















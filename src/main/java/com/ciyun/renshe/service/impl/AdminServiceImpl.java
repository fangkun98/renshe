package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.*;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.sdk.UserSDKUtil;
import com.ciyun.renshe.controller.vo.admin.SaveAdminByMobileAndNameVO;
import com.ciyun.renshe.controller.vo.manager.SaveAdmin;
import com.ciyun.renshe.controller.vo.manager.SaveAdminVO;
import com.ciyun.renshe.entity.*;
import com.ciyun.renshe.entity.po.AdminPO;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.service.AdminService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final AreaPermissionsMapper areaPermissionsMapper;
    private final AreaMapper areaMapper;
    private final UserRoleMapper userRoleMapper;
    private final StreetMapper streetMapper;

    /**
     * 保存管理员
     *
     * @param adminParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAdmin(SaveAdminVO adminParam) {

        for (SaveAdmin saveAdmin : adminParam.getAdmins()) {

            String mobile = saveAdmin.getMobile();
            List<Admin> admins = adminMapper.selectList(Wrappers.<Admin>lambdaQuery().eq(Admin::getMobile, mobile));
            if (CollectionUtil.isNotEmpty(admins)) {
                break;
            }

            Admin admin = new Admin();
            BeanUtils.copyProperties(saveAdmin, admin);
            admin.setCreateTime(new Date());
            admin.setFlag(1);

            List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().select(User::getUserId)
                    .eq(User::getMobile, admin.getMobile()).eq(User::getFlag, 1));

            // 如果查询结果不为空
            if (CollectionUtil.isNotEmpty(users)) {
                User user = users.get(0);
                user.setIsAdmin(2);
                user.setIsConfirm(1);
                userMapper.updateById(user);

                admin.setUserId(user.getUserId());
                adminMapper.insert(admin);
            } else {

                DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
                OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
                request.setMobile(admin.getMobile());

                try {
                    OapiUserGetByMobileResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
                    if (response.getErrcode().equals(0L)) {
                        User user = new User();
                        user.setMobile(saveAdmin.getMobile());
                        user.setIsConfirm(1);
                        user.setIsAdmin(1);
                        user.setDdUserId(response.getUserid());
                        userMapper.insert(user);
                        admin.setUserId(user.getUserId());
                        adminMapper.insert(admin);
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据adminId 删除管理员
     *
     * @param adminId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteAdminById(Integer adminId) {

        Result<Object> result = new Result<>(MessageInfo.DELETE_INFO.getInfo());

        Admin a = adminMapper.selectById(adminId);

        List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                .eq(AreaPermissions::getUserId, a.getUserId()));

        if (CollectionUtil.isNotEmpty(areaPermissions)) {
            AreaPermissions ap = areaPermissions.get(0);
            Integer areaId = ap.getAreaId();
            Area area = areaMapper.selectById(areaId);
            return result.setFlag(false).setCode(StatusCode.ERROR)
                    .setMessage("选择的管理员在" + area.getName() + "下绑定了管理权限，请先删除");
        }

        Integer userId = a.getUserId();
        User user = userMapper.selectById(userId);
        user.setIsAdmin(0);
        userMapper.updateById(user);

        /*Admin admin = new Admin();
        admin.setFlag(0);
        admin.setAdminId(adminId);*/
        adminMapper.deleteById(adminId);
        return result;
    }

    /**
     * 查询全部管理员
     *
     * @param page
     * @param adminName
     * @param mobile
     * @return
     */
    @Override
    public Result findAllAdmin(Page page, String adminName, String mobile) {

        Result result = new Result(MessageInfo.GET_INFO.getInfo());

        /*QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(adminName)) {
            wrapper.eq("admin_name", adminName);
        }

        if (StringUtils.isNotBlank(mobile)) {
            wrapper.eq("mobile", mobile);
        }
        wrapper.eq("flag", 1);*/

        //List<AdminPO> list = adminMapper.findAllAdmin(adminName,mobile);

        PageInfo<AdminPO> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> adminMapper.findAllAdmin(adminName, mobile));
        return result.setData(new PageResult(pageInfo.getTotal(), pageInfo.getList()));
    }

    /**
     * 根据手机号和名称添加管理员
     *
     * @param adminVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveAdminByMobileAndName(SaveAdminByMobileAndNameVO adminVO) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        if (adminVO.getStreetId() == null && adminVO.getAreaId() == null) {
            return result.setMessage("请选择对应的区或者街道").setCode(StatusCode.ERROR).setFlag(false);
        }

        List<Integer> roleIds = adminVO.getPermission().getRoleIds();

        boolean flag = !(roleIds.contains(1) || roleIds.contains(2));
        System.out.println(flag);
        // 排除 超级管理员与青岛市管理员
        if (flag) {
            List<Integer> areaIds = adminVO.getPermission().getAreaIds();
            // 如果过添加区管理员，判断是否有权限操作
            if (adminVO.getAreaId() != null && adminVO.getStreetId() == null) {
                if (CollectionUtil.isEmpty(areaIds)) {
                    return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("你不是这个区的管理员，无法添加区管理员");
                }
                // 如果该管理员不包含该区的权限
                if (!areaIds.contains(adminVO.getAreaId())) {
                    return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("你不是这个区的管理员，无法添加区管理员");
                }
            }
        }

        String ddUserId = null;
        try {
            // 根据手机号查询是否已经加入到钉钉中
            OapiUserGetByMobileResponse userByMobile = UserSDKUtil.getUserByMobile(adminVO.getMobile());
            if (userByMobile.getErrcode().equals(0L)) {
                ddUserId = userByMobile.getUserid();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        // 返回信息
        if (StringUtils.isBlank(ddUserId)) {
            return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("该手机号对应的用户未加入到钉钉中，请先通知该用户加入到钉钉中");
        }

        User userByDDUserId = userMapper.findUserByDDUserId(ddUserId);
        User user = new User();
        if (userByDDUserId != null) {
            // 说明这个用户已经添加到数据库中，将此用户修改为管理员
            user.setUserId(userByDDUserId.getUserId());
            user.setIsAdmin(1);
            user.setIsConfirm(1);
            user.setIsInnerUser(1);
            userMapper.updateById(user);
        } else {
            // 新增管理员
            user.setIsAdmin(1);
            user.setIsConfirm(1);
            user.setIsInnerUser(1);
            user.setCreateTime(new Date());

            user.setMobile(adminVO.getMobile());
            user.setDdUserId(ddUserId);
            user.setName(adminVO.getName());
            user.setCompanyName("青岛市人力资源和社会保障局信息中心");

            userMapper.insert(user);
            userByDDUserId = user;
        }
        // 保存到 user_role
        UserRole userRole = new UserRole();
        userRole.setUserId(userByDDUserId.getUserId());

        // 说明设置为区管理
        if (adminVO.getAreaId() != null && adminVO.getStreetId() == null) {
            userRole.setRoleId(3);
        }
        // 说明为街道管理员
        if (adminVO.getStreetId() != null) {
            userRole.setRoleId(4);
        }
        // 判断是否已经存在
        List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userRole.getUserId())
                .eq(UserRole::getRoleId, userRole.getRoleId()));
        if (CollectionUtil.isEmpty(userRoles)) {
            userRoleMapper.insert(userRole);
        }

        List<Admin> admins = adminMapper.selectList(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getUserId, userByDDUserId.getUserId())
                .eq(Admin::getFlag, 1));
        if (CollectionUtil.isEmpty(admins)) {
            // admin 表
            Admin admin = new Admin();
            admin.setAdminName(userByDDUserId.getName());
            admin.setMobile(userByDDUserId.getMobile());
            admin.setCreateTime(new Date());
            admin.setFlag(1);
            admin.setUserId(userByDDUserId.getUserId());
            adminMapper.insert(admin);
        }

        // sys_area_permissions 表
        AreaPermissions areaPermissions = new AreaPermissions();
        if (adminVO.getAreaId() != null) {
            areaPermissions.setAreaId(adminVO.getAreaId());
            areaPermissions.setAdminType(3);
        }
        if (adminVO.getStreetId() != null) {
            Street street = streetMapper.selectById(adminVO.getStreetId());
            areaPermissions.setAreaId(street.getAreaId());
            areaPermissions.setStreetId(street.getStreetId());
            areaPermissions.setAdminType(4);
        }
        areaPermissions.setUserId(userByDDUserId.getUserId());

        LambdaQueryWrapper<AreaPermissions> queryWrapper = new QueryWrapper<AreaPermissions>().lambda();
        // 如果区不为空
        if (areaPermissions.getAreaId() != null) {
            queryWrapper.eq(AreaPermissions::getAreaId, areaPermissions.getAreaId());
        }
        // 街道不为空
        if (areaPermissions.getStreetId() != null) {
            queryWrapper.eq(AreaPermissions::getStreetId, areaPermissions.getStreetId());
        }
        // 类型不为空
        if (areaPermissions.getAdminType() != null) {
            queryWrapper.eq(AreaPermissions::getAdminType, areaPermissions.getAdminType());
        }
        // 用户id不为空
        if (areaPermissions.getUserId() != null) {
            queryWrapper.eq(AreaPermissions::getUserId, areaPermissions.getUserId());
        }

        List<AreaPermissions> permissions = areaPermissionsMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(permissions)) {
            areaPermissionsMapper.insert(areaPermissions);
        }

        return result;
    }
}



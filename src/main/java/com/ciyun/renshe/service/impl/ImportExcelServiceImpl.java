package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ciyun.renshe.common.dingding.sdk.UserSDKUtil;
import com.ciyun.renshe.common.excel.ExcelUtils;
import com.ciyun.renshe.entity.Admin;
import com.ciyun.renshe.entity.Area;
import com.ciyun.renshe.entity.Street;
import com.ciyun.renshe.entity.User;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.service.ImportExcelService;
import com.ciyun.renshe.service.dto.excel.AdminExcelDTO;
import com.ciyun.renshe.service.dto.excel.InnerUserExcelDTO;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Date 2020/5/22 16:56
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class ImportExcelServiceImpl implements ImportExcelService {

    private final UserMapper userMapper;
    private final AreaMapper areaMapper;
    private final StreetMapper streetMapper;
    private final AdminMapper adminMapper;
    private final UserRoleMapper userRoleMapper;
    private final AreaPermissionsMapper areaPermissionsMapper;

    @Override
    public void importInnerUserInfo(MultipartFile file) {
        List<InnerUserExcelDTO> innerUserExcelDTOS = ExcelUtils.readExcel("", InnerUserExcelDTO.class, file);
        List<User> users = new ArrayList<>(innerUserExcelDTOS.size());
        innerUserExcelDTOS.forEach(innerUserExcelDTO -> {
            User user = new User();
            BeanUtils.copyProperties(innerUserExcelDTO, user);
            users.add(user);
        });
        for (User user : users) {
            if (StringUtils.isNotBlank(user.getMobile())) {
                List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery()
                        .eq(User::getMobile, user.getMobile())
                        .eq(User::getFlag, 1));
                if (CollectionUtil.isEmpty(userList)) {
                    // 进行用户的添加
                    user.setCreateTime(new Date());
                    user.setCompanyName("青岛市人力资源和社会保障局");
                    user.setIsConfirm(1);
                    user.setFlag(1);
                    user.setIsAdmin(2);
                    user.setIsInnerUser(1);
                    if (StringUtils.isNotBlank(user.getArea())) {
                        List<Area> areas = areaMapper.selectList(Wrappers.
                                <Area>lambdaQuery().like(Area::getName, user.getArea()));
                        if (CollectionUtil.isNotEmpty(areas)) {
                            user.setAreaId(areas.get(0).getAreaId());
                        }
                    }
                    if (StringUtils.isNotBlank(user.getStreet())) {
                        List<Street> streets = streetMapper.selectList(Wrappers.<Street>lambdaQuery()
                                .like(Street::getStreetName, user.getStreet()));
                        if (CollectionUtil.isNotEmpty(streets)) {
                            user.setStreetId(streets.get(0).getStreetId());
                        }
                    }

                    // 调用钉钉根据手机号获取用户信息接口
                    if (StringUtils.isNotBlank(user.getMobile())) {
                        try {
                            OapiUserGetByMobileResponse userByMobile = UserSDKUtil.getUserByMobile(user.getMobile());
                            if (userByMobile.getErrcode().equals(0L)) {
                                user.setDdUserId(userByMobile.getUserid());
                            }
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }

                    } else {
                        continue;
                    }

                    userMapper.insert(user);

                    List<Admin> admins = adminMapper.selectList(Wrappers.<Admin>lambdaQuery()
                            .eq(Admin::getMobile, user.getMobile())
                            .eq(Admin::getFlag, 1));
                    if (CollectionUtil.isEmpty(admins)) {
                        Admin admin = new Admin();
                        admin.setFlag(1);
                        admin.setUserId(user.getUserId());
                        admin.setCreateTime(new Date());
                        admin.setAdminName(user.getName());
                        admin.setMobile(user.getMobile());
                        adminMapper.insert(admin);
                    }

                }
            }
        }
    }

    /**
     * 导入管理员
     *
     * @param file
     * @param areaOrStreet 1 为市管理员 2 为街道管理员
     */
    @Override
    @Deprecated
    public void importAdmin(MultipartFile file, Integer areaOrStreet) {

        List<AdminExcelDTO> adminExcelDTOS = ExcelUtils.readExcel("", AdminExcelDTO.class, file);

        Set<AdminExcelDTO> errorAdmin = new HashSet<>(10);
        List<AdminExcelDTO> successAdmin = new ArrayList<>(10);

        // 查询企业全部手机号
        List<String> allUserMobile = this.getAllUserMobile();

        if (CollectionUtil.isNotEmpty(allUserMobile)) {

            for (AdminExcelDTO adminExcelDTO : adminExcelDTOS) {
                String mobile = adminExcelDTO.getMobile();
                if (allUserMobile.contains(mobile)) {
                    successAdmin.add(adminExcelDTO);
                } else {
                    errorAdmin.add(adminExcelDTO);
                }
            }

        }

        // 将成员添加到相关列表中
        // 1. 添加到 user 表中
        // 2. 添加到 admin 表中
        // 3. 添加到 user_role 表中
        // 4. 添加到 sys_area_permissions 表中
        if (CollectionUtil.isNotEmpty(successAdmin)) {
            for (AdminExcelDTO adminExcelDTO : successAdmin) {

                Integer areaId;
                Integer streetId;

                if (areaOrStreet.equals(1)) {
                    // 查询区名称或者街道名称是否和数据库匹配
                    if (StringUtils.isNotBlank(adminExcelDTO.getArea())) {
                        List<Area> areas = areaMapper.selectList(Wrappers.<Area>lambdaQuery()
                                .like(Area::getName, adminExcelDTO.getArea()));
                        if (CollectionUtil.isNotEmpty(areas)) {
                            Area area = areas.get(0);
                            areaId = area.getAreaId();
                        } else {
                            // 说明未查询到对应的区，将对应用户添加到 失败列表
                            errorAdmin.add(adminExcelDTO);
                        }
                    } else {
                        errorAdmin.add(adminExcelDTO);
                    }
                }

                // 如果是街道，查询区名称和街道名称是否正确
                if (areaOrStreet.equals(2)) {
                    // 查询区名称或者街道名称是否和数据库匹配
                    if (StringUtils.isNotBlank(adminExcelDTO.getArea())) {
                        List<Area> areas = areaMapper.selectList(Wrappers.<Area>lambdaQuery()
                                .like(Area::getName, adminExcelDTO.getArea()));
                        if (CollectionUtil.isNotEmpty(areas)) {
                            Area area = areas.get(0);
                            areaId = area.getAreaId();
                        } else {
                            // 说明未查询到对应的区，将对应用户添加到 失败列表
                            errorAdmin.add(adminExcelDTO);
                        }
                    } else {
                        errorAdmin.add(adminExcelDTO);
                    }

                    // 查询是否有此街道
                    if (StringUtils.isNotBlank(adminExcelDTO.getStreet())) {
                        List<Street> streets = streetMapper.selectList(Wrappers.<Street>lambdaQuery()
                                .like(Street::getStreetName, adminExcelDTO.getStreet()));
                        if (CollectionUtil.isNotEmpty(streets)) {
                            Street street = streets.get(0);
                            streetId = street.getStreetId();
                        } else {
                            errorAdmin.add(adminExcelDTO);
                        }
                    } else {
                        errorAdmin.add(adminExcelDTO);
                    }

                    /*if (areaId == null ||  streetId == null) {
                        continue;
                    }*/

                }

                User user = new User();
                user.setName(adminExcelDTO.getName());
                user.setMobile(adminExcelDTO.getMobile());
                user.setIsAdmin(1);
                user.setIsConfirm(1);
                user.setIsInnerUser(1);
                user.setCreateTime(new Date());

                OapiUserGetByMobileResponse userByMobile = null;
                try {
                    userByMobile = UserSDKUtil.getUserByMobile(adminExcelDTO.getMobile());
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                if (userByMobile != null && userByMobile.getErrcode().equals(0L)) {
                    user.setDdUserId(userByMobile.getUserid());
                } else {
                    errorAdmin.add(adminExcelDTO);
                    continue;
                }
                // 判断一下是否存在此用户
                User userByDDUserId = userMapper.findUserByDDUserId(user.getDdUserId());
                // 说明已经存在此用户，将用户修改为管理员
                if (userByDDUserId != null) {
                    userByDDUserId.setIsAdmin(1);
                    userByDDUserId.setIsConfirm(1);
                    userByDDUserId.setIsInnerUser(1);

                    User updateUser = new User();
                    updateUser.setIsInnerUser(1);
                    updateUser.setUserId(userByDDUserId.getUserId());
                    updateUser.setIsAdmin(1);
                    userMapper.updateById(updateUser);
                } else {
                    // 将用户进行插入
                    userMapper.insert(user);
                    userByDDUserId = user;
                }

                // 将数据插入 admin 表
                List<Admin> admins = adminMapper.selectList(Wrappers.<Admin>lambdaQuery()
                        .eq(Admin::getUserId, userByDDUserId.getUserId())
                        .eq(Admin::getFlag, 1));
                if (CollectionUtil.isEmpty(admins)) {
                    Admin admin = new Admin();
                    admin.setMobile(userByDDUserId.getMobile());
                    admin.setAdminName(userByDDUserId.getName());
                    admin.setCreateTime(new Date());
                    admin.setFlag(1);
                    admin.setUserId(userByDDUserId.getUserId());
                    adminMapper.insert(admin);
                }

                // 将数据插入 user_role 表
                /*if () {
                }
*/
            }

        }

    }

    /**
     * 获取部门全部手机号
     *
     * @return
     */
    private List<String> getAllUserMobile() {

        boolean flag = true;
        List<String> mobile = new ArrayList<>(10);
        AtomicLong atomicLong = new AtomicLong(0);
        // 查询全部部门
        while (flag) {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
            OapiUserListbypageRequest request = new OapiUserListbypageRequest();
            request.setDepartmentId(1L);
            request.setOffset(atomicLong.longValue());
            request.setSize(5L);
            request.setOrder("entry_asc");
            request.setHttpMethod("GET");
            OapiUserListbypageResponse execute = null;
            try {
                execute = client.execute(request, "75e687009f893f839b4083b73bfcb824");
            } catch (ApiException e) {
                log.warn("根据部门id获取部门下用户信息接口失败，{}", JSON.toJSONString(execute));
                e.printStackTrace();
            }

            if (execute != null && execute.getErrcode().equals(0L)) {
                List<OapiUserListbypageResponse.Userlist> userlist = execute.getUserlist();
                for (OapiUserListbypageResponse.Userlist user : userlist) {
                    if (StringUtils.isNotBlank(user.getMobile())) {
                        mobile.add(user.getMobile());
                    }
                }
                flag = execute.getHasMore();
            } else {
                flag = false;
            }
        }

        return mobile;
    }
}

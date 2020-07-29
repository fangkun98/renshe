package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.*;
import com.ciyun.renshe.controller.vo.user.*;
import com.ciyun.renshe.entity.User;
import com.ciyun.renshe.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/4/3 10:52
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "用户模块")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 根据用户id查询用户详细信息
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("/userId")
    @ApiOperation("根据用户id查询用户详细信息")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "String")
    public Result<User> findDdUserByUserId(@RequestParam String userId) {
        User user = userService.findDdUserByUserId(Integer.parseInt(userId));
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), user);
    }

    /**
     * 查询对应权限的所有用户
     *
     * @param
     * @return
     */
    @GetMapping
    @ApiOperation("查询对应权限的所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "权限id， 1 super 2 市管理员 3 区管理员 4 街道管理员 5. 网格管理员", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "int")
    })
    public Result getAreaUserList(@RequestParam Integer roleId, @RequestParam Integer userId) {
        return userService.getAreaUserList(roleId, userId);
    }

    /**
     * 将用户保存到钉钉小程序中
     *
     * @return
     */
    @PostMapping("/save2dd")
    @ApiOperation("将用户保存到钉钉小程序中")
    public Result saveUser2DD(@RequestBody @Valid SaveUserToDDVO saveUser) {
        return userService.saveUserToDD(saveUser);
    }

    /**
     * 注册钉钉小程序时添加用户信息，并将用户自动分配到对应的 网格群下
     *
     * @return
     */
    @PostMapping("/update2dd")
    @ApiOperation("注册钉钉小程序时添加用户信息")
    public Result updateUser2DD(@RequestBody @Valid UpdateUser2DDVO updateUser2DDVO) {
        return userService.updateDingDingUser(updateUser2DDVO);
    }

    /**
     * 修改用户公司
     *
     * @return
     */
    @PostMapping("/update/company")
    public Result updateUserCompany(@RequestBody @Valid UpdateUserCompanyVO updateUserCompany) {
        return userService.updateUserCompany(updateUserCompany);
    }

    /**
     * 已经成为钉钉小程序用户后，修改用户信息， 修改对应群，并通知双方群主
     *
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("已经成为钉钉小程序用户后，修改用户信息")
    public Result updateUserInfo(@RequestBody @Valid UpdateUserInfoVO userInfoVO) {
        return userService.updateUserInfo(userInfoVO);
    }

    /**
     * 审核用户信息
     *
     * @param userInfoListVO
     * @return
     */
    @ApiOperation("审核用户信息列表")
    @PostMapping("/userinfo/update")
    public Result findExamineUserInfoList(@RequestBody FindExamineUserInfoListVO userInfoListVO) {
        return userService.findExamineUserInfoList(userInfoListVO);
    }

    /**
     * 修改审核或者拒绝
     *
     * @param swapId
     * @param isPass
     * @param feedBack
     * @return
     */
    @GetMapping("/updateExamineInfo")
    @ApiOperation("修改审核或者拒绝")
    public Result updateExamineInfo(@RequestParam Integer swapId,
                                    @RequestParam Integer isPass, String feedBack, @RequestParam Integer userId) {
        return userService.updateExamineInfo(swapId, isPass, feedBack, userId);
    }

    /**
     * 根据用户id修改网格和换群操作
     *
     * @return
     */
    @PostMapping("/updateUserGrid")
    @ApiOperation("根据用户id修改网格和换群操作")
    public Result updateUserGrid(@RequestBody @Valid UpdateUserGridVO updateUserGridVO) {
        return userService.updateUserGrid(updateUserGridVO);
    }

    /**
     * 修改公司备注
     *
     * @param updateCompanyRemarkVO 接收参数
     * @return
     */
    @PostMapping("/update/company/remark")
    @ApiOperation("修改公司备注")
    public Result updateCompanyRemark(@RequestBody @Valid UpdateCompanyRemarkVO updateCompanyRemarkVO) {
        userService.updateCompanyRemark(updateCompanyRemarkVO);
        return new Result(true, StatusCode.OK, MessageInfo.UPDATE_INFO.getInfo());
    }

    /**
     * 获取部门下的用户详情
     *
     * @return
     */
    @GetMapping("/dept/detail")
    @ApiOperation("获取部门下的用户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentId", value = "部门 id", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "开始页码", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = false, paramType = "query", dataType = "int")
    })
    public Result getUserDetailByDeptId(Page page, String departmentId) {
        return userService.getUserDetailByDeptId(page, departmentId);
    }

    /**
     * 获取管理员列表
     *
     * @return
     */
    @GetMapping("/admin")
    @ApiOperation("获取管理员列表")
    public Result getAdminList() {
        return userService.getAdminList();
    }

    /**
     * 根据名称和手机号搜索
     *
     * @param name
     * @param phone
     * @return
     */
    @GetMapping("/search")
    @ApiOperation("根据名称和手机号搜索用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "state", value = "1 为只查询50条", required = false, paramType = "query", dataType = "String")
    })
    public Result<PageResult> searchUser(Page page, Integer state, String name, String phone) {
        PageInfo<User> userPageInfo = userService.searchUser(page, name, phone);
        // 1 代表只查询公司，并且不能超过50条
        int stateFlag = 1;
        if (state != null && state.equals(stateFlag) && userPageInfo.getTotal() > 50) {
            return new Result<>(true, StatusCode.ERROR, "搜索关键词过于泛化，请换一个关键词试试。");
        }
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), new PageResult(userPageInfo.getTotal(), userPageInfo.getList()));
    }

    /**
     * 根据行业 id 或者 规模id 查询对应用户
     *
     * @param esId
     * @param industryId
     * @return
     */
    @GetMapping("/es")
    @ApiOperation("根据行业 id 或者 规模id 查询对应用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "esId", value = "规模id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "industryId", value = "行业id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "esIdAll", value = "是否规模全部选中", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "industryIdAll", value = "是否行业全部选中", required = false, paramType = "query", dataType = "int")
    })
    public Result<PageResult> findUserByEsId(Page page, Integer esId, Integer esIdAll, Integer industryId, Integer industryIdAll) {
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                userService.findUserByEsId(page, esId, esIdAll, industryId, industryIdAll));
    }

    /**
     * 添加成员
     *
     * @param userVO
     * @return
     */
    @PostMapping
    @ApiOperation("添加成员信息")
    public Result createUser(@RequestBody @Valid CreateDDUserVO userVO) {
        return userService.createUser(userVO);
    }

    /**
     * 添加成员信息,向数据库添加成员
     *
     * @param userVO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("添加成员信息,向数据库添加成员")
    public Result saveUser(@RequestBody @Valid CreateUserVO userVO) {
        return userService.saveUser(userVO);
    }

    /**
     * 修改成员信息,包括移除部门，添加部门
     *
     * @param userVO
     * @return
     */
    @PostMapping("/update/updateUserInfo")
    @ApiOperation("修改成员信息")
    public Result updateUserInfo(@RequestBody @Valid UpdateUserVO userVO) {
        List<UpdateUserVO> list = new ArrayList<>();
        list.add(userVO);
        return userService.updateUser(list);
    }

    /**
     * 批量向部门添加用户
     *
     * @param addUserToDept
     * @return
     */
    @PostMapping("/add/dept")
    @ApiOperation("批量向部门添加用户")
    public Result addUserToDept(@RequestBody @Valid AddUserToDeptVO addUserToDept) {
        return userService.updateUser(addUserToDept.getUserVOS());
    }

    /**
     * 删除成员信息
     *
     * @param ddUserId
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation("删除对应用户")
    @ApiImplicitParam(name = "ddUserId", value = "钉钉成员 id", required = true, paramType = "query", dataType = "String")
    public Result deleteUser(@RequestParam String ddUserId) {
        return userService.deleteUser(ddUserId);
    }

    /**
     * 根据自定义标签查询对应的公司列表
     *
     * @param esId
     * @return
     */
    @GetMapping("/findUserByTag")
    @ApiOperation("根据自定义标签查询对应的公司列表")
    public Result findUserByTag(Integer esId, Page page) {
        return userService.findUserByTag(esId, page);
    }
}

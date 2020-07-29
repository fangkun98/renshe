package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.controller.vo.enterprisescale.UpdateCompanyTagVO;
import com.ciyun.renshe.entity.EnterpriseScale;
import com.ciyun.renshe.entity.po.EnterpriseScalePO;
import com.ciyun.renshe.service.EnterpriseScaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Date 2020/4/18 13:54
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "企业规模相关接口")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/es")
@AllArgsConstructor
public class EnterpriseScaleController {

    final private EnterpriseScaleService enterpriseScaleService;

    /**
     * 根据类型 查询所有企业规模列表 或者 标签列表
     * state：
     * 1 企业规模
     * 2 标签
     *
     * @return
     */
    @GetMapping("/state")
    @ApiOperation("查询所有企业规模列表 state 1 企业规模 2 行业")
    public Result findAllEnterpriseScale(@RequestParam Integer state) {
        List<EnterpriseScale> enterpriseScales = enterpriseScaleService.findAllEnterpriseScale(state);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                enterpriseScales);

    }

    /**
     * 树状结构
     *
     * @param state
     * @return
     */
    @GetMapping("/state/list")
    @ApiOperation("查询所有企业规模列表 state 1企业规模 2是行业")
    public Result findAllEnterpriseScaleList(@RequestParam Integer state) {
        List<EnterpriseScalePO> enterpriseScales = enterpriseScaleService.findAllEnterpriseScaleList(state);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                enterpriseScales);
    }

    /**
     * 小程序查询全部行业与规模,分类查询所有企业规模列表
     *
     * @return
     */
    @GetMapping("/type")
    @ApiOperation("小程序端 分类查询所有企业规模列表")
    public Result findAllByType() {
        Map<String, Object> map = enterpriseScaleService.findAllByType();
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    /**
     * 添加企业规模
     *
     * @return
     */
    @PostMapping
    @ApiOperation("添加企业规模")
    public Result addEnterpriseScale(@RequestBody EnterpriseScale enterpriseScale) {
        enterpriseScaleService.save(enterpriseScale);
        return new Result(true, StatusCode.OK, MessageInfo.ADD_INFO.getInfo());
    }

    /**
     * 修改企业规模名称
     *
     * @param enterpriseScale
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改企业规模")
    public Result updateEnterpriseScale(@RequestBody EnterpriseScale enterpriseScale) {
        enterpriseScaleService.saveOrUpdate(enterpriseScale);
        return new Result(true, StatusCode.OK, MessageInfo.UPDATE_INFO.getInfo());
    }

    /**
     * 根据id删除企业规模
     *
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation("根据id删除企业规模")
    @ApiImplicitParam(name = "esId", value = "规模Id", required = true, paramType = "query", dataType = "int")
    public Result deleteEnterpriseScale(@RequestParam Integer esId) {
        enterpriseScaleService.saveOrUpdate(new EnterpriseScale().setEsId(esId).setFlag(0));

        return new Result(true, StatusCode.OK, MessageInfo.DELETE_INFO.getInfo());
    }

    /**
     * 根据公司名称查询全部标签
     *
     * @param companyName
     * @return
     */
    @GetMapping("/companyname/get")
    public Result findTagByCompanyName(String companyName) {
        return enterpriseScaleService.findTagByCompanyName(companyName);
    }

    /**
     * 查询全部标签
     *
     * @param pageType 1规模 2行业 3自定义
     * @return
     */
    @GetMapping("/find/all/tag")
    public Result findAllTag(Page page, @RequestParam Integer pageType) {
        return enterpriseScaleService.findAllTag(page, pageType);
    }

    /**
     * 修改公司的标签
     *
     * @param updateCompanyTagVO
     * @return
     */
    @PostMapping("/updateCompanyTag")
    public Result updateCompanyTag(@RequestBody UpdateCompanyTagVO updateCompanyTagVO) {
        return enterpriseScaleService.updateCompanyTag(updateCompanyTagVO);
    }
}

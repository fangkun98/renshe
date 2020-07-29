package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.*;
import com.ciyun.renshe.controller.vo.enterprisescale.EnterpriseScaleVO;
import com.ciyun.renshe.controller.vo.enterprisescale.UpdateCompanyTagVO;
import com.ciyun.renshe.entity.EnterpriseScale;
import com.ciyun.renshe.entity.User;
import com.ciyun.renshe.entity.po.EnterpriseScalePO;
import com.ciyun.renshe.mapper.EnterpriseScaleMapper;
import com.ciyun.renshe.mapper.UserMapper;
import com.ciyun.renshe.service.EnterpriseScaleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseScaleServiceImpl extends ServiceImpl<EnterpriseScaleMapper, EnterpriseScale> implements EnterpriseScaleService {

    final private EnterpriseScaleMapper enterpriseScaleMapper;
    final private UserMapper userMapper;

    /**
     * 查询所有企业规模列表
     *
     * @return
     */
    @Override
    public List<EnterpriseScale> findAllEnterpriseScale(Integer state) {
        return enterpriseScaleMapper.selectList(Wrappers.<EnterpriseScale>lambdaQuery()
                .eq(EnterpriseScale::getState, state)
                .eq(EnterpriseScale::getFlag, 1));
    }

    /**
     * 分类查询所有企业规模列表
     *
     * @param state
     * @return
     */
    @Override
    public List<EnterpriseScalePO> findAllEnterpriseScaleList(Integer state) {
        List<EnterpriseScalePO> enterpriseScalePOS = enterpriseScaleMapper.findAllEnterpriseScaleList(state);
        return enterpriseScalePOS;
    }

    /**
     * 分类查询所有企业规模列表
     *
     * @return
     */
    @Override
    public Map<String, Object> findAllByType() {
        // 查询全部规模
        List<EnterpriseScale> es = enterpriseScaleMapper.selectList(Wrappers.<EnterpriseScale>lambdaQuery()
                .eq(EnterpriseScale::getState, 1)
                .eq(EnterpriseScale::getFlag, 1));

        // 查询全部行业
        List<EnterpriseScale> industry = enterpriseScaleMapper.selectList(Wrappers.<EnterpriseScale>lambdaQuery()
                .eq(EnterpriseScale::getState, 2)
                .eq(EnterpriseScale::getFlag, 1));

        Map<String, Object> map = new HashMap<>(6);
        map.put("es", es);
        map.put("industry", industry);
        return map;
    }

    /**
     * 根据公司名称查询全部标签
     *
     * @param companyName
     * @return
     */
    @Override
    public Result findTagByCompanyName(String companyName) {

        Result result = new Result(MessageInfo.GET_INFO.getInfo());

        List<User> users = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                .eq(User::getCompanyName, companyName).eq(User::getFlag, 1));

        Set<String> tagList = new LinkedHashSet<>(10);
        List<String> esList = new ArrayList<>(10);

        for (User user : users) {
            // 获取行业和自定义标签
            String industryId = user.getIndustryId();
            if (StringUtils.isNotBlank(industryId)) {
                String[] split = industryId.split(",");
                tagList.addAll(Arrays.asList(split));
            }

            // 获取企业标签id
            Integer esId = user.getEsId();
            if (esId != null) {
                esList.add(esId.toString());
            }
        }

        List<EnterpriseScaleVO> enterpriseScaleVOList = new ArrayList<>(10);

        if (CollectionUtil.isNotEmpty(esList)) {

            // 行业和自定义
            List<EnterpriseScale> enterpriseScales =
                    enterpriseScaleMapper.selectList(Wrappers.<EnterpriseScale>lambdaQuery()
                            .select(EnterpriseScale::getEsId, EnterpriseScale::getScaleName)
                            .eq(EnterpriseScale::getFlag, 1)
                            .eq(EnterpriseScale::getEsId, esList.get(0)));
            for (EnterpriseScale enterpriseScale : enterpriseScales) {
                EnterpriseScaleVO enterpriseScaleVO = new EnterpriseScaleVO();
                BeanUtils.copyProperties(enterpriseScale, enterpriseScaleVO);
                enterpriseScaleVO.setTagType(1);
                enterpriseScaleVOList.add(enterpriseScaleVO);
            }
        }

        if (CollectionUtil.isNotEmpty(tagList)) {
            // 行业和自定义
            List<EnterpriseScale> enterpriseScales =
                    enterpriseScaleMapper.selectList(Wrappers.<EnterpriseScale>lambdaQuery()
                            .select(EnterpriseScale::getEsId, EnterpriseScale::getScaleName)
                            .eq(EnterpriseScale::getFlag, 1)
                            .in(EnterpriseScale::getEsId, tagList));
            for (EnterpriseScale enterpriseScale : enterpriseScales) {
                EnterpriseScaleVO enterpriseScaleVO = new EnterpriseScaleVO();
                BeanUtils.copyProperties(enterpriseScale, enterpriseScaleVO);
                enterpriseScaleVO.setTagType(2);
                enterpriseScaleVOList.add(enterpriseScaleVO);
            }
        }
        return result.setData(enterpriseScaleVOList);
    }

    /**
     * 查询全部标签
     *
     * @param page
     * @param pageType 1规模 2行业 3自定义
     * @return
     */
    @Override
    public Result findAllTag(Page page, Integer pageType) {

        PageInfo<EnterpriseScale> tags = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> enterpriseScaleMapper.selectList(Wrappers.<EnterpriseScale>lambdaQuery()
                        .eq(EnterpriseScale::getFlag, 1)
                        .eq(EnterpriseScale::getState, pageType)));

        List<EnterpriseScaleVO> enterpriseScaleVOList = new ArrayList<>(10);
        for (EnterpriseScale enterpriseScale : tags.getList()) {
            EnterpriseScaleVO enterpriseScaleVO = new EnterpriseScaleVO();
            BeanUtils.copyProperties(enterpriseScale, enterpriseScaleVO);
            enterpriseScaleVO.setTagType(enterpriseScale.getState());
            enterpriseScaleVOList.add(enterpriseScaleVO);
        }
        Map<String, Object> resultMap = new HashMap<>(10);
        resultMap.put("tags", new PageResult(tags.getTotal(), enterpriseScaleVOList));
        resultMap.put("pageType", pageType);
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), resultMap);
    }

    /**
     * 修改公司的标签
     *
     * @param updateCompanyTagVO
     * @return
     */
    @Override
    public Result updateCompanyTag(UpdateCompanyTagVO updateCompanyTagVO) {

        List<String> tagList = updateCompanyTagVO.getTagList();
        String company = updateCompanyTagVO.getCompany();
        List<Integer> esList = new ArrayList<>(1);

        Iterator<String> iterator = tagList.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            EnterpriseScale enterpriseScale = enterpriseScaleMapper.selectById(next);
            if (enterpriseScale != null && enterpriseScale.getState() != null) {
                if (enterpriseScale.getState().equals(1)) {
                    esList.add(enterpriseScale.getEsId());
                    iterator.remove();
                }
            }
        }

        String join = String.join(",", tagList);
        User user = new User();
        if (CollectionUtil.isNotEmpty(esList)) {
            user.setEsId(esList.get(0));
        }
        user.setIndustryId(join + ",");

        userMapper.update(user, Wrappers.<User>lambdaQuery().eq(User::getCompanyName, company));
        return new Result(true, StatusCode.OK, MessageInfo.UPDATE_INFO.getInfo());
    }
}





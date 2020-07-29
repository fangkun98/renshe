package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.controller.vo.area.UpdateGridNameByIdVO;
import com.ciyun.renshe.entity.Area;
import com.ciyun.renshe.entity.Street;
import com.ciyun.renshe.service.AreaService;
import com.ciyun.renshe.service.GridService;
import com.ciyun.renshe.service.StreetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Date 2020/4/16 9:46
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "钉钉小程序区域操作")
@Validated
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/area")
public class AreaController {

    private final AreaService areaService;
    private final StreetService streetService;
    private final GridService gridService;

    /**
     * 查询全部区域数据，包含街道
     *
     * @return
     */
    @GetMapping("/total")
    @ApiOperation("查询全部区域数据，包含街道")
    public Result findAllAreaInfo() {
        return areaService.findAllAreaInfo();
    }

    @GetMapping("/allArea")
    @ApiOperation("查询全部区域数据，不包含街道")
    public Result findAllArea() {
        List<Area> allArea = areaService.findAllArea();
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), allArea);
    }

    @GetMapping("/getStreet")
    @ApiOperation("查询全部区域街道")
    public Result getStreet(@RequestParam(value = "areaId", defaultValue = "0") Integer areaId) {
        List<Street> allStreet = streetService.findAllStreet(areaId);
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), allStreet);
    }

    /**
     * 根据街道id查询街道下的所有网格
     *
     * @param streetId
     * @return
     */
    @GetMapping("/findGridIdByStreetId")
    @ApiOperation("根据街道id查询对应的全部网格群")
    public Result findGridIdByStreetId(Integer streetId) {
        return streetService.findGridIdByStreetId(streetId);
    }

    /**
     * 根据 id 修改网格名称
     *
     * @param gridNameVO
     * @return
     */
    @PostMapping("/update/grid/name")
    @ApiOperation("根据id修改网格名称")
    public Result updateGridNameById(@RequestBody @Valid UpdateGridNameByIdVO gridNameVO) {
        return gridService.updateGridNameById(gridNameVO);
    }
}

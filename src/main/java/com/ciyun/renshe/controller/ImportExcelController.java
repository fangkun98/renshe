package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.service.ImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 导入 Excel 信息
 *
 * @Date 2019/9/29 15:45
 * @Author Admin
 * @Version 1.0
 */
@CrossOrigin
@Controller
@RequestMapping("/excel/import")
public class ImportExcelController {

    private final ImportExcelService excelService;

    @Autowired
    public ImportExcelController(ImportExcelService excelService) {
        this.excelService = excelService;
    }

    /**
     * 导入人设内部人员信息
     *
     * @param file
     * @return
     */
    @PostMapping("/inner/user")
    public Result importInnerUserInfo(@RequestParam("file") MultipartFile file) {
        excelService.importInnerUserInfo(file);
        return new Result(true, StatusCode.OK, MessageInfo.IMPORT_SUCCESS.getInfo());
    }

    /**
     * 导入管理员
     *
     * @param file
     * @param areaOrStreet 1为区管理员 2为街道管理员
     * @return
     */
    @PostMapping("/admin")
    public Result importAdmin(@RequestParam("file") MultipartFile file, Integer areaOrStreet) {
        excelService.importAdmin(file, areaOrStreet);
        return new Result(true, StatusCode.OK, MessageInfo.IMPORT_SUCCESS.getInfo());
    }

}

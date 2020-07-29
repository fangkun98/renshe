package com.ciyun.renshe.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportExcelService {
    void importInnerUserInfo(MultipartFile file);

    /**
     * 导入管理员
     *
     * @param file
     * @param areaOrStreet
     */
    void importAdmin(MultipartFile file, Integer areaOrStreet);
}

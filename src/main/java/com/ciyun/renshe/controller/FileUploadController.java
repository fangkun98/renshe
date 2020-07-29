package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.IdWorker;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.common.dingding.DDErrResult;
import com.ciyun.renshe.common.dingding.file.MediaUploadUtils;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 *
 * @Date 2020/4/11 11:14
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@Api(tags = "文件上传")
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private IdWorker idWorker;

    @Value("${project.newsUrl}")
    private String newsUrl;

    @PostMapping("/media")
    @ApiOperation("钉钉文件上传")
    //@ApiImplicitParam(name = "file", value = "上传的文件", required = true, dataType = "file", paramType = "form")
    public Result mediaUpload(@ApiParam(value = "上传的文件", required = true)   /*required=true  必须传参*/
                              @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {

        Result result = new Result("上传成功");
        if (file.isEmpty()) {
            return result.setMessage("上传的文件不能为空").setFlag(false).setCode(StatusCode.ERROR);
        }

        // 获取文件后缀
        String originalFilename = file.getOriginalFilename();
        //获取文件的后缀名 .jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        log.info("文件的后缀为：" + suffix);

        /**
         * 是图片还是普通文件
         */
        boolean isFile;

        long size = file.getSize();
        // 判断文件类型，图片大小不能大于 1M
        if (suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals("png") || suffix.equals("gif")) {

            // @lADPDfmVOXEGkJExMw
            // 如果文件大小大于 1M，无法上传
            if (!FileUploadController.checkFileSize(size, 1, "M")) {
                return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("钉钉限制，文件不能大于 1M");
            }
            isFile = false;
        } else {
            if (!FileUploadController.checkFileSize(size, 10, "M")) {
                return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("钉钉限制，文件不能大于 10M");
            }
            isFile = true;
        }



        try {
            OapiMediaUploadResponse response = MediaUploadUtils.uploadImage(file.getOriginalFilename(), file.getInputStream(), isFile);
            if (response.getErrcode().equals(0L)) {
                Map<String, Object> map = this.uploadLocalFile(file, request);
                map.put("response", response);
                return result.setData(map);
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

    @PostMapping("/upload")
    @ApiOperation("普通文件上传")
    public Result<Object> uploadFile(@ApiParam(value = "上传的文件", required = true)
                                     @RequestParam(value = "file") MultipartFile file,
                                     HttpServletRequest request) {
        Result<Object> result = new Result<>("上传成功");
        if (file.isEmpty()) {
            return result.setMessage("上传的文件不能为空").setFlag(false).setCode(StatusCode.ERROR);
        }

        try {
            Map<String, Object> map = this.uploadLocalFile(file, request);
            return result.setData(map);
        } catch (IOException e) {
            e.printStackTrace();
            return result.setCode(StatusCode.ERROR).setFlag(false).setMessage("文件上传失败");
        }
    }

    /**
     * 判断文件大小
     *
     * @param len  文件长度
     * @param size 限制大小
     * @param unit 限制单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    public Map<String, Object> uploadLocalFile(MultipartFile file, HttpServletRequest request) throws IOException {

        // 获取文件后缀
        String originalFilename = file.getOriginalFilename();
        //获取文件的后缀名 .jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = idWorker.nextId() + suffix;

        File uploadFile = new File(fileName);
        //如果文件夹不存在则创建
        /*if (!uploadFile.exists() && !uploadFile.isDirectory()) {
            uploadFile.mkdirs();
        }*/

        file.transferTo(uploadFile);

        //String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        //String returnUrl =
        // 获取上传 URL
        Map<String, Object> map = new HashMap<>(7);
        map.put("fileUrl", "/uploadFile/" + fileName);
        map.put("url", newsUrl + "/uploadFile/" + fileName);
        map.put("fileName", fileName);
        return map;

    }
}
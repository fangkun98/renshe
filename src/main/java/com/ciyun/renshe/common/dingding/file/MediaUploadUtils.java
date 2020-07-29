package com.ciyun.renshe.common.dingding.file;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;

import java.io.InputStream;

/**
 * 钉钉上传文件工具类
 *
 * @Date 2020/4/11 11:22
 * @Author Admin
 * @Version 1.0
 */
public class MediaUploadUtils {

    /**
     * 钉钉上传图片
     *
     * @param fileName    文件名称
     * @param inputStream 文件流
     * @return
     * @throws ApiException
     */
    public static OapiMediaUploadResponse uploadImage(String fileName, InputStream inputStream, Boolean isFile) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
        OapiMediaUploadRequest request = new OapiMediaUploadRequest();
        if (isFile) {
            request.setType("file");
        } else {
            request.setType("image");
        }
        request.setMedia(new FileItem(fileName, inputStream));
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }
}

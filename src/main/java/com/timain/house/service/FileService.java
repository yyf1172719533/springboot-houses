package com.timain.house.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/28 16:36
 */
public interface FileService {

    /**
     * 获取上传头像的路径
     * @param fileList
     * @return
     */
    List<String> getImgPath(List<MultipartFile> fileList);
}

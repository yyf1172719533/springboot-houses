package com.timain.house.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.timain.house.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/28 17:00
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path:}")
    private String filePath;

    /**
     * 获取上传头像的路径
     *
     * @param fileList
     * @return
     */
    @Override
    public List<String> getImgPath(List<MultipartFile> fileList) {
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
        fileList.forEach(file -> {
            //File localFile = null;
            if (!file.isEmpty()){
                try {
                    //localFile = saveToLocal(file, filePath);
                    String url = saveToLocal(file, filePath);
                    String path = StringUtils.substringAfterLast(url, filePath);
                    /*System.out.println(path);*/
                    paths.add(path);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return paths;
    }

    private static String getResourcePath() {
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    private String saveToLocal(MultipartFile file, String filePath) throws IOException {
        String url = filePath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename();
        File newFile = new File(url);
        if (!newFile.exists()){
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(), newFile);
        return url;
    }
}

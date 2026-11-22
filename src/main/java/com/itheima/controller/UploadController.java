package com.itheima.controller;


import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    /**
     * 文件上传到本地磁盘
     */
    /*@PostMapping("/upload")
    public Result updload(String name, Integer age, MultipartFile file) throws IOException {
        log.info("接受的参数为：{},{},{}",name,age,file);

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();

        // 新文件名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extension;
        // 保存文件
        file.transferTo(new File("D:/images/" + newFileName));
        return Result.success();
    }*/


    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    /**
     * 文件上传到阿里云
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file.getOriginalFilename());
        String url = aliyunOSSOperator.upload(file.getBytes(), Objects.requireNonNull(file.getOriginalFilename()));
        log.info("文件上传oss,url:{}",url);
        return Result.success(url);
    }


}

package com.atyomi.boot.controller;

import com.atyomi.boot.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.root-path}")
    String rootPath;
    @RequestMapping("/download")
    public void getResources(String name, HttpServletResponse response) throws Exception {
        //1.读取文件
        FileInputStream fis = new FileInputStream(new File(rootPath+name));
        byte[] bytes = new byte[1024];
        int len = 0;
        //2.获取输出流，把文件相应回浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        while((len = fis.read(bytes))!=-1){
           outputStream.write(bytes,0,len);
        }
        //3.关闭流
        fis.close();
        outputStream.close();
    }
    @RequestMapping("/upload")
    public R<String> download(MultipartFile file) throws IOException {
        //使用UUID给文件设置新名字
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        //获取文件后缀名
        String orgName = file.getOriginalFilename();
        String aft = orgName.substring(orgName.indexOf("."));
        String name = s+aft;
        //将文件转存到指定目录下
        file.transferTo(new File(rootPath+name));
        //返回文件名字
        return R.success(name);
    }
}

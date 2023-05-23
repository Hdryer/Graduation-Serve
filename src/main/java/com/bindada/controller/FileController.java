package com.bindada.controller;

import com.bindada.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
@Api(tags = "文件模块")
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Value("${file.path}")
    private String filePath;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public ApiResponse uploadFile(@ApiParam(value = "上传文件", required = true) @RequestPart(value = "file") MultipartFile file){
        File directoryFile = new File(filePath);
        if (!directoryFile.exists()){
            directoryFile.mkdirs();
        }
        if (file.isEmpty())
            return ApiResponse.fail("文件为空");
        try {
            saveFile(file);
        }catch (IOException e){
            e.printStackTrace();
            return ApiResponse.fail("文件上传失败");
        }
        return ApiResponse.success("文件上传成功");
    }

    //保存文件到指定位置
    public void saveFile(MultipartFile file) throws IOException{
        String filename = file.getOriginalFilename();
        file.transferTo(new File(filePath+filename));
    }

    @GetMapping("/downLoad")
    @ApiOperation(value = "文件下载")
    public void  downLoadFile(@RequestParam(value = "fileName") String fileName, HttpServletResponse response) throws Exception {
        String name = URLDecoder.decode(fileName, "UTF-8");
        log.info("路径:{}{}",filePath,name);
        //获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(new File(filePath,name));

        //文件名包含中文时需要进行中文编码，否则会出现乱码问题
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(name,"utf-8")+"");
        response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        int len = 0;

        //设置一个缓冲区，大小取决于文件内容的大小
        byte[] buffer = new byte[1024];
        //每次读入缓冲区的数据，直到缓冲区无数据
        while((len=fileInputStream.read(buffer))!=-1)
        {
            //输出缓冲区的数据
            servletOutputStream.write(buffer,0,len);
        }
        servletOutputStream.close();
        fileInputStream.close();
    }

}

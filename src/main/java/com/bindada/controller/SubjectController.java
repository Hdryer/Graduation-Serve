package com.bindada.controller;

import com.bindada.common.ApiResponse;
import com.bindada.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "科目模块")
@RequestMapping("/subject")
@Slf4j
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/list")
    @ApiOperation(value = "获取所有科目")
    public ApiResponse getSubjects(){
        return ApiResponse.success("获取成功",subjectService.getSubject());
    }
}

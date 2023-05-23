package com.bindada.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bindada.common.ApiResponse;
import com.bindada.common.BasePageList;
import com.bindada.common.UserThreadLocal;
import com.bindada.dto.SourcesQueryDTO;
import com.bindada.entity.CourseEntity;
import com.bindada.entity.SourcesEntity;
import com.bindada.entity.UserEntity;
import com.bindada.mapper.UserMapper;
import com.bindada.other.StatusSources;
import com.bindada.service.SourcesService;
import com.bindada.service.UserService;
import com.bindada.vo.SourcesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "资源模块")
@RequestMapping("/sources")
@Slf4j
public class SourcesController {

    @Value("${file.path}")
    private String filePath;

    @Autowired
    private SourcesService sourcesService;

    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传资源")
    @Transactional
    public ApiResponse uploadSources(String typeOne, String typeTwo,String title,String type, @RequestPart(value = "file")MultipartFile file){
        File directoryFile = new File(filePath);
        if (!directoryFile.exists()){
            directoryFile.mkdirs();
        }
        if (file.isEmpty())
            return ApiResponse.fail("文件为空");
        try {
            String filename = file.getOriginalFilename();
            SourcesEntity sourcesEntity = new SourcesEntity();
            sourcesEntity.setTypeOne(typeOne);
            sourcesEntity.setTypeTwo(typeTwo);
            sourcesEntity.setTitle(title);
            sourcesEntity.setType(type);
            sourcesEntity.setFileName(filename);
            sourcesEntity.setStatus(StatusSources.CHECK.value);
            sourcesEntity.setCreatorId(UserThreadLocal.get().getId());  //设置创建人
            sourcesService.save(sourcesEntity);

            file.transferTo(new File(filePath+filename));     //保存文件
            return ApiResponse.success("资源上传成功");
        }catch (IOException e){
            e.printStackTrace();
            return ApiResponse.fail("资源上传失败");
        }
    }

    @ApiOperation("删除资源")
    @DeleteMapping("/del")
    @Transactional
    public ApiResponse delSources(String id){
        try {
            sourcesService.remove(new QueryWrapper<SourcesEntity>().lambda().eq(SourcesEntity::getId,id));
            return ApiResponse.success("删除成功");
        } catch (Exception e) {
            return ApiResponse.fail("网络波动，请重试");
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取所有教学资源(已审核)")
    public ApiResponse getListSources(){
        return ApiResponse.success("获取成功",sourcesService.ListSources());
    }

    @PostMapping("/queryAll")
    @ApiOperation(value = "获取所有教学资源")
    public ApiResponse queryAllSources(@RequestBody SourcesQueryDTO dto){
        BasePageList<SourcesEntity> pageList = sourcesService.queryAllSources(dto);
        List<SourcesEntity> records = pageList.getRecords();
        BasePageList<SourcesVO> pageList1 = new BasePageList<>();
        List<SourcesVO> records1 = new ArrayList<>();
        for (SourcesEntity record : records) {
            SourcesVO sourcesVO = new SourcesVO();
            UserEntity userEntity = userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, record.getCreatorId()));
            if (userEntity!=null)
                sourcesVO.setUserName(userEntity.getUserName());
            BeanUtils.copyProperties(record,sourcesVO);
            records1.add(sourcesVO);
        }
        BeanUtils.copyProperties(pageList,pageList1,"records");
        pageList1.setRecords(records1);
        return ApiResponse.success("获取成功",pageList1);
    }

    @GetMapping("/queryChecking")
    @ApiOperation(value = "获取所有教学资源(审核中)")
    public ApiResponse queryChecking(){
        return ApiResponse.success("获取成功",sourcesService.queryChecking());
    }

    @GetMapping("/check")
    @ApiOperation(value = "审核资源")
    @Transactional
    public ApiResponse check(String id){
        try {
            sourcesService.update(new UpdateWrapper<SourcesEntity>().lambda().eq(SourcesEntity::getId,id).set(SourcesEntity::getStatus,StatusSources.CHECKED.value));
            return ApiResponse.success("审核通过");
        } catch (Exception e) {
            return ApiResponse.fail("网络波动，请重试");
        }
    }

    @GetMapping("/dismiss")
    @ApiOperation(value = "驳回资源")
    @Transactional
    public ApiResponse Uncheck(String id){
        try {
            sourcesService.update(new UpdateWrapper<SourcesEntity>().lambda().eq(SourcesEntity::getId,id).set(SourcesEntity::getStatus,StatusSources.UNCHECKED.value));
            return ApiResponse.success("驳回成功");
        } catch (Exception e) {
            return ApiResponse.fail("网络波动，请重试");
        }
    }

}

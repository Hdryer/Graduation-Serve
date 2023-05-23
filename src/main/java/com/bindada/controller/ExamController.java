package com.bindada.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bindada.common.ApiResponse;
import com.bindada.common.BasePageList;
import com.bindada.common.UserThreadLocal;
import com.bindada.dto.*;
import com.bindada.entity.CourseEntity;
import com.bindada.entity.PaperEntity;
import com.bindada.entity.QuestionEntity;
import com.bindada.service.CourseService;
import com.bindada.service.PaperService;
import com.bindada.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Api(tags = "考核模块")
@RequestMapping("/exam")
@Slf4j
public class ExamController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/addQuestion")
    @ApiOperation("添加试题")
    @Transactional
    public ApiResponse createQuestion(@RequestBody QuestionDTO questionDTO){
        if (questionDTO.getId()==""){
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(questionDTO,questionEntity);
            questionEntity.setCreatorId(UserThreadLocal.get().getId());
            questionEntity.setCreatorName(UserThreadLocal.get().getUserName()==null?"":UserThreadLocal.get().getUserName());
            questionService.save(questionEntity);
            return ApiResponse.success("创建成功");
        }else {
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(questionDTO,questionEntity,"id");
            questionEntity.setModifierId(UserThreadLocal.get().getId());
            questionService.update(questionEntity,new UpdateWrapper<QuestionEntity>().lambda().eq(QuestionEntity::getId,questionDTO.getId()));
            return ApiResponse.success("编辑成功");
        }
    }

    @PostMapping("/queryQuestion")
    @ApiOperation("查找试题")
    public ApiResponse queryQuestion(@RequestBody QuestionQueryDTO dto){
        BasePageList<QuestionEntity> pageList = questionService.queryQuestion(dto);
        return ApiResponse.success("查询成功",pageList);
    }

    @GetMapping("/query")
    @ApiOperation("查看试题")
    public ApiResponse query(String id){
        QuestionEntity entity = questionService.getOne(new QueryWrapper<QuestionEntity>().lambda().eq(QuestionEntity::getId, id));
        return ApiResponse.success("查询成功",entity);
    }
    @DeleteMapping("/del")
    @ApiOperation("删除试题")
    @Transactional
    public ApiResponse delQuestion(String id){
        try {
            questionService.remove(new QueryWrapper<QuestionEntity>().lambda().eq(QuestionEntity::getId, id));
            return ApiResponse.success("删除成功");
        } catch (Exception e) {
            return ApiResponse.fail("网络波动请重试");
        }
    }

    @PostMapping("/addPaper")
    @ApiOperation("创编试卷")
    @Transactional
    public ApiResponse addPaper(@RequestBody PaperDTO dto){
        if (dto.getId()==""){
            PaperEntity paperEntity = new PaperEntity();
            BeanUtils.copyProperties(dto,paperEntity,"id","questions");
            CourseEntity courseEntity = courseService.getOne(new QueryWrapper<CourseEntity>().lambda().eq(CourseEntity::getId, dto.getCourseId()));
            paperEntity.setCourseName(courseEntity.getName());
            paperEntity.setCreatorId(UserThreadLocal.get().getId());
            paperEntity.setCreatorName(UserThreadLocal.get().getUserName());
            paperEntity.setNum(dto.getQuestions().size());
            paperEntity.setQuestions(JSON.toJSONString(dto.getQuestions()));

            paperService.save(paperEntity);
            return ApiResponse.success("创建试卷成功");
        }else {
            PaperEntity paperEntity = new PaperEntity();
            BeanUtils.copyProperties(dto,paperEntity,"id","questions");
            CourseEntity courseEntity = courseService.getOne(new QueryWrapper<CourseEntity>().lambda().eq(CourseEntity::getId, dto.getCourseId()));
            paperEntity.setCourseName(courseEntity.getName());
            paperEntity.setModifierId(UserThreadLocal.get().getId());
            paperEntity.setNum(dto.getQuestions().size());
            paperEntity.setQuestions(JSON.toJSONString(dto.getQuestions()));

            paperService.update(paperEntity,new UpdateWrapper<PaperEntity>().lambda().eq(PaperEntity::getId,dto.getId()));
            return ApiResponse.success("编辑试卷成功");
        }
    }


    @PostMapping("/queryPaper")
    @ApiOperation("查询所有试卷")
    public ApiResponse queryAllPaper(@RequestBody PaperQueryDTO dto){
        BasePageList<PaperEntity> pageList = paperService.queryAllPaper(dto);
        return ApiResponse.success("查询成功",pageList);
    }

    @GetMapping("/queryOnePaper")
    @ApiOperation("查看试卷")
    public ApiResponse queryOnePaper(String id){
        PaperEntity paperEntity = paperService.getOne(new QueryWrapper<PaperEntity>().lambda().eq(PaperEntity::getId, id));
        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<QuestionEntity> questions = new ArrayList<>();
        List list = JSON.parseObject(paperEntity.getQuestions(), List.class);
        for (int i = 0; i < list.size(); i++) {
            QuestionEntity questionEntity = questionService.getOne(new QueryWrapper<QuestionEntity>().lambda().eq(QuestionEntity::getId, list.get(i)));
            questions.add(questionEntity);
        }
        hashMap.put("paperEntity",paperEntity);
        hashMap.put("questions",questions);
        return ApiResponse.success("获取成功",hashMap);
    }

    @DeleteMapping("/delPaper")
    @ApiOperation("删除试卷")
    @Transactional
    public ApiResponse delPaper(String id){
        paperService.remove(new QueryWrapper<PaperEntity>().lambda().eq(PaperEntity::getId, id));
        return ApiResponse.success("删除成功");
    }

    @PostMapping("/submit")
    @ApiOperation("提交试卷")
    public ApiResponse submitPaper(@RequestBody  PaperSubmitDTO dto){
        PaperEntity paperEntity = paperService.getOne(new QueryWrapper<PaperEntity>().lambda().eq(PaperEntity::getId, dto.getId()));

        List<AnswerDTO> answerList = dto.getAnswerList();
        double count = 0;
        for (AnswerDTO answerDTO : answerList) {
            QuestionEntity questionEntity = questionService.getOne(new QueryWrapper<QuestionEntity>().lambda().eq(QuestionEntity::getId, answerDTO.getId()));
            if (questionEntity.getAnswer().equals(answerDTO.getAnswer())){
                count++;
            }
        }
        double realScore = (count/(double) paperEntity.getNum())*paperEntity.getScore();
        return ApiResponse.success("提交成功",realScore);
    }
}

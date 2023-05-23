package com.bindada.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bindada.common.ApiResponse;
import com.bindada.common.BasePageList;
import com.bindada.dto.CourseDTO;
import com.bindada.dto.CourseQueryDTO;
import com.bindada.dto.OutlineDTO;
import com.bindada.entity.CourseEntity;
import com.bindada.entity.OutlineEntity;
import com.bindada.entity.PaperEntity;
import com.bindada.mapper.UserMapper;
import com.bindada.service.CourseService;
import com.bindada.service.OutlineService;
import com.bindada.service.PaperService;
import com.bindada.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@Api(tags = "课程模块")
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Value("${file.path}")
    private String filePath;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private OutlineService outlineService;

    @Autowired
    private PaperService paperService;

    @ApiOperation("查询课程试卷")
    @GetMapping("/queryCoursePaper")
    public ApiResponse queryCoursePaper(String id){
        PaperEntity paperEntity = paperService.getOne(new QueryWrapper<PaperEntity>().lambda().eq(PaperEntity::getCourseId, id));
        if (paperEntity!=null)
            return ApiResponse.success("试卷ID",paperEntity.getId());
        else
            return ApiResponse.success("试卷不存在",null);
    }

    @ApiOperation("查询课程")
    @GetMapping("/query")
    public ApiResponse queryCourse(String id){
        CourseEntity courseEntity = courseService.getOne(new QueryWrapper<CourseEntity>().lambda().eq(CourseEntity::getId, id));
        return ApiResponse.success("查询成功",courseEntity);
    }

    @ApiOperation("查询所有课程")
    @PostMapping("/queryAll")
    public ApiResponse queryAllCourse(@RequestBody CourseQueryDTO dto){
        BasePageList<CourseEntity> pageList = courseService.queryAllCourse(dto);
        return ApiResponse.success("获取成功",pageList);
    }

    @ApiOperation("新建课程")
    @PostMapping("/add")
    public ApiResponse newCourse(@RequestBody CourseDTO courseDTO){
        try {
            courseService.newCourse(courseDTO);
            return ApiResponse.success("新建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail("网络波动，新建失败");
        }
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/del")
    @Transactional
    public ApiResponse delCourse(String id){
        try {
            courseService.remove(new QueryWrapper<CourseEntity>().lambda().eq(CourseEntity::getId,id));
            return ApiResponse.success("删除成功");
        } catch (Exception e) {
            return ApiResponse.fail("删除失败，请重试");
        }
    }

    @PutMapping("/publishCourse")
    @Transactional
    @ApiOperation(value = "上线课程")
    public ApiResponse publishCourse(String id){
        courseService.update(new UpdateWrapper<CourseEntity>().lambda().eq(CourseEntity::getId,id).set(CourseEntity::getIsDeleted,1));
        return ApiResponse.success("课程已上线");
    }

    @PutMapping("/unPublishCourse")
    @Transactional
    @ApiOperation(value = "下架课程")
    public ApiResponse unPublishCourse(String id){
        courseService.update(new UpdateWrapper<CourseEntity>().lambda().eq(CourseEntity::getId,id).set(CourseEntity::getIsDeleted,0));
        return ApiResponse.success("课程已下架");
    }

    @ApiOperation("获取授课老师列表")
    @GetMapping("/teacher")
    public ApiResponse getTeacher(){
        return ApiResponse.success("获取成功",userMapper.getTeacher());
    }

    @ApiOperation("编辑大纲")
    @PutMapping("/updateOutlin")
    public ApiResponse updateOutlin(@RequestBody  OutlineDTO outlineDTO){
        try {
            outlineService.updateOutline(outlineDTO);
            return ApiResponse.success("编辑成功");
        } catch (Exception e) {
            return ApiResponse.fail("网络波动请重试");
        }
    }
    @ApiOperation("查找课程大纲")
    @GetMapping("/queryOutlin")
    public ApiResponse queryOutlin(String id){
        OutlineEntity outlineEntity = outlineService.getOne(new QueryWrapper<OutlineEntity>().lambda().eq(OutlineEntity::getCourseId, id));
        return ApiResponse.success("查询成功",outlineEntity!= null ? outlineEntity:null);
    }

    @ApiOperation("初版单个MP4播放")
    @GetMapping(value = "/video/play1" ,produces ="application/json;charset=utf-8")
    @CrossOrigin
    public void aloneVideoPlay(HttpServletRequest request,@RequestParam(value = "fileName")String fileName, HttpServletResponse response) {
        InputStream is = null;
        OutputStream os = null;
        try {
            response.setContentType("video/mp4");
            File file = new File("E:\\Graduation\\data\\" + fileName);
            response.addHeader("Content-Length", "" + file.length());
            is = new FileInputStream(file);
            os = response.getOutputStream();
            IOUtils.copy(is, os);
        } catch (Exception e) {
            log.error("播放MP4失败", e);
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation("单个MP4播放")
    @GetMapping(value = "/video/play" ,produces ="application/json;charset=utf-8")
    @CrossOrigin
    public void play(@RequestParam(value = "fileName")String fileName, HttpServletRequest request, HttpServletResponse response) {
        String path = filePath+fileName;
        log.info("path:{}",path);
        RandomAccessFile targetFile = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.reset();
            //获取请求头中Range的值
            String rangeString = request.getHeader(HttpHeaders.RANGE);

            //打开文件
            File file = new File(path);
            if (file.exists()) {
                //使用RandomAccessFile读取文件
                targetFile = new RandomAccessFile(file, "r");
                long fileLength = targetFile.length();
                long requestSize = (int) fileLength;
                //分段下载视频
                if (StringUtils.hasText(rangeString)) {
                    //从Range中提取需要获取数据的开始和结束位置
                    long requestStart = 0, requestEnd = 0;
                    String[] ranges = rangeString.split("=");
                    if (ranges.length > 1) {
                        String[] rangeDatas = ranges[1].split("-");
                        requestStart = Integer.parseInt(rangeDatas[0]);
                        if (rangeDatas.length > 1) {
                            requestEnd = Integer.parseInt(rangeDatas[1]);
                        }
                    }
                    if (requestEnd != 0 && requestEnd > requestStart) {
                        requestSize = requestEnd - requestStart + 1;
                    }
                    //根据协议设置请求头
                    response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
                    response.setHeader(HttpHeaders.CONTENT_TYPE, "video/mp4");
                    if (!StringUtils.hasText(rangeString)) {
                        response.setHeader(HttpHeaders.CONTENT_LENGTH, fileLength + "");
                    } else {
                        long length;
                        if (requestEnd > 0) {
                            length = requestEnd - requestStart + 1;
                            response.setHeader(HttpHeaders.CONTENT_LENGTH, "" + length);
                            response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + requestStart + "-" + requestEnd + "/" + fileLength);
                        } else {
                            length = fileLength - requestStart;
                            response.setHeader(HttpHeaders.CONTENT_LENGTH, "" + length);
                            response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + requestStart + "-" + (fileLength - 1) + "/"
                                    + fileLength);
                        }
                    }
                    //断点传输下载视频返回206
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    //设置targetFile，从自定义位置开始读取数据
                    targetFile.seek(requestStart);
                } else {
                    //如果Range为空则下载整个视频
                    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getName());
                    //设置文件长度
                    response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength));
                }

                //从磁盘读取数据流返回
                byte[] cache = new byte[4096];
                try {
                    while (requestSize > 0) {
                        int len = targetFile.read(cache);
                        if (requestSize < cache.length) {
                            outputStream.write(cache, 0, (int) requestSize);
                        } else {
                            outputStream.write(cache, 0, len);
                            if (len < cache.length) {
                                break;
                            }
                        }
                        requestSize -= cache.length;
                    }
                } catch (IOException e) {
                    // tomcat原话。写操作IO异常几乎总是由于客户端主动关闭连接导致，所以直接吃掉异常打日志
                    //比如使用video播放视频时经常会发送Range为0- 的范围只是为了获取视频大小，之后就中断连接了
                    log.info(e.getMessage());
                }
            } else {
                throw new RuntimeException("文件路劲有误");
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("文件传输错误", e);
            throw new RuntimeException("文件传输错误");
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("流释放错误", e);
                }
            }
            if(targetFile != null){
                try {
                    targetFile.close();
                } catch (IOException e) {
                    log.error("文件流释放错误", e);
                }
            }
        }
    }

}

package com.yufei.eduservice.controller;


import com.yufei.commonutils.R;
import com.yufei.eduservice.entity.subject.OneSubject;
import com.yufei.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-14
 */
@Api(tags = "课程分类")
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传文件，把文件内容得到
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return  R.ok();
    }
    @ApiOperation(value = "获取课程列表")
    @GetMapping("list")
    public R getAllSubject(){
        //list集合是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("items",list);
    }
}


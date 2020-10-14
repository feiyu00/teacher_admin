package com.yufei.eduservice.controller;


import com.yufei.commonutils.R;
import com.yufei.eduservice.entity.EduTeacher;
import com.yufei.eduservice.entity.vo.TeacherQuery;
import com.yufei.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-09
 * @CrossOrigin 解决跨域问题
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")

public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
      /*
      try {
            int i = 10 / 0;
        } catch (Exception e) {
            throw new GuiLiException(20001,"执行自定义异常处理");
       }
      */
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    //逻辑删除讲师
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    /*
     * 3 分页查询讲师的方法
     * current 当前页
     * limit 每页显示的记录数
     *
     * */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current", value = "当前页码", required = true)
                             @PathVariable Long current,
                             @ApiParam(name = "limit", value = "每页记录数", required = true)
                             @PathVariable Long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        //调用方法实现分页
        eduTeacherService.page(page, null);
        //总记录数
        long total = page.getTotal();
        // 获得List集合
        List<EduTeacher> records = page.getRecords();
        Map map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "条件分页查询讲师列表")
    //使用@RequestBody需要使用post提交
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "当前页码", required = true)
                                  @PathVariable long current,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true)
                                  @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建一个page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String end = teacherQuery.getEnd();
        String begin = teacherQuery.getBegin();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            //大于等于
            wrapper.ge("gmt_create", begin);

        }
        if (!StringUtils.isEmpty(end)) {
            //小于等于
            wrapper.le("gmt_modified", end);
        }
        //排序
        wrapper.orderByDesc("sort");
        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据Id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}


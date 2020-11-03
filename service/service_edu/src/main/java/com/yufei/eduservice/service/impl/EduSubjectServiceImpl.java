package com.yufei.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yufei.eduservice.entity.EduSubject;
import com.yufei.eduservice.entity.excel.SubjectData;
import com.yufei.eduservice.entity.subject.OneSubject;
import com.yufei.eduservice.entity.subject.TwoSubject;
import com.yufei.eduservice.listener.SubjectExcelListener;
import com.yufei.eduservice.mapper.EduSubjectMapper;
import com.yufei.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService service) {
        try {
            //文件输入流

            InputStream in = file.getInputStream();
            //SubjectListener类不能交给springboot管理，但是EduSubjectServiceImpl使用了@Service交给了springboot管理
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(service)).sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     *
     * 因为是树形结构
     *
     * */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1.查询出所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);//        this.getMap();
        //2.查询出所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //创建list集合，用于存储最终封装
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //3.封装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            //把eduSubject获取出来放在OneSubject对象里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //第一个参数为get对象，第二个为set对象
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);
            //4.封装二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject tSubject = twoSubjectList.get(j);
                //判断二级分类parent_id是否和一级分类id一样
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级分类下面所有二级分类放在一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);

        }


        return finalSubjectList;
    }
}

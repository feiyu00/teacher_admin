package com.yufei.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yufei.eduservice.entity.EduSubject;
import com.yufei.eduservice.entity.excel.SubjectData;
import com.yufei.eduservice.listener.SubjectExcelListener;
import com.yufei.eduservice.mapper.EduSubjectMapper;
import com.yufei.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
    public void saveSubject(MultipartFile file,EduSubjectService service) {
        try {
            //文件输入流

            InputStream in = file.getInputStream();
            //SubjectListener类不能交给springboot管理，但是EduSubjectServiceImpl使用了@Service交给了springboot管理
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(service)).sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

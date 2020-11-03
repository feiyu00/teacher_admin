package com.yufei.eduservice.service;

import com.yufei.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yufei.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    void addChapter(EduChapter eduChapter);

    EduChapter getChapterInfo(String chapterId);

    void updateChapter(EduChapter eduChapter);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String id);
}

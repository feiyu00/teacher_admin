package com.yufei.eduservice.service;

import com.yufei.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
public interface EduVideoService extends IService<EduVideo> {

    void addVideo(EduVideo video);

    EduVideo getVideo(String id);

    boolean updateVideo(EduVideo video);

    void deleteVideo(String id);

    boolean removeVideoByCourseId(String id);
}

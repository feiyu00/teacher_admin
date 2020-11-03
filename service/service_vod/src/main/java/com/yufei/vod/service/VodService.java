package com.yufei.vod.service;

import com.yufei.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VodService {
    String uploadAliVideo(MultipartFile file) throws IOException;

    R removeAlyVideo(String id);

    void removeMoreAlyVideo(List videoIdList);
}

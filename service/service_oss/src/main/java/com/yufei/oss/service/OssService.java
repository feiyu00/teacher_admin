package com.yufei.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadOssFileAvatar(MultipartFile file);

}

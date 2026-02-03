// src/main/java/com/delas/api/service/UploadService.java
package com.delas.api.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UploadService {

  private final Cloudinary cloudinary;

  public UploadService(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  public Map uploadProfile(MultipartFile file, Long userId) throws Exception {
    return cloudinary.uploader().upload(
        file.getBytes(),
        ObjectUtils.asMap(
            "folder", "maisdelas/perfis",
            "public_id", "user_" + userId,     // sobrescreve sempre
            "overwrite", true,
            "resource_type", "image"
        )
    );
  }

  public Map uploadServicePhoto(MultipartFile file, Long serviceId, int index) throws Exception {
    return cloudinary.uploader().upload(
        file.getBytes(),
        ObjectUtils.asMap(
            "folder", "maisdelas/servicos",
            "public_id", "service_" + serviceId + "_" + index, // 0..2
            "overwrite", true,
            "resource_type", "image"
        )
    );
  }
}

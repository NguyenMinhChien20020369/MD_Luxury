package com.java.md_luxury_2.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageStorageService {

    private static final long MAX_SIZE = 5 * 1024 * 1024; // Giới hạn 5MB

    @Autowired
    private Cloudinary cloudinary;

    public String upload(MultipartFile file) {
        // 1. Kiểm tra tính hợp lệ của file
        validate(file);

        try {
            // Upload file lên Cloudinary với cấu hình tự động nhận diện loại file (resource_type = auto)
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto")
            );

            // Trả về đường dẫn URL trực tiếp của ảnh để bạn lưu vào Database
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Lỗi xảy ra trong quá trình upload file lên Cloudinary: " + e.getMessage());
        }
    }

    private void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File tải lên không được để trống");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("Dung lượng ảnh vượt quá giới hạn tối đa (5MB)");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Chỉ chấp nhận các định dạng file ảnh (PNG, JPG, JPEG,...)");
        }
    }
}
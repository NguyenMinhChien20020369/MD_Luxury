package com.java.md_luxury_2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final S3Client s3Client;
    private static final long MAX_SIZE = 5 * 1024 * 1024; // Giới hạn 5MB
    private static final String BUCKET = "mdluxury-content-images";

    public ImageStorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String upload(MultipartFile file) {
        // 1. Kiểm tra tính hợp lệ của file
        validate(file);

        // 2. Tạo key/filename duy nhất
        String key = "products/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        // 3. Đẩy file lên S3
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            throw new RuntimeException("Tải ảnh lên S3 thất bại", e);
        }

        // 4. Trả về URL đầy đủ của ảnh
        return "https://cdn.mdluxury.vn/" + key;
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
package com.java.md_luxury_2.controller.cms;

import com.java.md_luxury_2.service.ImageStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cms/products")
public class ImageCmsController {

    private final ImageStorageService imageStorageService;

    public ImageCmsController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping("/images/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = imageStorageService.upload(file);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "url", imageUrl
        ));
    }
}
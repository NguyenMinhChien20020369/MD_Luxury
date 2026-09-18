package com.java.md_luxury_2.entity;

import com.java.md_luxury_2.security.EncryptedStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer", schema = "crm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    // Số điện thoại dạng mã hoá để hiển thị (bài 19 sẽ thêm @Convert)
    @Column(name = "phone_encrypted", nullable = false, length = 255)
    // Tự động mã hoá khi lưu xuống DB, giải mã khi đọc lên Entity
    @Convert(converter = EncryptedStringConverter.class)
    private String phoneEncrypted;

    // Chuỗi HMAC-SHA256 của số điện thoại dùng để tìm kiếm chính xác
    @Column(name = "phone_hash", nullable = false, length = 64, unique = true)
    private String phoneHash;

    @Column(name = "email", length = 150)
    private String email;

    // ID của nhân viên phụ trách (liên kết tới app_user.id)
    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "tier", nullable = false, length = 20)
    private String tier = "Member";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.tier == null) {
            this.tier = "Member";
        }
    }
}
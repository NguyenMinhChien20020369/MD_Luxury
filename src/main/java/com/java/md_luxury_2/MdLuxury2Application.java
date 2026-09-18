package com.java.md_luxury_2;

import com.java.md_luxury_2.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.java.md_luxury_2.repository")
@EnableScheduling
public class MdLuxury2Application {

    public static void main(String[] args) {
        SpringApplication.run(MdLuxury2Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.findByEmail("admin@mdluxury.vn").ifPresent(user -> {
                // Tự băm và lưu lại trực tiếp bằng đúng Encoder của ứng dụng
                user.setPasswordHash(passwordEncoder.encode("123456"));
                userRepository.save(user);
                System.out.println("====== DA CAP NHAT MAT KHAU ADMIN THANH CONG ======");
            });
        };
    }
}

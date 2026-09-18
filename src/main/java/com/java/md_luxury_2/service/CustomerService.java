package com.java.md_luxury_2.service;

import com.java.md_luxury_2.entity.Customer;
import com.java.md_luxury_2.repository.CustomerRepository;
import com.java.md_luxury_2.repository.CustomerSpecifications;
import com.java.md_luxury_2.security.CurrentUser;
import com.java.md_luxury_2.util.PhoneHashUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> list(CurrentUser currentUser, String search, Pageable pageable) {
        // Bắt đầu với Specification rỗng
        Specification<Customer> spec = Specification.allOf();

        // 1. Phân quyền: Nếu là SALE, chỉ tự động lọc các bản ghi do chính SALE đó phụ trách
        if ("SALE".equals(currentUser.role())) {
            spec = spec.and(CustomerSpecifications.ownedBy(currentUser.userId()));
        }

        // 2. Tìm kiếm: Thêm điều kiện search theo tên nếu người dùng truyền keyword
        if (search != null && !search.trim().isEmpty()) {
            spec = spec.and(CustomerSpecifications.hasNameLike(search));
        }

        return customerRepository.findAll(spec, pageable);
    }

    public Customer getById(CurrentUser currentUser, UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng"));

        // Kiểm tra quyền: Nếu là SALE và không phải owner của khách hàng này -> Chặn 403
        if ("SALE".equals(currentUser.role()) && !customer.getOwnerId().equals(currentUser.userId())) {
            throw new AccessDeniedException("Bạn không có quyền xem thông tin khách hàng này");
        }

        return customer;
    }

    // Tìm kiếm chính xác khách hàng theo số điện thoại thô truyền vào
    public Customer findByPhone(String rawPhone) {
        String normalized = PhoneHashUtil.normalize(rawPhone);
        String hash = PhoneHashUtil.hash(normalized);
        return customerRepository.findByPhoneHash(hash)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng với số điện thoại này"));
    }

    // Xử lý tạo mới khách hàng có kiểm tra trùng số điện thoại theo bài tập yêu cầu
    @Transactional
    public Customer createCustomer(String fullName, String rawPhone, String email) {
        String normalizedPhone = PhoneHashUtil.normalize(rawPhone);
        String phoneHash = PhoneHashUtil.hash(normalizedPhone);

        // Kiểm tra nếu đã tồn tại phoneHash thì cảnh báo trùng
        if (customerRepository.existsByPhoneHash(phoneHash)) {
            throw new IllegalStateException("Số điện thoại này đã tồn tại trên hệ thống!");
        }

        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setPhoneEncrypted(normalizedPhone); // Sẽ tự động qua EncryptedStringConverter để mã hoá
        customer.setPhoneHash(phoneHash);
        customer.setEmail(email);

        return customerRepository.save(customer);
    }
}
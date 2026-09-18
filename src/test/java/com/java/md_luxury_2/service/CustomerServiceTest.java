package com.java.md_luxury_2.service;

import com.java.md_luxury_2.entity.Customer;
import com.java.md_luxury_2.repository.CustomerRepository;
import com.java.md_luxury_2.security.CurrentUser;
import com.java.md_luxury_2.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void nhanVienKhongDuocXemKhachHangCuaNguoiKhac() {
        UUID ownerA = UUID.randomUUID();
        UUID ownerB = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setOwnerId(ownerA); // Khách hàng thuộc nhân viên A

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CurrentUser nhanVienB = new CurrentUser(ownerB, "SALE"); // Nhân viên B gọi API

        // Khẳng định truy cập bị từ chối
        assertThrows(AccessDeniedException.class,
                () -> customerService.getById(nhanVienB, customerId));
    }

    @Test
    void nhanVienDuocXemKhachHangCuaChinhMinh() {
        UUID ownerA = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setOwnerId(ownerA);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CurrentUser nhanVienA = new CurrentUser(ownerA, "SALE");

        assertDoesNotThrow(() -> customerService.getById(nhanVienA, customerId));
    }
}
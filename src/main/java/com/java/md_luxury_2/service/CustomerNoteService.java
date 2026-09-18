package com.java.md_luxury_2.service;

import com.java.md_luxury_2.entity.Customer;
import com.java.md_luxury_2.entity.CustomerNote;
import com.java.md_luxury_2.repository.CustomerNoteRepository;
import com.java.md_luxury_2.security.CurrentUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomerNoteService {

    private final CustomerNoteRepository noteRepository;
    private final CustomerService customerService;

    public CustomerNoteService(CustomerNoteRepository noteRepository, CustomerService customerService) {
        this.noteRepository = noteRepository;
        this.customerService = customerService;
    }

    public void add(CurrentUser currentUser, UUID customerId, String content) {
        // Tái sử dụng getById từ CustomerService (đã có logic chặn Sale xem/thêm note của khách do người khác phụ trách)
        Customer customer = customerService.getById(currentUser, customerId);

        CustomerNote note = new CustomerNote();
        note.setCustomerId(customer.getId());
        note.setCreatedBy(currentUser.userId());
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());

        noteRepository.save(note);
    }
}
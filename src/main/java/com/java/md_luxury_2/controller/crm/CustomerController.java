package com.java.md_luxury_2.controller.crm;

import com.java.md_luxury_2.dto.CustomerDTO;
import com.java.md_luxury_2.entity.Customer;
import com.java.md_luxury_2.security.CurrentUser;
import com.java.md_luxury_2.security.CurrentUserProvider;
import com.java.md_luxury_2.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/crm/customers")
@PreAuthorize("hasAnyRole('SALE', 'MANAGER', 'ADMIN')")
public class CustomerController {

    private final CustomerService customerService;
    private final CurrentUserProvider currentUserProvider;

    public CustomerController(CustomerService customerService, CurrentUserProvider currentUserProvider) {
        this.customerService = customerService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> listCustomers(
            @RequestParam(value = "search", required = false) String search,
            @AuthenticationPrincipal CurrentUser currentUser,
            Pageable pageable) {

        Page<Customer> customers = customerService.list(currentUser, search, pageable);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable UUID id) {
        CurrentUser currentUser = currentUserProvider.get();
        Customer customer = customerService.getById(currentUser, id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CustomerDTO customerDTO) {
//        CurrentUser currentUser = currentUserProvider.get();
        Customer customer = customerService.createCustomer(customerDTO.fullName(), customerDTO.phoneEncrypted(), customerDTO.email());
        return ResponseEntity.ok(customer);
    }
}
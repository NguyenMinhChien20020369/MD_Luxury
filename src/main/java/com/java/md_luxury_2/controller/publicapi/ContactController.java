package com.java.md_luxury_2.controller.publicapi;

import com.java.md_luxury_2.dto.ContactRequest;
import com.java.md_luxury_2.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/contact")
public class ContactController {

    private final LeadService leadService;

    public ContactController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<Void> submit(@Valid @RequestBody ContactRequest request) {
        leadService.receiveFromWebsite(request);
        return ResponseEntity.accepted().build();
    }
}
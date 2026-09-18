package com.java.md_luxury_2.controller.crm;

import com.java.md_luxury_2.dto.ConvertLeadRequest;
import com.java.md_luxury_2.dto.CustomerDTO;
import com.java.md_luxury_2.security.CurrentUser;
import com.java.md_luxury_2.service.LeadConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/crm/leads")
@PreAuthorize("hasAnyRole('SALE', 'MANAGER', 'ADMIN')")
@Tag(name = "Lead Management", description = "Quản lý Lead")
public class LeadCrmController {

    private final LeadConversionService leadConversionService;

    public LeadCrmController(LeadConversionService leadConversionService) {
        this.leadConversionService = leadConversionService;
    }

    @PostMapping("/{id}/convert")
    @Operation(summary = "Chuyển đổi Lead sang Customer", description = "Chuyển đổi Lead sang Customer")
    public ResponseEntity<CustomerDTO> convertLead(
            @PathVariable("id") UUID leadId,
            @Valid @RequestBody ConvertLeadRequest request,
            @AuthenticationPrincipal CurrentUser currentUser) {

        CustomerDTO createdCustomer = leadConversionService.convert(
                leadId,
                request.assignToUserId(),
                currentUser.userId()
        );

        return ResponseEntity.ok(createdCustomer);
    }
}
package com.java.md_luxury_2.service;

import com.java.md_luxury_2.dto.CustomerDTO;
import com.java.md_luxury_2.entity.Customer;
import com.java.md_luxury_2.entity.Lead;
import com.java.md_luxury_2.entity.LeadStatus;
import com.java.md_luxury_2.repository.CustomerRepository;
import com.java.md_luxury_2.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class LeadConversionService {

    private final LeadRepository leadRepository;
    private final CustomerRepository customerRepository;
    private final AuditLogService auditLogService;

    public LeadConversionService(LeadRepository leadRepository,
                                 CustomerRepository customerRepository,
                                 AuditLogService auditLogService) {
        this.leadRepository = leadRepository;
        this.customerRepository = customerRepository;
        this.auditLogService = auditLogService;
    }

    @Transactional // Đảm bảo cả hai bước đổi trạng thái Lead và tạo Customer đều thành công hoặc thất bại cùng nhau
    public CustomerDTO convert(UUID leadId, UUID assignToUserId, UUID actorId) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lead"));

        if (lead.getStatus() == LeadStatus.CONVERTED) {
            throw new IllegalStateException("Lead này đã được chuyển đổi trước đó");
        }

        Customer customer = new Customer();
        customer.setFullName(lead.getFullName());
        customer.setOwnerId(assignToUserId);
        customer.setTier("Member");
        customer.setCreatedAt(LocalDateTime.now());
        // Tạm thời lưu thô SĐT, bài 19 sẽ xử lý mã hóa phoneEncrypted và tính phoneHash
        customer.setPhoneEncrypted(lead.getPhone());

        Customer saved = customerRepository.save(customer);

        lead.setStatus(LeadStatus.CONVERTED);
        leadRepository.save(lead);

        auditLogService.log(actorId, "CONVERT_LEAD", "customer", saved.getId(),
                Map.of("fromLeadId", leadId.toString()));

        return toDTO(saved);
    }

    private CustomerDTO toDTO(Customer c) {
        return new CustomerDTO(
                c.getId(),
                c.getFullName(),
                c.getPhoneEncrypted(),
                c.getEmail(),
                c.getOwnerId(),
                c.getTier(),
                c.getCreatedAt()
        );
    }
}
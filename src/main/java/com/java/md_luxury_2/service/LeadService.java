package com.java.md_luxury_2.service;

import com.java.md_luxury_2.dto.ContactRequest;
import com.java.md_luxury_2.entity.Lead;
import com.java.md_luxury_2.entity.LeadStatus;
import com.java.md_luxury_2.repository.LeadRepository;
import org.springframework.stereotype.Service;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public void receiveFromWebsite(ContactRequest request) {
        // Bắt honeypot: nếu bot điền dữ liệu vào trường ẩn thì âm thầm bỏ qua
        if (request.honeypot() != null && !request.honeypot().isBlank()) {
            return;
        }

        Lead lead = new Lead();
        lead.setFullName(request.fullName());
        lead.setPhone(request.phone());
        lead.setMessage(request.message());
        lead.setSource("WEBSITE_CONTACT_FORM");
        lead.setStatus(LeadStatus.NEW);

        leadRepository.save(lead);
    }
}
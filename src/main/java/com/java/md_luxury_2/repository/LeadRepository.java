package com.java.md_luxury_2.repository;

import com.java.md_luxury_2.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LeadRepository extends JpaRepository<Lead, UUID> {
}
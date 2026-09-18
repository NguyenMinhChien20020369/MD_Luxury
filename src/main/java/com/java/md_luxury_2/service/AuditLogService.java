package com.java.md_luxury_2.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;

@Service
public class AuditLogService {

    public void log(UUID actorId, String action, String entity, UUID entityId, Map<String, Object> detail) {
        // Code ghi nhận vết thao tác vào schema audit.activity_log
        System.out.println("Audit Log: Actor " + actorId + " performed " + action + " on " + entity + " " + entityId);
    }
}
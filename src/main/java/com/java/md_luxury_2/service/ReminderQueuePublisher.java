package com.java.md_luxury_2.service;

import com.java.md_luxury_2.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReminderQueuePublisher {

    private final RabbitTemplate rabbitTemplate;

    public ReminderQueuePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Đẩy sự kiện nhắc nhở khách hàng vào hàng đợi[cite: 1]
    public void publishReminder(UUID customerId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.CUSTOMER_REMINDER_QUEUE, customerId.toString());
    }

    // Đẩy sự kiện có lead mới từ contact form[cite: 1]
    public void publishLeadNotification(UUID leadId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.LEAD_NOTIFICATION_QUEUE, leadId.toString());
    }
}
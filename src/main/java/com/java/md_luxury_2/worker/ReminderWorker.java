package com.java.md_luxury_2.worker;

import com.java.md_luxury_2.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReminderWorker {

    // Giả lập hoặc tiêm (inject) các Service xử lý email/thông báo tại đây
    // private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.CUSTOMER_REMINDER_QUEUE)
    public void handleCustomerReminder(String customerIdStr) {
        UUID customerId = UUID.fromString(customerIdStr);
        // Xử lý gửi email/tin nhắn nhắc nhở cho khách hàng ở luồng nền[cite: 1]
        System.out.println("[Worker] Đang xử lý gửi email nhắc nhở cho Customer ID: " + customerId);
    }

    @RabbitListener(queues = RabbitMQConfig.LEAD_NOTIFICATION_QUEUE)
    public void handleLeadNotification(String leadIdStr) {
        UUID leadId = UUID.fromString(leadIdStr);
        // Xử lý gửi email thông báo cho đội ngũ Sale khi có Lead mới[cite: 1]
        System.out.println("[Worker] Đang xử lý thông báo Lead mới tới đội Sale cho Lead ID: " + leadId);
    }
}
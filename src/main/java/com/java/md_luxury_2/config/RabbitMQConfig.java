package com.java.md_luxury_2.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String LEAD_NOTIFICATION_QUEUE = "lead-notification-queue";
    public static final String CUSTOMER_REMINDER_QUEUE = "customer-reminder-queue";

    @Bean
    public Queue leadNotificationQueue() {
        return new Queue(LEAD_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Queue customerReminderQueue() {
        return new Queue(CUSTOMER_REMINDER_QUEUE, true);
    }
}
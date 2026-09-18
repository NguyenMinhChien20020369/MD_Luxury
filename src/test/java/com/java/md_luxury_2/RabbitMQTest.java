package com.java.md_luxury_2;

import com.java.md_luxury_2.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSendMessage() throws InterruptedException {
        String msg = "Xin chao RabbitMQ tu Spring Boot!";

        // Gửi message vào Exchange
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, msg);
        System.out.println("====== [RABBITMQ SENT]: " + msg + " ======");

        // Đợi 2 giây để Consumer kịp nhận tin nhắn trước khi Test kết thúc
        Thread.sleep(2000);
    }
}
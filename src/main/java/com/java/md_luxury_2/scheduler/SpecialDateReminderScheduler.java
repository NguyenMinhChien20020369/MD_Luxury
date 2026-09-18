package com.java.md_luxury_2.scheduler;

import com.java.md_luxury_2.repository.CustomerRepository;
import com.java.md_luxury_2.service.ReminderQueuePublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpecialDateReminderScheduler {

    private final CustomerRepository customerRepository;
    private final ReminderQueuePublisher queuePublisher;

    public SpecialDateReminderScheduler(CustomerRepository customerRepository, ReminderQueuePublisher queuePublisher) {
        this.customerRepository = customerRepository;
        this.queuePublisher = queuePublisher;
    }

    // Chạy vào 07:00 sáng mỗi ngày (giây phút giờ ngày tháng thứ)
    @Scheduled(cron = "0 25 15 * * *")
    public void scanUpcomingSpecialDates() {
        // Quét danh sách khách hàng có dịp đặc biệt trong 7 ngày tới và đẩy vào hàng đợi
        customerRepository.findWithSpecialDateInNextDays(7)
                .forEach(customer -> queuePublisher.publishReminder(customer.getId()));
    }
}
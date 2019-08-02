package pl.jcommerce.joannajaromin.studentbook.service;

import org.springframework.messaging.handler.annotation.Payload;

public interface KafkaReceiver {

    void receive(@Payload Integer gradeId);
}

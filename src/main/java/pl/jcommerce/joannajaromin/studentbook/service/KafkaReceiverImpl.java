package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaReceiverImpl implements KafkaReceiver {

    private final GradeNotificationService gradeNotificationService;

    @Override
    @KafkaListener(topics = "grade")
    public void receive(@Payload Integer gradeId){
        gradeNotificationService.notifyAboutNewGrade(gradeId);
    }



}

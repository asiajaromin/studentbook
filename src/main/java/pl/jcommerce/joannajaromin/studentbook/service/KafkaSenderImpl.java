package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSenderImpl implements KafkaSender {

    private final KafkaTemplate<String,Integer> kafkaTemplate;

    private String topic = "grade";

    @Override
    public void send(Integer gradeId){
        kafkaTemplate.send(topic,gradeId);
    }
}

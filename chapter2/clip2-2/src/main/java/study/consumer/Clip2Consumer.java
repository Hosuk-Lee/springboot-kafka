package study.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Clip2Consumer {

    @KafkaListener(id = "clip2-listener", topics = "clip2")
    public void listen(String message) {
        System.out.println("message=" + message);
    }
}

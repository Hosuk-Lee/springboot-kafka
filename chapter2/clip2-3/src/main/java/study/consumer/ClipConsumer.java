package study.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class ClipConsumer {

    @KafkaListener(id = "clip3-animal-listener", topics = "clip3")
    public void listenClip3(String message) {
        System.out.println("Consumer message=" + message);
    }

}

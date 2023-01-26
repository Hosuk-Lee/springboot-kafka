package study.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class ClipConsumer {

    @KafkaListener(id = "clip3-id", topics = "clip3")
    public void listenClip3(String message) {
        System.out.println("Consumer message=" + message);
    }

    @KafkaListener(id = "clip3-byte-id", topics = "clip3-byte")
    public void listenClip3Byte(String message) {
        System.out.println("Consumer Byte message=" + message);
    }

    @KafkaListener(id = "clip3-request-id", topics = "clip3-request")
    @SendTo
    public String listenClip3Request(String message) {
        System.out.println(message);
        return "Pong Clip3";
    }

//    @KafkaListener(id = "clip3-byte-id", topics = "clip3-byte")
//    public void listenClip3Response(String message) {
//        System.out.println("Consumer Byte message=" + message);
//    }

}

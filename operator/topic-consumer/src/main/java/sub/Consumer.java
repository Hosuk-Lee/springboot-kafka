package sub;

import java.util.Date;
import javax.validation.Valid;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

//    @KafkaListener(id = "clip3-animal-listener", topics = "clip3-animal", containerFactory = "kafkaJsonListenerContainerFactory")
//    public void listenClip3(@Valid Animal animal
//            ,@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp
//    ) {
//        System.out.println("Consumer Timestamp=" + new Date(timestamp) +", Animal=" + animal);
//    }

    @KafkaListener(
//            id = "infra-sample-id", topics = "infra-sample", groupId = "infra-sample-hosuk"
//            topics = "infra-sample", groupId = "infra-sample-hosuk"
              topics = "infra-sample", groupId = "AA"
    )
    public void listen(String message) {
        System.out.println("==========================================");
        System.out.println(message);
        System.out.println("==========================================");
    }
}

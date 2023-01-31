package study.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import study.model.Animal;

import javax.validation.Valid;
import java.util.Date;

@Service
public class ClipConsumer {

    @KafkaListener(id = "clip3-animal-listener", topics = "clip3-animal", containerFactory = "kafkaJsonListenerContainerFactory")
    public void listenClip3(@Valid Animal animal
                            ,@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp
    ) {
         System.out.println("Consumer Timestamp=" + new Date(timestamp) +", Animal=" + animal);
    }


    @KafkaListener(id = "clip3-animal.DLT-listener", topics = "clip3-animal.DLT", containerFactory = "kafkaJsonListenerContainerFactory")
    public void listenAnimalDLT(Animal animal) {
        System.out.println("DLT Animal. animal=" + animal);
    }

}

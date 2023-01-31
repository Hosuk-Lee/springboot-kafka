package study.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import study.model.Animal;

@Service
public class ClipProducer {

    private final KafkaTemplate<String, Animal> kafkaJsonTemplate;

    public ClipProducer(KafkaTemplate<String, Animal> kafkaJsonTemplate) {
        this.kafkaJsonTemplate = kafkaJsonTemplate;
    }


    public void async(String topic, Animal animal) {
        ListenableFuture<SendResult<String, Animal>> future = kafkaJsonTemplate.send(topic, animal);
        future.addCallback(new KafkaSendCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Animal> result) {
                ProducerRecord<String, Animal> record = result.getProducerRecord();
                System.out.println("Success to send message. record=" + record);
            }

            @Override
            public void onFailure(KafkaProducerException e) {
                ProducerRecord<Object, Object> record = e.getFailedProducerRecord();
                System.out.println("Fail to send message. record=" + record);

            }
        });
    }

}

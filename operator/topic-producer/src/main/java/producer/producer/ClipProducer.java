package producer.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import producer.model.Message;

@Service
public class ClipProducer {

    private final KafkaTemplate<String, Message> kafkaJsonTemplate;

    public ClipProducer(KafkaTemplate<String, Message> kafkaJsonTemplate) {
        this.kafkaJsonTemplate = kafkaJsonTemplate;
    }


    public void async(String topic, Message animal) {
        ListenableFuture<SendResult<String, Message>> future = kafkaJsonTemplate.send(topic, animal);
        future.addCallback(new KafkaSendCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Message> result) {
                ProducerRecord<String, Message> record = result.getProducerRecord();
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

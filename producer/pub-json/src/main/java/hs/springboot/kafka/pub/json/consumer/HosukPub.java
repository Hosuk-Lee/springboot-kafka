package hs.springboot.kafka.pub.json.consumer;

import java.util.Map;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HosukPub {

    //    @Autowired
//    KafkaTemplate kafkaJsonTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> kafkaJsonTemplate;


    public HosukPub(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, Object> kafkaJsonTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaJsonTemplate = kafkaJsonTemplate;
    }

    public void async(String topicName, String data){
        System.out.println("@@@@"+topicName + ":" + data);
        kafkaTemplate.send(topicName, data);
    }
    public void async(String topicName, Map<String, Object> data){
        System.out.println("@@@@"+topicName + ":" + data);
        kafkaJsonTemplate.send(topicName, data);
    }
}

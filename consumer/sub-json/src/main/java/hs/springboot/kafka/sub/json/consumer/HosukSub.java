package hs.springboot.kafka.sub.json.consumer;

import java.util.Date;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class HosukSub {

    @KafkaListener(id = "hosuk-listener-id1", topics = "hosuk-test-1")
//    @KafkaListener(topics = "hosuk-test-1")
    public void listen(String message) {
        System.out.println("message="+message);
    }

//    @KafkaListener(id = "hosuk-listener-id2", topics = "hosuk-test-2")
//    public void listenHosuk2(String message
//            ,@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp
//    ) {
//        System.out.println("Consumer Timestamp=" + new Date(timestamp) +", message=" + message);
//    }

    @KafkaListener(topics = "hosuk-test-2", containerFactory = "listenerContainerFactory")
//    @KafkaListener(id="hosuk-listener-id2", topics = "hosuk-test-2", containerFactory = "listenerContainerFactory")
    public void listenHosuk2(Map<String,Object> message
            ,@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp
    ) {
        System.out.println("Consumer Timestamp=" + new Date(timestamp) +", message=" + message);
        System.out.println("@@ : " + message.get("key1"));
        Map<String,Object> key4 = (Map<String, Object>) message.get("key4");
        System.out.println("@@ : " + key4.get("sub_key2"));
    }

}

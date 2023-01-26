package study.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;

public class ClipConsumer extends AbstractConsumerSeekAware {

    @KafkaListener(id = "clip1-listener-id", topics = "clip1-listener")
    public void listen(String message) {
        System.out.println("message="+message);
    }

    public void seek(){
        getSeekCallbacks().forEach((topicPartition, consumerSeekCallback) -> consumerSeekCallback.seek(
                topicPartition.topic(), topicPartition.partition(), 0));

        // 단 하나의 특정 카프카의 콜백을 찾아간다..
        // getSeekCallbackFor();
    }


}

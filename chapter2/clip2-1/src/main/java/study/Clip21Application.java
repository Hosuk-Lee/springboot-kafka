package study;

import java.util.Map;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import study.service.ClipConsumer;
import study.service.KafkaManager;

@SpringBootApplication
public class Clip21Application {


    public static void main(String[] args) {
        SpringApplication.run(Clip21Application.class, args);
    }
    @Bean
    public ApplicationRunner run(KafkaManager kafkaManager,
            KafkaTemplate<String,String> kafkaTemplate,
            ClipConsumer clipConsumer
    ) {
        return args -> {
//            kafkaManager.describeTopicConfigs();
//            kafkaManager.changeConfig();
//            kafkaManager.describeTopicConfigs();
//            kafkaManager.deleteRecords();

            kafkaManager.findAllConsumerGroups();
//            kafkaManager.deleteConsumerGroup();
//            kafkaManager.findAllConsumerGroups();

            kafkaManager.findAllOffsets();

            kafkaTemplate.send("clip1-listener", "Hello, Listener");
            clipConsumer.seek();
        };
    }
}

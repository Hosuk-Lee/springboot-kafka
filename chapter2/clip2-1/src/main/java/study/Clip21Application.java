package study;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import study.service.KafkaManager;

@SpringBootApplication
public class Clip21Application {


    public static void main(String[] args) {
        SpringApplication.run(Clip21Application.class, args);
    }

    @Bean
    public ApplicationRunner run(KafkaManager kafkaManager,
            KafkaTemplate<String,String> kafkaTemplate){
        return args -> {
            kafkaManager.describeTopicConfigs();
            kafkaManager.changeConfig();
            kafkaManager.describeTopicConfigs();
            kafkaManager.deleteRecords();

            kafkaManager.findAllConsumerGroups();
//            kafkaManager.deleteConsumerGroup();
//            kafkaManager.findAllConsumerGroups();

            kafkaManager.findAllOffsets();

            kafkaTemplate.send("clip1-listener", "Hello, Listener");
        };
    }
}

package hs.springboot.kafka.study;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class Clip11Application {


    public static void main(String[] args) {
        SpringApplication.run(Clip11Application.class, args);
    }

    @Bean
    public ApplicationRunner run(KafkaTemplate<String, String> kafkaTemplate){
        return args -> {
            kafkaTemplate.send("quickstart-events", "hello-world");
        };
    }
}

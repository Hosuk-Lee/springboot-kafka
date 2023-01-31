package study;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;

@SpringBootApplication
public class Clip24Application {

    public static void main(String[] args) {
        SpringApplication.run(Clip24Application.class, args);
    }

    @Bean
    public ApplicationRunner run(KafkaTemplate<String,String> kafkaTemplate){
        return args -> {
//            Sample 구분1
//            while (true) {
//                kafkaTemplate.send("clip4", "Hello, Clip4");
//                Thread.sleep(1_000);
//            }

//            Sample 구분2
            while (true) {
                kafkaTemplate.send("clip4", String.valueOf(new Date().getTime()));
                Thread.sleep(1_000);
            }
        };
    }
}

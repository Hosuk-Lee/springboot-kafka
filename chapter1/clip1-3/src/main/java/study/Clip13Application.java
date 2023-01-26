package study;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import study.producer.ClipProducer;

@SpringBootApplication
public class Clip13Application {


    public static void main(String[] args) {
        SpringApplication.run(Clip13Application.class, args);
    }

    @Bean
    public ApplicationRunner run(ClipProducer clipProducer){
        return args -> {
            clipProducer.async("clip3", "Hello, clip3 ---- async");
            clipProducer.sync("clip3", "Hello, clip3 ---- sync");
            clipProducer.routingSend("clip3", "Hello, clip3 ---- routing");
            clipProducer.routingSendBytes("clip3-byte", "Hello, clip3 ---- byte".getBytes(StandardCharsets.UTF_8));
            clipProducer.replyingSend("clip3-request", "Ping Clip3");

        };
    }

//    기본.
//    @Bean
//    public ApplicationRunner run(KafkaTemplate<String,String> kafkaTemplate){
//        return args -> {
//            kafkaTemplate.send("clip3", "Hello, clip3"); // Async
//        };
//    }


}

package producer;

import java.time.LocalDateTime;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import producer.producer.ClipProducer;
import producer.model.Message;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner run(ClipProducer clipProducer){
        return args -> {
            clipProducer.async("infra-sample", new Message("A0001","Message Producer" + LocalDateTime.now()));
            Thread.sleep(1000*5);
        };
    }

}

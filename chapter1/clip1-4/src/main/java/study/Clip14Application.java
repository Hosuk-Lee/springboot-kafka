package study;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import study.model.Animal;
import study.producer.ClipProducer;

@SpringBootApplication
public class Clip14Application {


    public static void main(String[] args) {
        SpringApplication.run(Clip14Application.class, args);
    }

    @Bean
    public ApplicationRunner run(ClipProducer clipProducer){
        return args -> {
            clipProducer.async("clip3-animal", new Animal("Dog",11));
        };
    }

}

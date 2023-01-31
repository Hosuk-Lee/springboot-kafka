package study.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {

    @Bean
    public NewTopic topic(){
        // consumer 같은 경우 별도 토픽을 만들지 않아도 토픽이 생성되지만 STREAM 은 별도 지정해야 한다.
        return TopicBuilder.name("clip4").build();
    }
}

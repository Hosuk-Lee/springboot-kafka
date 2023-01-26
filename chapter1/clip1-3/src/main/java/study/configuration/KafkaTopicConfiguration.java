package study.configuration;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopics clip2s() {
        return new NewTopics(
                TopicBuilder.name("clip3").build(),
                TopicBuilder.name("clip3-byte").build(),
                TopicBuilder.name("clip3-request").build(),
                TopicBuilder.name("clip3-replies").build()
        );
    }

}

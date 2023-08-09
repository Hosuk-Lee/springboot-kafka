package hs.springboot.kafka.pub.json.config;

import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopics hosuk() {
        System.out.println("@@@@@@@");
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("hosuk-test-1").build(),
                TopicBuilder.name("hosuk-test-2")
                        .partitions(3)
                        .replicas(1)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000*60*60)) // 1시간 Retention Time
                        .build()
        );
    }
}

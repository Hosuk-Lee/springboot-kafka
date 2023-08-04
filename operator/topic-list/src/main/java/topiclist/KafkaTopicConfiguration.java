package topiclist;

import java.util.Properties;
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
    public AdminClient adminClient(KafkaAdmin kafkaAdmin) {

        Properties props = new Properties();
        //props.put("bootstrap.servers", "kafka1.pab.kbpsandbox.com:9092");
        props.put("bootstrap.servers", "localhost:9092");
        return AdminClient.create(props);
//        return AdminClient.create( kafkaAdmin.getConfigurationProperties() );
    }

//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put("bootstrap.servers", bootstrapServers);
//
//        return new KafkaAdmin(configs);
//    }
//
//    @Bean
//    public AdminClient adminClient(KafkaAdmin kafkaAdmin) {
//        return AdminClient.create(kafkaAdmin.getConfig());
//    }

}

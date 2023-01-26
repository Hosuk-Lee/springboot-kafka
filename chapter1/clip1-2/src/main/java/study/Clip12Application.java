package study;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.Uuid;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Clip12Application {


    public static void main(String[] args) {
        SpringApplication.run(Clip12Application.class, args);
    }

    @Bean
    public ApplicationRunner run(AdminClient adminClient){
        return args -> {
            printAndDeleteTopicList(adminClient);
        };
    }

    private static void printAndDeleteTopicList(AdminClient adminClient) throws InterruptedException, ExecutionException {
        System.out.println("---------- Topic List ----------");
        Map<String, TopicListing> topics = adminClient.listTopics().namesToListings().get();
        for (String topicName : topics.keySet()) {
            TopicListing topicListing = topics.get(topicName);
            System.out.println(topicListing);

            Map<String, TopicDescription> topicDescriptionMap = adminClient.describeTopics(
                    Collections.singleton(topicName)).allTopicNames().get();
            System.out.println(topicDescriptionMap);

            adminClient.deleteTopics(Collections.singleton(topicName));
        }
        System.out.println("--------------------------------");
    }
}

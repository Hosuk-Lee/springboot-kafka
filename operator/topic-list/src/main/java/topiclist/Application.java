package topiclist;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner run(AdminClient adminClient){
        return args -> {
            printAndDeleteTopicList(adminClient);
            describeTopicConfigs(adminClient);
            findAllConsumerGroups(adminClient);
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
/*
//            adminClient.deleteTopics(Collections.singleton(topicName));
*/
        }

        System.out.println("--------------------------------");
    }

    private static void describeTopicConfigs(AdminClient adminClient) throws ExecutionException, InterruptedException {

        Collection<ConfigResource> resource = List.of(
                new ConfigResource(ConfigResource.Type.TOPIC, "infra-sample")
        );
        DescribeConfigsResult result = adminClient.describeConfigs(resource);
        System.out.println("---- 토픽의 정보 ----");
        System.out.println(result.all().get());

    }

    private static void findAllConsumerGroups(AdminClient adminClient) throws ExecutionException, InterruptedException {
        ListConsumerGroupsResult listConsumerGroupsResult = adminClient.listConsumerGroups();
        Collection<ConsumerGroupListing> consumerGroupListings = listConsumerGroupsResult.valid().get();

        System.out.println("---- 그룹 조회 ----");
        for ( ConsumerGroupListing group : consumerGroupListings ) {
            System.out.println(group);
        }
    }

}

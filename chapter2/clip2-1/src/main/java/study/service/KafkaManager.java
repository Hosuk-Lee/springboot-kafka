package study.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.AlterConfigOp.OpType;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.DeleteRecordsResult;
import org.apache.kafka.clients.admin.DeletedRecords;
import org.apache.kafka.clients.admin.DescribeConfigsResult;

import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.admin.RecordsToDelete;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

@Service
public class KafkaManager {


    private final KafkaAdmin kafkaAdmin;
    private final AdminClient adminClient;

    public KafkaManager(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
        this.adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    public void describeTopicConfigs() throws ExecutionException, InterruptedException {

        Collection<ConfigResource> resource = List.of(
                new ConfigResource(ConfigResource.Type.TOPIC, "clip4-listener")
        );
        DescribeConfigsResult result = adminClient.describeConfigs(resource);
        System.out.println("---- 토픽의 정보 ----");
        System.out.println(result.all().get());

    }

    public void changeConfig() {
        ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, "clip4-listener");
        Map<ConfigResource, Collection<AlterConfigOp>> ops = new HashMap<>();

        // 변경
        // ops.put(resource,List.of(new AlterConfigOp(new ConfigEntry(TopicConfig.RETENTION_MS_CONFIG, "6000"), AlterConfigOp.OpType.SET)));

        // 삭제? 초기화?
        ops.put(resource,List.of(new AlterConfigOp(new ConfigEntry(TopicConfig.RETENTION_MS_CONFIG, null), OpType.DELETE)));

        System.out.println("---- 토픽의 정보 변경 ----");
        adminClient.incrementalAlterConfigs(ops);
    }

    public void deleteRecords() {
        TopicPartition tp = new TopicPartition("clip4-listener", 0);
        Map<TopicPartition, RecordsToDelete> target = new HashMap<>();
        target.put(tp, RecordsToDelete.beforeOffset(1));
        DeleteRecordsResult result = adminClient.deleteRecords(target);
        Map<TopicPartition, KafkaFuture<DeletedRecords>> topicPartitionKafkaFutureMap = result.lowWatermarks();

        System.out.println("---- 토픽의 레코드 삭제 ----");
        for (Entry<TopicPartition, KafkaFuture<DeletedRecords>> topicPartitionKafkaFutureEntry : topicPartitionKafkaFutureMap.entrySet()) {
            System.out.println(
                    topicPartitionKafkaFutureEntry.getKey().topic() + ", " +
                    topicPartitionKafkaFutureEntry.getKey().partition() + ", "  +
                    topicPartitionKafkaFutureEntry.getValue());
        }
    }

    public void findAllConsumerGroups() throws ExecutionException, InterruptedException {
        ListConsumerGroupsResult listConsumerGroupsResult = adminClient.listConsumerGroups();
        Collection<ConsumerGroupListing> consumerGroupListings = listConsumerGroupsResult.valid().get();

        System.out.println("---- 그룹 조회 ----");
        for ( ConsumerGroupListing group : consumerGroupListings ) {
            System.out.println(group);
        }
    }

    public void deleteConsumerGroup() throws ExecutionException, InterruptedException {
        // (groupId='clip4-animal-listener', isSimpleConsumerGroup=false, state=Optional[Stable])
        // The group is not empty.
        // adminClient.deleteConsumerGroups(List.of("clip4-animal-listener")).all().get();

        adminClient.deleteConsumerGroups(List.of("clip4-container")).all().get();

        // The group id does not exist.
        // 여러번 하면 존재하지 않음 오류발생
    }

    public void findAllOffsets() throws ExecutionException, InterruptedException {
        Map<TopicPartition, OffsetSpec> target = new HashMap<>();
        target.put(new TopicPartition("clip4-listener", 0), OffsetSpec.latest());
        ListOffsetsResult listOffsetsResult = adminClient.listOffsets(target);

        for (TopicPartition topicPartition: target.keySet()
        ) {
            System.out.println(
                    "topic=" + topicPartition.topic() + ", " +
                    "partition=" + topicPartition.partition() + ", " +
                    "offsets=" + listOffsetsResult.partitionResult(topicPartition).get()
            );
        }
    }
}

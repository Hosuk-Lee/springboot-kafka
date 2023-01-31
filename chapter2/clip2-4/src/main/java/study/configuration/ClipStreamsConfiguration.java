package study.configuration;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class ClipStreamsConfiguration {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        // streamsBuilder.stream(topic); topic 설정
        // streamsBuilder.stream(pattern); multi topic 지원
        // streamsBuilder.stream(topic, consumed); consumed 는 key value 직렬화,역직렬화 가능

        KStream<String,String> stream = streamsBuilder.stream("clip4");

        // peek > return 을 하지 않고 어떤 일들을 수행만 함.
        // 기본예제
//        stream.peek((key, value) -> System.out.println("Stream. message=" + value))
//                .map((key, value) -> KeyValue.pair(key, "Hello, Listener")) // 재발행 할때는 변경가능하다.
//                .to("clip4-to") // 어디로 발행을 할 것인가.
        ;


//        stream.groupBy((key,value)-> value)
//                .count()
//                .toStream()
//                .peek((key, value) -> System.out.println("key="+ key + ", value="+value))
        // 이렇게하면 Listener 로 전달되진 않음.
        // .to("clip4-to") 실제 보내면 이상한 값이 전달 됨.
        ;

        // Sample 구분2
        // Branch 를 사용하게되면 타입별로, Message 별로 처리할 수 있음.
        // 2.8 이후 삭제 됨.
        KStream<String, String>[] branches = stream.branch(
                (key, value) -> Long.valueOf(value) % 10 == 0,
                (key, value) -> true
        );
        branches[0].peek((key, value) ->System.out.println("Branch 0. message=" + value)).to("clip4-to");
        branches[1].peek((key, value) ->System.out.println("Branch 1. message=" + value));

        return stream;
    }

    // application id 는 컨슈머의 group id를 대체한다. 그리고 client-id도 대체한다.
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfigs(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(StreamsConfig.APPLICATION_ID_CONFIG,"clip4-streams-id");
        configs.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        configs.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // configs.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "2"); // Thread 가 2개 생성됨.
        configs.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        // AT_LEAST_ONCE 적어도 한번
        // EXACTLY_ONCE_V2 무조건 한번
        return new KafkaStreamsConfiguration(configs);
    }

    //Consumer
    // clientId=clip4-streams-id-ccd9613b-6a57-45e5-9c04-6a5bda28e694-StreamThread-1-consumer,
    // groupId=clip4-streams-id] Found no committed offset for partition clip4-0
    //stream-thread
    // clip4-streams-id-ccd9613b-6a57-45e5-9c04-6a5bda28e694-StreamThread-1
    //

}

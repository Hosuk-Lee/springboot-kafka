package sub.configuration;


import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import sub.model.Message;

//@Configuration
public class KafkaJsonListenerContainerConfiguration
        implements KafkaListenerConfigurer {

    private final KafkaTemplate<String, Message> kafkaTemplate;


    public KafkaJsonListenerContainerConfiguration(KafkaTemplate<String, Message> kafkaTemplate,
            LocalValidatorFactoryBean validator) {
        this.kafkaTemplate = kafkaTemplate;
        this.validator = validator;
    }

//    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Message>> kafkaJsonListenerContainerFactory(
            KafkaTemplate<String, Message> kafkaJsonTemplate
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(messageConsumerFactory());


        // 이걸 알아봐야 함..
        // factory.setCommonErrorHandler();



        // 결국엔 없어질 함수들..
        /* 기초
        factory.setRetryTemplate(customizedRetryTemplate()); // 설정한 시간만큼 있다가 오류발생.
        factory.setRecoveryCallback(retryContext ->  {
            ConsumerRecord record = (ConsumerRecord) retryContext.getAttribute("record");
            System.out.println("Record callback. message" + record.value() );
            // return Optional.empty();
            throw  new RuntimeException("Runtime Exception..");
        });
        factory.setErrorHandler((throwsException, data) -> System.out.println("Exception Handler. exception=" + throwsException.getMessage() +","+data));
         */
        // DLT로 보내는 방법
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaJsonTemplate)));

//        CommonErrorHandler commonErrorHandler = new CommonErrorHandler() {
//
//        };
        factory.setCommonErrorHandler(customErrorHandler());

        return factory;
    }

    private CommonErrorHandler customErrorHandler() {
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        //defaultErrorHandler.reco
//        defaultErrorHandler.addNotRetryableExceptions();
        return null;
    }

    private ConsumerFactory<String, Message> messageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                props(),
                new StringDeserializer(),
                new JsonDeserializer<>(Message.class)
        );
    }

    private Map<String, Object> props() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    private final LocalValidatorFactoryBean validator;

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar kafkaListenerEndpointRegistrar) {
        // validation
        kafkaListenerEndpointRegistrar.setValidator(validator);
    }
}

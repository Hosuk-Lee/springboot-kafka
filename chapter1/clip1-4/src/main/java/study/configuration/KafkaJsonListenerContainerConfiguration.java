package study.configuration;


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
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import study.model.Animal;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaJsonListenerContainerConfiguration
        implements KafkaListenerConfigurer {

    private final KafkaTemplate<String, Animal> kafkaTemplate;


    public KafkaJsonListenerContainerConfiguration(KafkaTemplate<String, Animal> kafkaTemplate,
            LocalValidatorFactoryBean validator) {
        this.kafkaTemplate = kafkaTemplate;
        this.validator = validator;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Animal>> kafkaJsonListenerContainerFactory(
            KafkaTemplate<String, Animal> kafkaJsonTemplate
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Animal> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(animalConsumerFactory());


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

    private RetryTemplate customizedRetryTemplate() {
        // 2.8버전부터 없어 진듯
        return new RetryTemplateBuilder()
                .fixedBackoff(1_000)
                .customPolicy(retryPolicy())
                .build();
    }

    private RetryPolicy retryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptions = new HashMap<>();
        exceptions.put(ListenerExecutionFailedException.class, true);

        return new SimpleRetryPolicy(3, exceptions);
    }

    private ConsumerFactory<String, Animal> animalConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                props(),
                new StringDeserializer(),
                new JsonDeserializer<>(Animal.class)
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

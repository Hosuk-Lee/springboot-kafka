package hs.springboot.kafka.pub.json;

import hs.springboot.kafka.pub.json.consumer.HosukPub;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
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
    public ApplicationRunner run(HosukPub hs){
        Map<String, Object> data = new HashMap<>();
        data.put("key1", "String 1");
        data.put("key2", "String 2");
        data.put("key3", BigInteger.ZERO);
        Map<String, Object> subdata = new HashMap<>();
        subdata.put("sub_key1", "String1");
        subdata.put("sub_key2", Boolean.valueOf(true));
        data.put("key4", subdata);
        return args -> {
//            hs.async("hosuk-test-1", data);
            hs.async("hosuk-test-1", "Hello");
            hs.async("hosuk-test-2", data);
            Thread.sleep(1_000L);
        };
    }


}
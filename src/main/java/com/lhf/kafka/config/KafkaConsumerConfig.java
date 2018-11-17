package com.lhf.kafka.config;

import com.lhf.kafka.common.MessageEntity;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: KafkaConsumerConfig
 * @Description:  kafka消费者配置
 * @Author: liuhefei
 * @Date: 2018/11/17
 **/
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    //服务地址
    @Value("${kafka.consumer.servers}")
    private String servers;

    //是否自动提交
    @Value("${kafka.consumer.enable.auto.commit}")
    private boolean enableAutoCommit;

    //session时间
    @Value("${kafka.consumer.session.timeout}")
    private String sessionTimeout;

    //提交的间隔
    @Value("${kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;

    @Value("${kafka.consumer.group.id}")
    private String groupId;

    //重置
    @Value("${kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;

    //并发数
    @Value("${kafka.consumer.concurrency}")
    private int concurrency;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MessageEntity>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, MessageEntity> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(1500);

        return  factory;
    }

    private ConsumerFactory<String, MessageEntity> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(MessageEntity.class)
        );
    }

    /**
     * 封装配置
     * @return
     */
    private Map<String, Object> consumerConfigs(){
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

        return propsMap;
    }
}

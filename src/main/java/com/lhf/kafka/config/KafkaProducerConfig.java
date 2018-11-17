package com.lhf.kafka.config;

import com.lhf.kafka.common.MessageEntity;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: KafkaProducerConfig
 * @Description: kafka生产者配置
 * @Author: liuhefei
 * @Date: 2018/11/17
 **/
@Configuration
@EnableKafka
public class KafkaProducerConfig {
    //服务地址
    @Value("${kafka.producer.servers}")
    private String servers;

    //重试次数
    @Value("${kafka.producer.retries}")
    private int retries;

    //批处理大小
    @Value("${kafka.producer.batch.size}")
    private int batchSize;

    //将多少时间范围内的数据作为一个批次发送，减少交互次数
    @Value("${kafka.producer.linger}")
    private int linger;

    //缓存空间
    @Value("${kafka.producer.buffer.memory}")
    private int bufferMemory;

    /**
     * 封装配置
     * @return
     */
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    public ProducerFactory<String, MessageEntity> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(),
                new StringSerializer(),
                new JsonSerializer<MessageEntity>());
    }

    @Bean
    public KafkaTemplate<String, MessageEntity> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}

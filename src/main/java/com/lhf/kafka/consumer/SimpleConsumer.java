package com.lhf.kafka.consumer;

import com.google.gson.Gson;
import com.lhf.kafka.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SimpleConsumer
 * @Description: 消息消费者
 * @Author: liuhefei
 * @Date: 2018/11/17
 **/
@Slf4j
@Component
public class SimpleConsumer {

    private final Gson gson = new Gson();

    /**
     * 消费者监听消息
     * @param message
     */
    @KafkaListener(topics = "${kafka.topic.default}", containerFactory = "kafkaListenerContainerFactory")
    public void receive(MessageEntity message){
        log.info(gson.toJson(message));
    }
}

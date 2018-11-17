package com.lhf.kafka.controller;

import com.google.gson.Gson;
import com.lhf.kafka.common.ErrorCode;
import com.lhf.kafka.common.MessageEntity;
import com.lhf.kafka.common.Response;
import com.lhf.kafka.producer.SimpleProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ProduceController
 * @Description: 控制层
 * @Author: liuhefei
 * @Date: 2018/11/17
 **/
@Slf4j
@RestController
@RequestMapping("/kafka")
public class ProduceController {

    @Autowired
    private SimpleProducer simpleProducer;

    @Value("${kafka.topic.default}")
    private String topic;

    private Gson gson = new Gson();

    //http://localhost:8080/kafka/index
    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = {"application/json"})
    public Response sendKafka(){
        return new Response(ErrorCode.SUCCESS, "OK");
    }

    //http://localhost:8080/kafka/send
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {"application/json"})
    public Response sendKafka(@RequestBody MessageEntity message){
        try{
            log.info("kafka消息= {}", gson.toJson(message));
            simpleProducer.send(topic, "key", message);
            log.info("发送kafka消息成功");
            return new Response(ErrorCode.SUCCESS, "发送kafka成功");
        }catch (Exception e){
            log.error("发送kafka失败",e);
            return new Response(ErrorCode.EXCEPTION, "发送kafka失败");
        }

    }
}

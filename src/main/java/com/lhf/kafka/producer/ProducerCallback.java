package com.lhf.kafka.producer;

import com.google.gson.Gson;
import com.lhf.kafka.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @ClassName: ProducerCallback
 * @Description: 消息生产者发送消息接口
 * @Author: liuhefei
 * @Date: 2018/11/17
 **/
@Slf4j
public class ProducerCallback implements ListenableFutureCallback<SendResult<String, MessageEntity>>{

    //消息发送时间
    private final long startTime;

    //消息key
    private final String key;

    //消息内容
    private final MessageEntity message;

    private final Gson gson = new Gson();

    public ProducerCallback(long startTime, String key, MessageEntity message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    /**
     * 消息发送失败
     * @param ex
     */
    @Override
    public void onFailure(Throwable ex) {
        ex.printStackTrace();
    }

    /**
     * 消息发送成功
     * @param result
     */
    @Override
    public void onSuccess(SendResult<String, MessageEntity> result) {
        if(result == null){
            return;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("time :" + elapsedTime);

        RecordMetadata metadata = result.getRecordMetadata();
        if(metadata != null){
            StringBuilder record = new StringBuilder();
            record.append("message(")
                    .append("key = ").append(key).append(",")
                    .append("message = ").append(gson.toJson(message)).append(")")
                    .append("send to partition(").append(metadata.partition()).append(")")
                    .append("with offset(").append(metadata.offset()).append(")")
                    .append("in ").append(elapsedTime).append(" ms");
            log.info(record.toString());
        }
    }
}

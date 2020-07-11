package com.dfjx.sendlog.sink.impl;/**
 * <h3>kafkaUtils</h3>
 * <p>执行kafka任务</p>
 *
 * @author : PanhuGao
 * @date : 2020-06-07 16:50
 **/

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.BlockingQueue;

/**
 * @创建人 PanHu.Gao
 * @创建时间 2020/6/7
 * @描述
 */
@Slf4j
public class SendMsgToKafkaTask implements Runnable {

    private String topicName;
    private String msg;
    private BlockingQueue<KafkaProducer<String,String>> queue;

    public SendMsgToKafkaTask(BlockingQueue<KafkaProducer<String,String>> queue,String topicName,String msg){
        this.queue = queue;
        this.msg = msg;
        this.topicName = topicName;
    }

    @Override
    public void run() {
          KafkaProducer<String,String> kafkaProducer = null;

        try {
            kafkaProducer = queue.take();
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, msg);
            log.info("sink msg to kafka, topic :{}",topicName);
            kafkaProducer.send(record);
        } catch (InterruptedException e) {
           log.error("sink msg to kafka error,topic :{}",topicName,e);
        }finally {
            if(null != kafkaProducer){
                try {
                    //归还连接到连接池
                    queue.put(kafkaProducer);
                } catch (InterruptedException e) {
                    log.error("sink msg to kafka error,topic :{}",topicName,e);
                }
            }
        }

    }
}

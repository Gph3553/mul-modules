package com.dfjx.sendlog.sink.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * <h3>kafkaUtils</h3>
 * <p>kafka客户端</p>
 * @author : PanhuGao
 * @date : 2020-06-07 15:33
 **/

@Slf4j
@Component
public class KafkaClient {
    @Value("${spring.kafka.producerNum}")
    public int producerNum;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.acks}")
    public String acks;
    @Value("${spring.kafka.retries}")
    public String retries;
    @Value("${spring.kafka.batchSize}")
    public String batchSize;
    @Value("${spring.kafka.compressionType}")
    public String compressionType;
    @Value("${spring.kafka.linger-ms}")
    public String lingerMs;
    @Value("${spring.kafka.bufferMemory}")
    public String bufferMemory;
    @Value("${spring.kafka.key-serializer}")
    public String keyserializer;
    @Value("${spring.kafka.value-serializer}")
    public String valueSerializer;

    private BlockingQueue<KafkaProducer<String,String>> queue;

    private ObjectMapper mapper = new ObjectMapper();

    private static ThreadFactory  nameThreadFactory =  new ThreadFactoryBuilder().setNameFormat("kafa-log").build();

   private static ExecutorService service=  new ThreadPoolExecutor(10,20,0L,TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>(1024),nameThreadFactory,new ThreadPoolExecutor.AbortPolicy());
    @PostConstruct
    public void getKafkaConfig(){
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        prop.put(ProducerConfig.ACKS_CONFIG, acks);
        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        prop.put(ProducerConfig.BUFFER_MEMORY_CONFIG,bufferMemory);
        prop.put(ProducerConfig.LINGER_MS_CONFIG,lingerMs);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keyserializer);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,valueSerializer);
        prop.put(ProducerConfig.RETRIES_CONFIG, retries);
        // TBDS原生认证参数设置
//        prop.put("security.protocol", "SASL_TBDS");
//        prop.put("sasl.mechanism", "TBDS");
//        prop.put("sasl.tbds.secure.id", "4PXcEaAhtyFbzn22LSqEMP4p0WB4oR59Gqq3");
//        prop.put("sasl.tbds.secure.key", "WHHYqxWnPndbUZZtJLfa0T1XIW3CSUWx");

        queue = new LinkedBlockingQueue<>(producerNum);
        for(int i = 0; i< producerNum; i++){
            log.info("kafka生产者"+i+queue.toString());
            KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(prop);
             queue.add(kafkaProducer);
        }
    }

    public void  sinkLogToKafka(String topicName,Object result){
        try {
            String s = mapper.writeValueAsString(result);
            service.execute(new SendMsgToKafkaTask(queue,topicName,s));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }






















}

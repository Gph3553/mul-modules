package com.dfjx.sendlog.sink.impl;
/**
 * <h3>kafkaUtils</h3>
 * <p>日志实现类</p>
 * @author : PanhuGao
 * @date : 2020-06-07 17:08
 **/

import com.dfjx.sendlog.model.UserActionLog;
import com.dfjx.sendlog.sink.SinkLogToKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SinkLogToKafkaImpl implements SinkLogToKafka {

    @Value("${spring.kafka.topic-userAction}")
    private  String userBehavior;
    @Autowired
    private KafkaClient kafkaClient;
    @Override
    public void sendUserActionLog(String optTime, String visitModule, String function, String optContent, String requestPra, String servicePath) {
        UserActionLog userActionLog = new UserActionLog();
        userActionLog.setOptTime(optTime);
        userActionLog.setVisitModule(visitModule);
        userActionLog.setFunction(function);
        userActionLog.setOptContent(optContent);
        userActionLog.setRequestPra(requestPra);
        userActionLog.setServicePath(servicePath);
        kafkaClient.sinkLogToKafka(userBehavior,userActionLog);
    }
}

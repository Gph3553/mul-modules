package com.dfjx.sendlog;/**
 * <h3>kafkaUtils</h3>
 * <p>kafka入口</p>
 *
 * @author : PanhuGao
 * @date : 2020-06-09 12:02
 **/

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @创建人 PanHu.Gao
 * @创建时间 2020/6/9
 * @描述
 */
@Configuration
@ComponentScan("com.dfjx.sendlog.**")
public class KafkaOpeStart {
    public static void main(String[] args) {
        System.out.println("启动Kafka工具类");
    }
}

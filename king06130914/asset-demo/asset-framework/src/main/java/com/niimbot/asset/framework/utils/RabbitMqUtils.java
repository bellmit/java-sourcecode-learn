package com.niimbot.asset.framework.utils;

import com.niimbot.asset.framework.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * RabbitMq工具类
 */
@Slf4j
@Component
public class RabbitMqUtils {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

    public void send(String queueName, String jsonString) {
        String uuid = UUID.randomUUID() + "";
        // 生产者发送消息的时候需要设置消息id
        Message message = MessageBuilder.withBody(jsonString.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
                .setMessageId(uuid).build();
        //将messageId存入redis（在监听器判空，防止重复消费）
        redisService.set(uuid, uuid);
        rabbitTemplate.convertAndSend(queueName, message);
    }

}
package com.niimbot.asset.mq;

import com.alibaba.fastjson.JSONObject;
import com.niimbot.asset.framework.service.RedisService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class FanoutSmsConsumer {

	@Autowired
	private RedisService redisService;

	@RabbitListener(queues = "test_queue")
	@RabbitHandler
	public void consumeMessage(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
		String messageId = message.getMessageProperties().getMessageId();
		if(!redisService.hasKey(messageId)){
			return;//已经被消费
		}
		String msg = new String(message.getBody(), StandardCharsets.UTF_8);
		JSONObject jsonObject = JSONObject.parseObject(msg);
		System.out.println("消费消息：" + jsonObject);

		System.out.println(Thread.currentThread().getName()
				+ ",msg:" + jsonObject
				+ ",messageId:" + messageId);

		// 业务代码
		//		String email = jsonObject.getString("email");
//		String emailUrl = "http://127.0.0.1:8083/sendEmail?email=" + email;
//		JSONObject result = HttpClientUtils.httpGet(emailUrl);
//		//如果调用第三方邮件接口无法访问，如何实现自动重试？抛出异常即可
//		if (result == null) {
//			throw new Exception("调用第三方邮件服务器接口失败!");//如果走到这一行，则会自动重试
//		}

		// 手动ack
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
		// 手动签收
		channel.basicAck(deliveryTag, false);

		redisService.del(messageId);//删除，实际开发中也可以设置为空，只需要在上面判空即可
	}

}
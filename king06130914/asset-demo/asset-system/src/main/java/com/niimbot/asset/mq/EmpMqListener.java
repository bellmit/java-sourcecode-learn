package com.niimbot.asset.mq;

import com.niimbot.asset.framework.constant.MqConstant;
import com.niimbot.system.CusEmployeeDto;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "rocketmq-group", topic = MqConstant.ASSET_TOPIC)
class EmpMqListener implements RocketMQListener<CusEmployeeDto> {
    @Override
    public void onMessage(CusEmployeeDto employeeDto) {
        System.out.println("接收到消息，开始消费..name:" + employeeDto.getEmpName() + ",id:" + employeeDto.getId());
    }
}
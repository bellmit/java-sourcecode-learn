package com.hechuan.event.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.hechuan.event.spring.event.TaskEvent;

/**
 * A service
 * 
 * @author hechuan
 *
 * @created 2017年6月13日
 *
 * @version 1.0.0
 */
@EnableAsync
@Component(value="Eservice")
public class EService implements IService{
	
	private static final Logger logger = LoggerFactory.getLogger(EService.class);
	
	@EventListener(condition  = "#taskEvent.bizId eq '3'")
	@Override
	public void notice(TaskEvent taskEvent) {
	    logger.info("taskEvent : {}",taskEvent);
	}
}

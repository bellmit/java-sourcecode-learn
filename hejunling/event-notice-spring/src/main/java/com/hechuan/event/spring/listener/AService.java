package com.hechuan.event.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
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
@Component(value="Aservice")
public class AService implements IService{
	
	private static final Logger logger = LoggerFactory.getLogger(AService.class);
	
	@EventListener(condition  = "#taskEvent.bizId == 'a'")
	@Override
	public void notice(TaskEvent taskEvent) {
	    logger.info("taskEvent : {}",taskEvent);
	}
}

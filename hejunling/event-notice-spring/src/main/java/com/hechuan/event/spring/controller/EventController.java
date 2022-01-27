package com.hechuan.event.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hechuan.event.spring.domain.TaskBean;
import com.hechuan.event.spring.event.TaskEvent;

@RequestMapping("/event")
@RestController
public class EventController {

	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired  
    private ApplicationContext applicationContext;  

	/**
	 * 事件执行
	 * 
	 * @param bizId
	 * @return
	 */
	@RequestMapping(value = "/do", method = RequestMethod.POST)
	public String doEvent(String bizId) {
		TaskEvent taskEvent = new TaskEvent("taskEvent");

		TaskBean bean = new TaskBean();
		bean.setChange(true);
		bean.setTaskId(Math.random() + "");
		bean.setTaskName("postEvent");

		taskEvent.setBizId(bizId).setTask(bean);
		
		applicationContext.publishEvent(taskEvent);
		
		logger.info("execute completely....");
		
		return "success";

	}
}

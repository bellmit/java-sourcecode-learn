package com.hechuan.event.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hechuan.event.spring.domain.TaskBean;
import com.hechuan.event.spring.domain.TaskRespBean;
import com.hechuan.event.spring.event.TaskRespEvent;

/**
 * 有返回值
 * 
 * @author hechuan
 *
 * @created 2017年6月13日
 *
 * @version 1.0.0
 */
@RequestMapping("/event/resp")
@RestController
public class EventRespController {

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
		TaskRespEvent taskRespEvent = new TaskRespEvent("taskRespEvent");

		TaskBean bean = new TaskBean();
		bean.setChange(true);
		bean.setTaskId(Math.random() + "");
		bean.setTaskName("postEvent");

		taskRespEvent.setBizId(bizId).setTask(bean);

		applicationContext.publishEvent(taskRespEvent);

		logger.info("execute completely....");
		
		TaskRespBean respBean = taskRespEvent.getRespBean();
		
		logger.info("respBean = {}",respBean);

		return "success";

	}
}

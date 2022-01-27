package com.hechuan.event.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hechuan.event.spring.domain.TaskRespBean;
import com.hechuan.event.spring.event.TaskRespEvent;

/**
 * Bresponse service
 * 
 * @author hechuan
 *
 * @created 2017年6月13日
 *
 * @version 1.0.0
 */
@Component(value = "RespBService")
public class RespBService implements IRespService {

	private static final Logger logger = LoggerFactory.getLogger(RespBService.class);

	@EventListener(condition = "#p0.bizId == '2'")
	@Override
	public void notice(TaskRespEvent taskRespEvent) {

		logger.info("RespBService.taskRespEvent B....exectue.....");

		TaskRespBean respBean = new TaskRespBean();
		respBean.setResult("成功-杭州-B！");
		respBean.setSuccess(true);

		taskRespEvent.setRespBean(respBean);
	}

}

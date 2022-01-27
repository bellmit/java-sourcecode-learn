package com.hechuan.event.spring.listener;

import com.hechuan.event.spring.event.TaskRespEvent;

/**
 * 有返回值的
 * 
 * @author hechuan
 *
 * @created 2017年6月13日
 *
 * @version 1.0.0
 */
public interface IRespService {

	/**
	 * 通知事件
	 * 
	 * @param taskRespEvent
	 */
	public void notice(TaskRespEvent taskRespEvent);
}

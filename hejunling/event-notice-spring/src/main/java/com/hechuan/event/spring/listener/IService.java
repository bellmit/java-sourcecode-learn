package com.hechuan.event.spring.listener;

import com.hechuan.event.spring.event.TaskEvent;

/**
 * 通知事件
 * 
 * @author hechuan
 *
 * @created 2017年6月13日
 *
 * @version 1.0.0
 */
public interface IService {
	
	/**
	 * 通知事件
	 * @param taskEvent
	 */
	public void notice(TaskEvent taskEvent);
}

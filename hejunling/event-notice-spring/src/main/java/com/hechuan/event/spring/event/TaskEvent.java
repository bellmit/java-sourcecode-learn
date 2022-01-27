package com.hechuan.event.spring.event;

import org.springframework.context.ApplicationEvent;

import com.google.common.base.MoreObjects;
import com.hechuan.event.spring.domain.TaskBean;

/**
 * 任务事件
 * 
 * @author hechuan
 *
 * @created 2017年6月13日
 *
 * @version 1.0.0
 */
public class TaskEvent extends ApplicationEvent {

	public TaskEvent(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 任务实体 */
	private TaskBean task;

	/** 业务ID */
	private String bizId;
	

	public TaskBean getTask() {
		return task;
	}

	public TaskEvent setTask(TaskBean task) {
		this.task = task;
		return this;
	}

	public String getBizId() {
		return bizId;
	}

	public TaskEvent setBizId(String bizId) {
		this.bizId = bizId;
		return this;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("task", task).add("bizId", bizId).omitNullValues().toString();
	}
	
	
	
	
}

package com.hechuan.event.spring.domain;

import java.io.Serializable;

import com.google.common.base.MoreObjects;

/**
 * 任务BEAN
 * 
 * @author hechuan
 *
 * @created 2017年6月13日
 *
 * @version 1.0.0
 */
public class TaskBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 任务ID */
	private String taskId;

	/** 是否改变 */
	private boolean change;

	/** 任务名称 */
	private String taskName;

	public String getTaskId() {
		return taskId;
	}

	public TaskBean setTaskId(String taskId) {
		this.taskId = taskId;
		return this;
	}

	public boolean isChange() {
		return change;
	}

	public TaskBean setChange(boolean change) {
		this.change = change;
		return this;
	}

	public String getTaskName() {
		return taskName;
	}

	public TaskBean setTaskName(String taskName) {
		this.taskName = taskName;
		return this;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("taskId", taskId).add("taskName", taskName).add("change", change).omitNullValues().toString();
	}
}

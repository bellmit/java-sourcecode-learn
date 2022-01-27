package com.hechuan.event.spring.domain;

import java.io.Serializable;

import com.google.common.base.MoreObjects;

public class TaskRespBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否成功
	 */
	public boolean success;

	/**
	 * 结果如何
	 */
	private String result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("success", success).add("result", result).omitNullValues()
				.toString();
	}
}

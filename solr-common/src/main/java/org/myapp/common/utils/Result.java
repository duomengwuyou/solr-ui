package org.myapp.common.utils;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 736968089362283709L;
	long time;
	Object data;
	int code;
	String message;
	int page = 0;//当前页
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	int pageCount = 0;//总页数
	Long total = 0l; //总记录数

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTotal() {
		return total;
	}

}

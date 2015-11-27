package org.myapp.common.utils;

import java.util.Date;


public class ResultUtils  {

	public static Result result(Object data) {
		Result result = new Result();
		result.setTime(new Date().getTime());
		if(data == null) {
			data = "{}";
		}
		
		result.setData(data);
		return result;
	}
	
	public static Result result(Object data, int page, Long total) {
		Result result = new Result();
		result.setPage(page);
		result.setTotal(total);
		result.setPageCount((total.intValue() / Constants.PAGE_SIZE) + 1);
		result.setTime(new Date().getTime());
		if(data == null) {
			data = "{}";
		}
		
		result.setData(data);
		return result;
	}
	
	
	public static Result result() {
		return result(null);
	}
}

package com.TourGuideApplication.exception;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		log.error("A problem occured with async method "+ method.getName());
		log.error("Exception raised is: "+ex);
		log.error("Message: "+ex.getMessage());
		log.error("Caused by: "+ex.getCause());
	}

	
	
	

}

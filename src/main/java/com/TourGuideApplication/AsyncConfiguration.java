package com.TourGuideApplication;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.TourGuideApplication.exception.AsyncExceptionHandler;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer{
	
	@Override
	public Executor getAsyncExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(128);
	    executor.setMaxPoolSize(256);
	    executor.setQueueCapacity(100000);
	    executor.setThreadNamePrefix("AsyncThread-");
	    executor.initialize();
	    return executor;
	}
	
	@Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

}

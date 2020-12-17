package com.TourGuideApplication;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.model.User;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.UserProxy;
import com.TourGuideApplication.service.UserLocationService;
import com.TourGuideApplication.service.UserService;



public class TourGuideApplicationTracker extends Thread{

	private Logger logger = LoggerFactory.getLogger(TourGuideApplicationTracker.class);
	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private boolean stop = false;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserLocationService userLocationService;
	
	@Autowired
	private LocationProxy locationProxy;
	
	@Autowired
	private UserProxy userProxy;
	
	public TourGuideApplicationTracker() {
		executorService.submit(this);
	}
	
	public void trackUserLocation(UUID userId) {
		VisitedLocationBean visitedLocation = locationProxy.getUserLocation(userId);
		userProxy.addUserVisitedLocation(userId, visitedLocation);
		// rewardsService.generateUserRewards(user);
	}
	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {
		stop = true;
		executorService.shutdownNow();
	}
	
	@Override
	public void run() {
		System.out.println("Tracker is running");
		StopWatch stopWatch = new StopWatch();
		while(true) {
			if(Thread.currentThread().isInterrupted() || stop) {
				logger.debug("Tracker stopping");
				break;
			}
			
			List<User> users = userService.getAllUsers();
			logger.debug("Begin Tracker. Tracking " + users.size() + " users.");
			stopWatch.start();
			users.forEach(u -> userLocationService.trackUserLocation(u.getUserId()));
			stopWatch.stop();
			logger.debug("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
			stopWatch.reset();
			try {
				logger.debug("Tracker sleeping");
				TimeUnit.SECONDS.sleep(trackingPollingInterval);
			} catch (InterruptedException e) {
				break;
			}
		}
		
	}
	
}

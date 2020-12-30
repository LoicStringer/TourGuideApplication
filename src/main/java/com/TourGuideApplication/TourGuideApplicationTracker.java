package com.TourGuideApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.TourGuideApplication.service.TrackerService;


public class TourGuideApplicationTracker extends Thread{

	private Logger logger = LoggerFactory.getLogger(TourGuideApplicationTracker.class);
	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private boolean stop = false;

	private TrackerService trackerService;
	
	public TourGuideApplicationTracker(TrackerService trackerService) {
		this.trackerService = trackerService;
		executorService.submit(this);
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
			System.out.println("Begin Tracker. Tracking " + trackerService.getAllUsersIdList().size() + " users.");
			//logger.debug("Begin Tracker. Tracking " + trackerService.getAllUsersIdList().size() + " users.");
			stopWatch.start();
			trackerService.getAllUsersIdList().parallelStream().forEach(id -> trackerService.trackUserLocation(id));
			stopWatch.stop();
			System.out.println("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			//logger.debug("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
			stopWatch.reset();
			try {
				logger.debug("Tracker sleeping");
				TimeUnit.SECONDS.sleep(trackingPollingInterval);
			} catch (InterruptedException e) {
				break;
			}
		}
		
	}
	
	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {
		stop = true;
		executorService.shutdownNow();
	}
	
}

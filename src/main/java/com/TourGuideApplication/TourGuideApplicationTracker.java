package com.TourGuideApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.TourGuideApplication.service.TrackerService;


@Component
public class TourGuideApplicationTracker extends Thread {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	private boolean isRunning;
	private TrackerService trackerService;

	public TourGuideApplicationTracker(TrackerService trackerService,@Value("${tracker.isRunning}")boolean isRunning) {
		this.isRunning = isRunning;
		this.trackerService = trackerService;
		startTracking();
	}

	@Override
	public void run() {
		isRunning = true;
		StopWatch stopWatch = new StopWatch();
		while (isRunning) {
			if (Thread.currentThread().isInterrupted() || isRunning == false) {
				logger.debug("Tracker stopping");
				break;
			}
			System.out.println("Begin Tracker. Tracking " + trackerService.getAllUsersIdList().size() + " users.");
			logger.debug("Begin Tracker. Tracking " + trackerService.getAllUsersIdList().size() + " users.");

			stopWatch.start();
			trackUsers();
			stopWatch.stop();
			System.out.println(
					"Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
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

	/**
	 * Assures to shut down the Tracker thread
	 */

	public void startTracking() {
		if (isRunning==false) {
			executorService.submit(this);
		}
	}

	public void stopTracking() {
		if (isRunning == true)
			executorService.shutdownNow();
		isRunning = false;
	}

	public void trackUsers() {
		trackerService.trackUsers();
	}
}

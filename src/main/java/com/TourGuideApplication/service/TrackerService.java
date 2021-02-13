package com.TourGuideApplication.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.UserProxy;

@Service
public class TrackerService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LocationProxy locationProxy;

	@Autowired
	private UserProxy userProxy;

	@Value("${scheduled.enabled}")
	private boolean scheduledEnabled;

	public TrackerService() {
	}

	@Scheduled(cron = "0 0/5 * * * *")
	public void trackUsers() {
		if (scheduledEnabled == true) {
			log.error("Begin tracking " + getAllUsersIdList().size() + " users.");
			ExecutorService executorService = Executors.newFixedThreadPool(16);
			long start = System.currentTimeMillis();
			getAllUsersIdList().stream().forEach(id -> {
				executorService.execute(new TrackUserTaskRunnable(id));
			});
			executorService.shutdown();
			try {
				executorService.awaitTermination(30, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				log.error("Tracker service has been interrupted" + e.getMessage());
			}
			long time = System.currentTimeMillis() - start;
			log.error("Tracker Time Elapsed: " + time / 1000 + " seconds.");
		}
	}

	public List<UUID> getAllUsersIdList() {
		List<UUID> allUsersIdList = userProxy.getAllUsersIdList();
		return allUsersIdList;
	}

	public void trackUserLocation(UUID userId) {
		locationProxy.getUserLocation(userId);
	}

	public void addUserReward(UUID userId) {
		userProxy.addUserReward(userId);
	}

	public class TrackUserTaskRunnable implements Runnable {

		private UUID userId;

		public TrackUserTaskRunnable(UUID userId) {
			this.userId = userId;
		}

		@Override
		public void run() {
			trackUserLocation(userId);
			addUserReward(userId);
		}

	}
}

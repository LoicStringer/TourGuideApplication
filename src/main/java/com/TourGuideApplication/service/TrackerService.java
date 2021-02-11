package com.TourGuideApplication.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	private boolean isRunning = false;

	public TrackerService(@Value("${isRunning}")boolean isRunning) {
		this.isRunning = isRunning;
	}

	@PostConstruct
	public void cronJobTrackUsers() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		if (isRunning == false) {
			executorService.execute(new Runnable() {
				public void run() {
					isRunning = true;
					while (isRunning) {
						if (Thread.currentThread().isInterrupted() || isRunning == false) {
							log.error("Tracker is stopping");
							break;
						}
						trackUsers();
						try {
							log.error("Tracker is sleeping");
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							log.error("Tracking is stopping");
							executorService.shutdown();
							break;
						}
					}

				}
			});
		}
	}

	public void trackUsers() {
		log.debug("Begin tracking " + getAllUsersIdList().size() + " users.");
		ExecutorService executorService = Executors.newFixedThreadPool(16);
		long start = System.currentTimeMillis();
		getAllUsersIdList().stream().forEach(id -> {
			executorService.execute(new TrackUserTaskRunnable(id));
		});
		executorService.shutdown();
		try {
			executorService.awaitTermination(30, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			log.debug("Tracker service has been interrupted" + e.getMessage());
		}
		long time = System.currentTimeMillis() - start;
		log.debug("Tracker Time Elapsed: " + time / 1000 + " seconds.");
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

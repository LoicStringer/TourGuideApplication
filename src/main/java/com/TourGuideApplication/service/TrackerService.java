package com.TourGuideApplication.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.bean.VisitedLocationBean;

@Service
public class TrackerService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TourGuideApplicationService tourGuideApplicationService;
	
	@Autowired
	private TourGuideApplicationAsyncService tourGuideApplicationAsyncService;
	
	@Value("${scheduled.enabled}")
	private boolean scheduledEnabled;

	private CompletableFuture<VisitedLocationBean> userLocation;
	private CompletableFuture<UserRewardBean> userReward;
	
	public TrackerService() {
	}

	@Scheduled(initialDelay = 0, fixedDelay = 60000)
	public void trackUsers() {
		if (scheduledEnabled == true) {
			log.debug("Begin tracker service for " + tourGuideApplicationService.getAllUsersIdList().size() + " users.");
			long start = System.currentTimeMillis();
			tourGuideApplicationService.getAllUsersIdList().stream().forEach(id -> {
				userLocation = tourGuideApplicationAsyncService.trackUserLocation(id);
				userReward = tourGuideApplicationAsyncService.addUserReward(id);
			});
			CompletableFuture.allOf(userLocation,userReward).join();
			long time = System.currentTimeMillis() - start;
			log.debug("Tracker service Time Elapsed: " + time / 1000 + " seconds.");
		}
	}
}

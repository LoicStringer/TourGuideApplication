package com.TourGuideApplication.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.bean.VisitedLocationBean;

@Service
public class TourGuideApplicationAsyncService {
	
	@Autowired
	private TourGuideApplicationService tourGuideApplicationService;

	@Async
	public CompletableFuture<VisitedLocationBean> trackUserLocation(UUID userId) {
		return(CompletableFuture.completedFuture(tourGuideApplicationService.trackUserLocation(userId)));
	}
	
	@Async
	public CompletableFuture<UserRewardBean> addUserReward(UUID userId) {
		return(CompletableFuture.completedFuture(tourGuideApplicationService.addUserReward(userId)));
	}
	
}

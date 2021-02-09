package com.TourGuideApplication;

import java.util.UUID;

import com.TourGuideApplication.service.TrackerService;

public class TrackUserTaskRunnable implements Runnable{

	private UUID userId;
	private TrackerService trackerService;

	public TrackUserTaskRunnable(UUID userId, TrackerService trackerService) {
		this.userId = userId;
		this.trackerService = trackerService;
	}

	@Override
	public void run() {
		trackerService.trackUserLocation(userId);
		trackerService.addUserReward(userId);
	}

}
	


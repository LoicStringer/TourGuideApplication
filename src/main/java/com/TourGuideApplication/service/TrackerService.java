package com.TourGuideApplication.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.TourGuideApplicationTracker;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.UserProxy;

@Service
public class TrackerService {

	@Autowired
	private LocationProxy locationProxy;
	
	@Autowired
	private UserProxy userProxy;
	
	public TourGuideApplicationTracker tourGuideApplicationTracker;
	
	public TrackerService() {
		tourGuideApplicationTracker = new TourGuideApplicationTracker(this);
	}

	public List<UUID> getAllUsersIdList(){
		List<UUID> allUsersIdList = userProxy.getAllUsersIdList();
		return allUsersIdList;
	}
	
	public void trackUserLocation(UUID userId) {
		locationProxy.getUserLocation(userId);
		userProxy.addUserReward(userId);
	}
	
}

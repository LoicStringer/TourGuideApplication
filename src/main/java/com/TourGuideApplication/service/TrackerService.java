package com.TourGuideApplication.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.UserProxy;


@Service
public class TrackerService {

	@Autowired
	private LocationProxy locationProxy;
	
	@Autowired
	private UserProxy userProxy;
	
	
	
	public TrackerService() {
	}

	public void trackUsers() {
		getAllUsersIdList().parallelStream().forEach(id -> {
			trackUserLocation(id);
			addUserReward(id);
		});
		
	}
	
	public List<UUID> getAllUsersIdList(){
		List<UUID> allUsersIdList = userProxy.getAllUsersIdList();
		return allUsersIdList;
	}
	
	public void trackUserLocation(UUID userId) {
		locationProxy.getUserLocation(userId);
	}
	
	public void addUserReward(UUID userId) {
		userProxy.addUserReward(userId);
	}
	
}

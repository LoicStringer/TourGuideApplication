package com.TourGuideApplication.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
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

	//@Scheduled(cron = "0 0/5 * * * *")
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

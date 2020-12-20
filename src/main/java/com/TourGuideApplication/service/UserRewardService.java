package com.TourGuideApplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.bean.AttractionBean;
import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.model.User;
import com.TourGuideApplication.model.UserReward;
import com.TourGuideApplication.proxy.RewardsProxy;

@Service
public class UserRewardService {
	
	/*
	@Autowired 
	private RewardsProxy rewardsProxy;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserLocationService userLocationService;
	
	@Autowired
	private AttractionService attractionService;
	
	@Value("${rewardAwardingDistance}")
	private int rewardAwardingDistance;
	
	public List<UserReward> generateUserRewardsList (UUID userId){
		List<UserReward> userRewardsList = new ArrayList<UserReward>();
		
		return userRewardsList;
	}
	
	public List<AttractionBean> getAttractionsWithinRewardingDistanceFromUserLocation(UUID userId){
		LocationBean userLocation = userLocationService.getUserLocation(userId).getLocation();
		TreeMap<Double,AttractionBean> attractionsDistancesFromUserLocationMap = attractionService.getDistancesToAttractionsMap(userLocation);
		List<AttractionBean> attractions = attractionsDistancesFromUserLocationMap.entrySet().stream()
				.filter(entry->checkRewardAwardingDistance(entry.getKey()))
				.map(entry->entry.getValue())
				.collect(Collectors.toList());
		return attractions;
	}
	
	
	public int getAttractionRewardPoints(UUID userId, UUID attractionId) {
		return rewardsProxy.getAttractionRewardPoints(userId, attractionId);
	}

	private boolean checkRewardAwardingDistance (double distance) {
		return(distance<rewardAwardingDistance);
	}
	*/
	
}

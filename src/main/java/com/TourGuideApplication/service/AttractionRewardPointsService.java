package com.TourGuideApplication.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.proxy.RewardsProxy;

@Service
public class AttractionRewardPointsService {

	@Autowired 
	private RewardsProxy rewardsProxy;
	
	public int getAttractionRewardPoints(UUID userId, UUID attractionId) {
		return rewardsProxy.getAttractionRewardPoints(userId, attractionId);
	}
	
}

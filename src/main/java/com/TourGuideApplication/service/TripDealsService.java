package com.TourGuideApplication.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.bean.ProviderBean;
import com.TourGuideApplication.dto.TripPricerDto;
import com.TourGuideApplication.model.User;
import com.TourGuideApplication.proxy.TripDealsProxy;

@Service
public class TripDealsService {

	@Autowired
	private TripDealsProxy tripDealsProxy;
	
	@Autowired
	private UserService userService;
	
	public void addTripDealsToUser (List<ProviderBean> tripDealsList, User user) {
		user.setTripDealsList(tripDealsList);
	}
	
	public TripPricerDto buildTripPricerDto (UUID userId) {
		User user = userService.getUser(userId);
		TripPricerDto tripPricerDto = new TripPricerDto();
		tripPricerDto.setId(userId);
		tripPricerDto.setAdultsNumber(user.getPreferences().getNumberOfAdults());
		tripPricerDto.setChildrenNumber(user.getPreferences().getNumberOfChildren());
		tripPricerDto.setTripDuration(user.getPreferences().getTripDuration());
		tripPricerDto.setUserRewardsPointsSum(500);
		//tripPricerDto.setUserRewardsPointsSum(user.getUserRewardsList().stream().mapToInt(ur->ur.getRewardCentralPoints()).sum());
		return tripPricerDto ;
	}
	
	public List<ProviderBean> getTripDealsList (UUID id){
		TripPricerDto tripPricerDto = buildTripPricerDto(id);
		List<ProviderBean> tripDealsList = tripDealsProxy.getTripDeals(tripPricerDto);
		addTripDealsToUser(tripDealsList, userService.getUser(id));
		return tripDealsList;
	}
	
}

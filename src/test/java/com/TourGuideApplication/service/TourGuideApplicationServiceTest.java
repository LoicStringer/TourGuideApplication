package com.TourGuideApplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.TourGuideApplication.bean.AttractionBean;
import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.ProviderBean;
import com.TourGuideApplication.bean.UserBean;
import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.form.UserTripPreferencesForm;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.RewardsProxy;
import com.TourGuideApplication.proxy.UserProxy;
import com.TourGuideApplication.responseentity.ClosestAttractionsList;

@ExtendWith(MockitoExtension.class)
class TourGuideApplicationServiceTest {

	@Mock
	private UserProxy userProxy;
	
	@Mock
	private LocationProxy locationProxy;
	
	@Mock
	private RewardsProxy rewardProxy;
	
	@InjectMocks
	private TourGuideApplicationService tourGuideApplicationService;
	
	@Test
	void getUserTest() {
		UserBean userToGet = new UserBean();
		userToGet.setUserId(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"));
		userToGet.setUserName("Tony");
		
		when(userProxy.getUserBean(any(UUID.class))).thenReturn(userToGet);
		
		assertEquals(userToGet, tourGuideApplicationService.getUserBean(userToGet.getUserId()));
	}

	@Test
	void addUserTest() {
		UserBean userToAdd = new UserBean();
		userToAdd.setUserId(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"));
		userToAdd.setUserName("Tony");
		
		when(userProxy.addUser(any(UserBean.class))).thenReturn(userToAdd);
		
		assertEquals(userToAdd,  tourGuideApplicationService.addUser(userToAdd));
	}
	
	@Test
	void getUserLocationTest() {
		
		UserBean user = new UserBean();
		user.setUserId(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"));
		user.setUserName("Tony");
		
		VisitedLocationBean visitedLocation = new VisitedLocationBean(user.getUserId(),new LocationBean(48.80,2.40),new Date());
		
		when(locationProxy.getUserLocation(any(UUID.class))).thenReturn(visitedLocation);
		
		assertEquals(48.80, tourGuideApplicationService.getUserLocation(user.getUserId()).getLatitude());
	}
	
	@Test
	void getEachUserLastLocationTest() {
		
		Map<UUID,LocationBean> usersLastLocationMap = new HashMap<UUID,LocationBean> ();
		LocationBean location = new LocationBean(48.80,2.40);
		LocationBean locationBis = new LocationBean(2.40,48.80);
		UUID userId = UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5");
		UUID userBisId = UUID.fromString("0b0bb92f-f1f0-4b64-8460-60a896ff67ff");
		usersLastLocationMap.put(userId,location);
		usersLastLocationMap.put(userBisId, locationBis);
		
		when(userProxy.getEachUserLatestLocationList()).thenReturn(usersLastLocationMap);
		
		assertEquals(48.80,tourGuideApplicationService.getEachUserLatestLocationList().get(userId).getLatitude());
		assertEquals(2.40,tourGuideApplicationService.getEachUserLatestLocationList().get(userBisId).getLatitude());
	}
	
	@Test
	void getUserTripDealsTest() {
		UserTripPreferencesForm form = new UserTripPreferencesForm();
		
		List<ProviderBean> userTripDealsList = new ArrayList<ProviderBean>();
		ProviderBean provider = new ProviderBean(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"),"Belleville FairyTail",500.00);
		ProviderBean providerBis = new ProviderBean(UUID.fromString("0b0bb92f-f1f0-4b64-8460-60a896ff67ff"),"Menilmontant LeisurePark",600.00);
		userTripDealsList.add(provider);
		userTripDealsList.add(providerBis);
		
		when(userProxy.getUserTripDeals(any(UUID.class))).thenReturn(userTripDealsList);
		
		assertEquals("Belleville FairyTail",tourGuideApplicationService.getUserTripDeals(UUID.randomUUID(),form).get(0).getProviderName());
	}
	
	@Test
	void getUseRewardsListTest() {
		
		List<UserRewardBean> userRewardsList  =new ArrayList<UserRewardBean>();
		UserRewardBean userReward = new UserRewardBean();
		userReward.setRewardCentralPoints(5000);
		UserRewardBean userRewardBis = new UserRewardBean();
		userRewardBis.setRewardCentralPoints(6000);
		userRewardsList.add(userReward);
		userRewardsList.add(userRewardBis);
		
		when(userProxy.getUserRewardList(any(UUID.class))).thenReturn(userRewardsList);
		
		assertEquals(5000,tourGuideApplicationService.getUserRewardsList(UUID.randomUUID()).get(0).getRewardCentralPoints());
	}
	
	@Test
	void getTheUserClosestAttractionsListTest() {
		UserBean user = new UserBean();
		user.setUserId(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"));
		VisitedLocationBean visitedLocation = new VisitedLocationBean(user.getUserId(),new LocationBean(48.80,2.40),new Date());
		when(locationProxy.getUserLocation(user.getUserId())).thenReturn(visitedLocation);
		
		TreeMap<Double,AttractionBean> distancesToAttraction = new TreeMap<Double,AttractionBean>();
		AttractionBean attraction = new AttractionBean(user.getUserId(),"Buttes Chaumont","Paris","France",48.8809,2.3828);
		distancesToAttraction.put(2.50,attraction);
		for(int i=0 ;i<7; i++) {
			distancesToAttraction.put(3.50,attraction);
		}
		
		when(locationProxy.getDistancesToAttractions(visitedLocation.getLocation())).thenReturn(distancesToAttraction);
		//when(rewardProxy.getAttractionRewardPoints(user.getUserId(), attraction.getAttractionId())).thenReturn(5000);
		
		ClosestAttractionsList closestAttractionsList = tourGuideApplicationService.getTheUserClosestAttractionsList(user.getUserId());
		
		System.out.println(closestAttractionsList);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

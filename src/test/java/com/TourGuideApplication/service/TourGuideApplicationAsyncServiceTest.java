package com.TourGuideApplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.UserBean;
import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.proxy.LocationProxy;

@ExtendWith(MockitoExtension.class)
class TourGuideApplicationAsyncServiceTest {

	@InjectMocks
	private TourGuideApplicationAsyncService tourGuideApplicationAsyncService;
	
	@Mock
	private TourGuideApplicationService tourGuideApplicationService;
	
	@Mock
	private LocationProxy locationProxy;
	
	@Test
	void trackUserLocationAsyncMethodTest() throws InterruptedException, ExecutionException {
		UserBean user = new UserBean();
		user.setUserId(UUID.fromString("a5ef098f-f6f9-469a-a96c-ac8b168cfec0"));
		VisitedLocationBean userLocation = new VisitedLocationBean(user.getUserId(),new LocationBean(48,2), new Date());
		when(tourGuideApplicationService.trackUserLocation(user.getUserId())).thenReturn(userLocation);
		assertEquals(48, tourGuideApplicationAsyncService.trackUserLocation(user.getUserId()).get().getLocation().getLatitude());
	}

	@Test
	void addUserRewardAsyncMethodTest() throws InterruptedException, ExecutionException {
		UserBean user = new UserBean();
		user.setUserId(UUID.fromString("a5ef098f-f6f9-469a-a96c-ac8b168cfec0"));
		UserRewardBean userReward = new UserRewardBean();
		userReward.setRewardCentralPoints(500);
		when(tourGuideApplicationService.addUserReward(user.getUserId())).thenReturn(userReward);
		assertEquals(500, tourGuideApplicationAsyncService.addUserReward(user.getUserId()).get().getRewardCentralPoints());
	}
	
}

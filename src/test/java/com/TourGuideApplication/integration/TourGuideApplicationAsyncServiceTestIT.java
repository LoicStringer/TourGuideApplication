package com.TourGuideApplication.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.UserBean;
import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.UserProxy;
import com.TourGuideApplication.service.TourGuideApplicationAsyncService;

@ActiveProfiles("test")
@SpringBootTest
class TourGuideApplicationAsyncServiceTestIT {
	
	@Autowired
	private TourGuideApplicationAsyncService tourGuideApplicationAsyncService;
	
	@MockBean
	private LocationProxy locationProxy;
	
	@MockBean
	private UserProxy userProxy;
	
	@Test
	void trackUserLocationAsyncMethodTest() throws InterruptedException, ExecutionException {
		
		UserBean user = new UserBean();
		user.setUserId(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"));
		VisitedLocationBean visitedLocation = new VisitedLocationBean(user.getUserId(), new LocationBean(48.80, 2.40),
				new Date());
		when(locationProxy.getUserLocation(any(UUID.class))).thenReturn(visitedLocation);
		
		assertEquals(48.80, tourGuideApplicationAsyncService.trackUserLocation(user.getUserId()).get().getLocation().getLatitude());
	}

	@Test
	void addUserRewardAsyncMethodTest() throws InterruptedException, ExecutionException {
		UserBean user = new UserBean();
		user.setUserId(UUID.fromString("a5ef098f-f6f9-469a-a96c-ac8b168cfec0"));
		UserRewardBean userReward = new UserRewardBean();
		userReward.setRewardCentralPoints(500);
		
		when(userProxy.addUserReward(user.getUserId())).thenReturn(userReward);
		
		assertEquals(500, tourGuideApplicationAsyncService.addUserReward(user.getUserId()).get().getRewardCentralPoints());
	}
}

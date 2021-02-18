package com.TourGuideApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.proxy.UserProxy;
import com.TourGuideApplication.service.TourGuideApplicationAsyncService;
import com.TourGuideApplication.service.TourGuideApplicationService;

@ActiveProfiles("test")
@SpringBootTest
class PerformanceTestIT {

	@Autowired
	private TourGuideApplicationAsyncService tourGuideApplicationAsyncService;
	
	@Autowired
	private TourGuideApplicationService tourGuideApplicationService;
	
	@Autowired
	private UserProxy userProxy;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private CompletableFuture<VisitedLocationBean> userLocationCompletableFuture ;
	private CompletableFuture<UserRewardBean> userRewardCompletableFuture ;
	
	@Test
	void trackUserLocationPerformanceTest() throws IOException {
		StopWatch stopWatch = new StopWatch();
		int[] usersNumberArray = new int[] { 100, 1000, 5000, 10000, 50000, 100000 };
		for (int i : usersNumberArray) {
			stopWatch.reset();
			userProxy.performanceTestUsersGeneration(i);
			log.debug("Begin tracking " + i + " users.");
			stopWatch.start();
			tourGuideApplicationService.getAllUsersIdList().stream().forEach(id -> {
				userLocationCompletableFuture = tourGuideApplicationAsyncService.trackUserLocation(id);
			});
			CompletableFuture.allOf(userLocationCompletableFuture).join();
			stopWatch.stop();

			log.debug("Tracking Time Elapsed: " + stopWatch.getTime(TimeUnit.SECONDS) + " seconds.");
			assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		}

	}

	@Test
	void addUserRewardPerformanceTest() {
		StopWatch stopWatch = new StopWatch();
		int[] usersNumberArray = new int[] { 100, 1000, 10000, 100000 };
		for (int i : usersNumberArray) {
			stopWatch.reset();
			userProxy.performanceTestUsersGeneration(i);
			log.debug("Begin adding rewards for " + i + " users.");
			stopWatch.start();
			tourGuideApplicationService.getAllUsersIdList().stream().forEach(id -> {
				userRewardCompletableFuture = tourGuideApplicationAsyncService.addUserReward(id);
			});
			CompletableFuture.allOf(userRewardCompletableFuture).join();
			stopWatch.stop();
			log.debug("Adding Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		}
	}

}

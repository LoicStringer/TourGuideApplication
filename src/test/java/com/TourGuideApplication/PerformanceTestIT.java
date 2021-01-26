package com.TourGuideApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.TourGuideApplication.proxy.UserProxy;
import com.TourGuideApplication.service.TrackerService;

@ActiveProfiles("test")
@SpringBootTest
class PerformanceTestIT {

	@Autowired
	private UserProxy userProxy;

	@Autowired
	private TrackerService trackerService;
	
	
	@Test
	void trackUserPerformanceTest() {
		StopWatch stopWatch = new StopWatch();
		int[] usersNumberArray = new int[] {100,1000,5000,10000,50000,100000};
		for (int i : usersNumberArray) {
			stopWatch.reset();
			userProxy.performanceTestUsersGeneration(i);
			stopWatch.start();
			trackerService.trackUsers();
			stopWatch.stop();
			System.out.println("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		}
	}
	
	@Disabled
	@Test
	void addUserRewardPerformanceTest() {
		StopWatch stopWatch = new StopWatch();
		int[] usersNumberArray = new int[] {100,1000,5000,10000,50000,100000};
		for (int i : usersNumberArray) {
			stopWatch.reset();
			userProxy.performanceTestUsersGeneration(i);
			stopWatch.start();
			trackerService.getAllUsersIdList().parallelStream().forEach(id->{
				trackerService.addUserReward(id);
			});
			stopWatch.stop();
			System.out.println("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		}
	}

}

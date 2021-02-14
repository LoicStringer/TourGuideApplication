package com.TourGuideApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	void trackUserPerformanceTest() throws IOException {
		StopWatch stopWatch = new StopWatch();
		int[] usersNumberArray = new int[] { 100, 1000, 5000, 10000, 50000, 100000 };
		for (int i : usersNumberArray) {
			stopWatch.reset();
			userProxy.performanceTestUsersGeneration(i);
			log.error("Begin tracking " + i + " users.");
			ExecutorService executorService = Executors.newFixedThreadPool(16);
			stopWatch.start();
			trackerService.getAllUsersIdList().stream().forEach(id -> {
				executorService.execute(trackerService.new TrackUserTaskRunnable(id));
			});
			executorService.shutdown();
			try {
				executorService.awaitTermination(30, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stopWatch.stop();

			log.error("Tracker Time Elapsed: " + stopWatch.getTime(TimeUnit.SECONDS) + " seconds.");
			assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		}

	}

	@Disabled
	@Test
	void addUserRewardPerformanceTest() {
		StopWatch stopWatch = new StopWatch();
		int[] usersNumberArray = new int[] { 100, 1000, 5000, 10000, 50000, 100000 };
		for (int i : usersNumberArray) {
			stopWatch.reset();
			userProxy.performanceTestUsersGeneration(i);
			stopWatch.start();
			trackerService.getAllUsersIdList().parallelStream().forEach(id -> {
				trackerService.addUserReward(id);
			});
			stopWatch.stop();
			log.error("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		}
	}

}

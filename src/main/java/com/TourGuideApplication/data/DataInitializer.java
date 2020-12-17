package com.TourGuideApplication.data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.model.User;

@Component
public class DataInitializer {
	
	/*
	private Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
	public void initializeTestsUsers(int testUserNumber) {
		IntStream.range(0, testUserNumber).forEach(i -> {
			String userName = "testlUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);

			DataContainer.usersData.put(user.getUserId(), user);
		});
		logger.debug("Created " + testUserNumber + " test users.");
	}

	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i -> {
			user.getVisitedLocationsList().add(new VisitedLocationBean(user.getUserId(),
					new LocationBean(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}

	private double generateRandomLongitude() {
		double leftLimit = -180;
		double rightLimit = 180;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
		double rightLimit = 85.05112878;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
		return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}
	
	*/
}

package com.TourGuideApplication.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.data.DataContainer;
import com.TourGuideApplication.model.User;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.responseentity.UsersLocationsList;
import com.TourGuideApplication.responseentity.UsersLocationsList.UserLocation;

@Service
public class UserLocationService {

	@Autowired
	private LocationProxy locationProxy;

	@Autowired
	private UserService userService;

	public VisitedLocationBean addUserVisitedLocation(VisitedLocationBean visitedLocation, UUID userId) {
		userService.getUser(userId).getVisitedLocationsList().add(visitedLocation);
		return visitedLocation;
	}

	public List<VisitedLocationBean> getUserVisitedLocationHistory(User user) {
		return user.getVisitedLocationsList();
	}

	public VisitedLocationBean getUserLastVisitedLocation(UUID userId) {
		VisitedLocationBean visitedLocation = new VisitedLocationBean();
		visitedLocation = null;
		if (userService.getUser(userId).getVisitedLocationsList().size() > 0)
			visitedLocation = userService.getUser(userId).getVisitedLocationsList()
					.get((userService.getUser(userId).getVisitedLocationsList().size() - 1));
		return visitedLocation;
	}

	public void clearUserVisitedLocation(User user) {
		user.getVisitedLocationsList().clear();
	}

	public VisitedLocationBean getUserLocation(UUID userId) {
		VisitedLocationBean visitedLocation = getUserLastVisitedLocation(userId);
		if (visitedLocation == null)
			visitedLocation = trackUserLocation(userId);
		return visitedLocation;
	}

	public VisitedLocationBean trackUserLocation(UUID userId) {
		VisitedLocationBean visitedLocation = locationProxy.getUserLocation(userId);
		addUserVisitedLocation(visitedLocation, userId);
		// rewardsService.generateUserRewards(user);
		return visitedLocation;
	}

	public UsersLocationsList getEachUsersLocationsList() {
		UsersLocationsList usersLocationsList = new UsersLocationsList();
		DataContainer.usersData.entrySet().forEach(entry -> {
			UserLocation userLocation = new UsersLocationsList().new UserLocation(entry.getKey(),
					getUserLastVisitedLocation(entry.getKey()).getLocation());
			usersLocationsList.addUserLocation(userLocation);
		});
		return usersLocationsList;
	}
	
	
	
}

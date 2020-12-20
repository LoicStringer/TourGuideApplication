package com.TourGuideApplication.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TourGuideApplication.data.DataContainer;
import com.TourGuideApplication.model.User;

@Service
public class UserService {
	
	/*
	public User getUser(UUID userId) {
		return DataContainer.usersData.get(userId);
	}
	
	public List<User> getAllUsers() {
		return DataContainer.usersData.values().stream().collect(Collectors.toList());
	}
	
	public User addUser(User user) {
		if(checkIfUserAlreadyRegistered(user.getUserId())==false) {
			DataContainer.usersData.put(user.getUserId(), user);
		}
		return user;
	}
	
	public boolean checkIfUserAlreadyRegistered(UUID userId) {
		boolean isRegistered = false;
		if(DataContainer.usersData.containsKey(userId))
			isRegistered = true;
		return isRegistered;
	}

	public void clearAllUsersData() {
		DataContainer.clearUsersData();
	}
	*/
	
}

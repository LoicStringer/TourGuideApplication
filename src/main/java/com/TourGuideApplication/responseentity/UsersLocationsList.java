package com.TourGuideApplication.responseentity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.TourGuideApplication.bean.LocationBean;

public class UsersLocationsList {

	private List<UserLocation> usersLocationsList;

	public UsersLocationsList() {
		this.usersLocationsList = new ArrayList<UserLocation>();
	}

	public void addUserLocation(UserLocation userLocation) {
		usersLocationsList.add(userLocation);
	}
	
	public List<UserLocation> getUsersLocationsList() {
		return usersLocationsList;
	}

	public void setUsersLocationsList(List<UserLocation> usersLocationsList) {
		this.usersLocationsList = usersLocationsList;
	}

	@Override
	public String toString() {
		return "UsersLocationsList [usersLocationsList=" + usersLocationsList + "]";
	}

	public class UserLocation {

		private UUID userId;
		private LocationBean location;

		public UserLocation(UUID userId, LocationBean location) {
			super();
			this.userId = userId;
			this.location = location;
		}

		public UUID getUserId() {
			return userId;
		}

		public void setUserId(UUID userId) {
			this.userId = userId;
		}

		public LocationBean getLocation() {
			return location;
		}

		public void setLocation(LocationBean location) {
			this.location = location;
		}

		@Override
		public String toString() {
			return "UserLocation [userId=" + userId + ", location=" + location + "]";
		}

	}

}

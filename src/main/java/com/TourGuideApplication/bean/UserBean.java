package com.TourGuideApplication.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



public class UserBean {

	private UUID userId;
	private String userName;
	private String phoneNumber;
	private String emailAddress;
	private Date latestLocationTimestamp;
	private List<VisitedLocationBean> visitedLocationsList = new ArrayList<>();
	private List<UserRewardBean> userRewardsList = new ArrayList<>();
	private UserTripPreferencesBean preferences = new UserTripPreferencesBean();
	private List<ProviderBean> tripDealsList = new ArrayList<>();
	
	public UserBean() {
	}

	public UserBean(UUID userId, String userName, String phoneNumber, String emailAddress) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getLatestLocationTimestamp() {
		return latestLocationTimestamp;
	}

	public void setLatestLocationTimestamp(Date latestLocationTimestamp) {
		this.latestLocationTimestamp = latestLocationTimestamp;
	}

	public List<VisitedLocationBean> getVisitedLocationsList() {
		return visitedLocationsList;
	}

	public void setVisitedLocationsList(List<VisitedLocationBean> visitedLocationsList) {
		this.visitedLocationsList = visitedLocationsList;
	}

	public List<UserRewardBean> getUserRewardsList() {
		return userRewardsList;
	}

	public void setUserRewardsList(List<UserRewardBean> userRewardsList) {
		this.userRewardsList = userRewardsList;
	}

	public UserTripPreferencesBean getPreferences() {
		return preferences;
	}

	public void setPreferences(UserTripPreferencesBean preferences) {
		this.preferences = preferences;
	}

	public List<ProviderBean> getTripDealsList() {
		return tripDealsList;
	}

	public void setTripDealsList(List<ProviderBean> tripDealsList) {
		this.tripDealsList = tripDealsList;
	}

	
	
	
}

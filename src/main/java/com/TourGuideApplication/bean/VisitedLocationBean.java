package com.TourGuideApplication.bean;

import java.util.Date;
import java.util.UUID;

public class VisitedLocationBean {
	
	private UUID userId;
	private LocationBean location;
	private Date timeVisited;
	
	public VisitedLocationBean() {
	}

	public VisitedLocationBean(UUID userId, LocationBean location, Date timeVisited) {
		this.userId = userId;
		this.location = location;
		this.timeVisited = timeVisited;
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

	public Date getTimeVisited() {
		return timeVisited;
	}

	public void setTimeVisited(Date timeVisited) {
		this.timeVisited = timeVisited;
	}

	@Override
	public String toString() {
		return "VisitedLocationBean [userId=" + userId + ", location=" + location + ", timeVisited=" + timeVisited
				+ "]";
	}
	
	
}

package com.TourGuideApplication.responseentity;

import java.util.ArrayList;
import java.util.List;

import com.TourGuideApplication.bean.LocationBean;

public class ClosestAttractionsList {
	
	private List<ClosestAttraction> closestAttractionsList;
	
	public ClosestAttractionsList() {
		this.closestAttractionsList = new ArrayList<ClosestAttraction>();
	}

	public void addClosestAttraction(ClosestAttraction closestAttraction) {
		closestAttractionsList.add(closestAttraction);
	}
	
	public List<ClosestAttraction> getClosestAttractionsList() {
		return closestAttractionsList;
	}

	public void setClosestAttractionsList(List<ClosestAttraction> closestAttractionsList) {
		this.closestAttractionsList = closestAttractionsList;
	}

	@Override
	public String toString() {
		return "ClosestAttractionsList [closestAttractionsList=" + closestAttractionsList + "]";
	}

	public class ClosestAttraction{
		
		private String attractionName;
		private LocationBean attractionLocation;
		private LocationBean userLocation;
		private double distanceInMiles;
		private int rewardPoints;
		
		public ClosestAttraction() {
		}

		public String getAttractionName() {
			return attractionName;
		}

		public void setAttractionName(String attractionName) {
			this.attractionName = attractionName;
		}

		public LocationBean getAttractionLocation() {
			return attractionLocation;
		}

		public void setAttractionLocation(LocationBean attractionLocation) {
			this.attractionLocation = attractionLocation;
		}

		public LocationBean getUserLocation() {
			return userLocation;
		}

		public void setUserLocation(LocationBean userLocation) {
			this.userLocation = userLocation;
		}

		public double getDistanceInMiles() {
			return distanceInMiles;
		}

		public void setDistanceInMiles(double distanceInMiles) {
			this.distanceInMiles = distanceInMiles;
		}

		public int getRewardPoints() {
			return rewardPoints;
		}

		public void setRewardPoints(int rewardPoints) {
			this.rewardPoints = rewardPoints;
		}

		@Override
		public String toString() {
			return "ClosestAttraction [attractionName=" + attractionName + ", distanceInMiles=" + distanceInMiles
					+ ", rewardPoints=" + rewardPoints + "]";
		}
		
		
		
	}

}

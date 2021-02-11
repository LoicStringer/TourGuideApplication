package com.TourGuideApplication.responseentity;

import java.util.ArrayList;
import java.util.List;

import com.TourGuideApplication.bean.LocationBean;

public class ClosestAttractionsList {
	
	private List<AttractionDetails> attractionDetailsList;
	
	public ClosestAttractionsList() {
		this.attractionDetailsList = new ArrayList<AttractionDetails>();
	}
	
	public List<AttractionDetails> getAttractionDetailsList() {
		return attractionDetailsList;
	}

	public void setAttractionDetailsList(List<AttractionDetails> attractionDetailsList) {
		this.attractionDetailsList = attractionDetailsList;
	}

	@Override
	public String toString() {
		return "ClosestAttractionsList [attractionDetailsList=" + attractionDetailsList + "]";
	}

	public class AttractionDetails{
		
		private String attractionName;
		private LocationBean attractionLocation;
		private LocationBean userLocation;
		private double distanceInMiles;
		private int rewardPoints;
		
		public AttractionDetails() {
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
			return "AttractionDetails [attractionName=" + attractionName + ", distanceInMiles=" + distanceInMiles
					+ ", rewardPoints=" + rewardPoints + "]";
		}
		
		
		
	}

}

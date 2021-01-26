package com.TourGuideApplication.bean;

import java.util.UUID;

public class ProviderBean {

	private UUID tripId;
	private String providerName;
	private double price;
	
	public ProviderBean() {
	}

	public ProviderBean(UUID tripId, String providerName, double price) {
		super();
		this.tripId = tripId;
		this.providerName = providerName;
		this.price = price;
	}

	public UUID getTripId() {
		return tripId;
	}

	public void setTripId(UUID tripId) {
		this.tripId = tripId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProviderBean [tripId=" + tripId + ", providerName=" + providerName + ", price=" + price + "]";
	}
	
	
	
}

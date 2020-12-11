package com.TourGuideApplication.model;

import com.TourGuideApplication.bean.AttractionBean;
import com.TourGuideApplication.bean.VisitedLocationBean;

public class UserReward {

	private VisitedLocationBean visitedLocationBean;
	private AttractionBean attractionbean;
	private int rewardCentralPoints;
	
	public UserReward() {
	}

	public UserReward(VisitedLocationBean visitedLocationBean, AttractionBean attractionbean,
			int rewardCentralPoints) {
		super();
		this.visitedLocationBean = visitedLocationBean;
		this.attractionbean = attractionbean;
		this.rewardCentralPoints = rewardCentralPoints;
	}

	public VisitedLocationBean getVisitedLocationBean() {
		return visitedLocationBean;
	}

	public void setVisitedLocationBean(VisitedLocationBean visitedLocationBean) {
		this.visitedLocationBean = visitedLocationBean;
	}

	public AttractionBean getAttractionbean() {
		return attractionbean;
	}

	public void setAttractionbean(AttractionBean attractionbean) {
		this.attractionbean = attractionbean;
	}

	public int getRewardCentralPoints() {
		return rewardCentralPoints;
	}

	public void setRewardCentralPoints(int rewardCentralPoints) {
		this.rewardCentralPoints = rewardCentralPoints;
	}
	
	
}

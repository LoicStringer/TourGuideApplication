package com.TourGuideApplication.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.bean.AttractionBean;
import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.ProviderBean;
import com.TourGuideApplication.bean.UserBean;
import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.form.UserTripPreferencesForm;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.RewardsProxy;
import com.TourGuideApplication.proxy.UserProxy;
import com.TourGuideApplication.responseentity.ClosestAttractionsList;
import com.TourGuideApplication.responseentity.ClosestAttractionsList.AttractionDetails;

@Service
public class TourGuideApplicationService {

	@Autowired
	private LocationProxy locationProxy;
	
	@Autowired
	private RewardsProxy rewardsProxy;
	
	@Autowired
	private UserProxy userProxy;
	
	@Value("${closestAttractionsRetrieved.number}")
	private int attractionRetrievedNumber;
	
	public UserBean getUserBean(UUID userId) {
		return userProxy.getUserBean(userId);
	}
	
	public LocationBean getUserLocation(UUID userId) {
		return locationProxy.getUserLocation(userId).getLocation();
	}

	public Map<UUID,LocationBean> getEachUserLatestLocationList() {
		return userProxy.getEachUserLatestLocationList();
	}
	
	public List<ProviderBean> getUserTripDeals(UUID userId, UserTripPreferencesForm userTripPreferencesForm) {
		userProxy.addUserTripPreferences(userId,userTripPreferencesForm);
		return userProxy.getUserTripDeals (userId);
	}
	
	public List<UserRewardBean> getUserRewardsList(UUID userId) {
		List<UserRewardBean> userRewardsList = userProxy.getUserRewardList(userId);
		return userRewardsList;
	}
	
	public ClosestAttractionsList getTheUserClosestAttractionsList(UUID userId) {
		ClosestAttractionsList userClosestAttractionsList = new ClosestAttractionsList();
		VisitedLocationBean userLocation = locationProxy.getUserLocation(userId);
		TreeMap<Double,AttractionBean> distancesToAttractions = getTheXFirstEntries(locationProxy.getDistancesToAttractions(userLocation.getLocation()));
		distancesToAttractions.forEach((k,v)->{
			AttractionDetails attractionDetails = new ClosestAttractionsList().new AttractionDetails();
			attractionDetails.setAttractionName(v.getAttractionName());
			attractionDetails.setAttractionLocation(new LocationBean (v.getLatitude(),v.getLongitude()));
			attractionDetails.setUserLocation(userLocation.getLocation());
			attractionDetails.setDistanceInMiles(k);
			attractionDetails.setRewardPoints(rewardsProxy.getAttractionRewardPoints(userId, v.getAttractionId()));
			userClosestAttractionsList.getAttractionDetailsList().add(attractionDetails);
		});
		return userClosestAttractionsList;
	}
	
	private <K,V> TreeMap<K,V> getTheXFirstEntries (TreeMap<K,V> treeMap ){
		TreeMap<K,V> filteredTreeMap = new TreeMap<K,V>();
		IntStream.range(0,attractionRetrievedNumber).forEach(i->{
			Map.Entry<K,V> entryToGet = treeMap.pollFirstEntry();
					filteredTreeMap.put(entryToGet.getKey(),entryToGet.getValue());
				});
		return filteredTreeMap;
	}
	
}

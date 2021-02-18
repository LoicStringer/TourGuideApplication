package com.TourGuideApplication.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LocationProxy locationProxy;
	
	@Autowired
	private RewardsProxy rewardsProxy;
	
	@Autowired
	private UserProxy userProxy;
	
	@Value("${closestAttractionsRetrieved.number}")
	private int attractionRetrievedNumber;
	
	public TourGuideApplicationService() {
	}

	public TourGuideApplicationService(int attractionRetrievedNumber) {
		this.attractionRetrievedNumber = attractionRetrievedNumber;
	}

	public UserBean getUserBean(UUID userId) {
		return userProxy.getUserBean(userId);
	}
	
	public UserBean addUser(UserBean user) {
		userProxy.addUser(user);
		return user;
	}
	
	public List<UUID> getAllUsersIdList() {
		List<UUID> allUsersIdList = userProxy.getAllUsersIdList();
		return allUsersIdList;
	}

	
	public VisitedLocationBean trackUserLocation(UUID userId) {
		return(locationProxy.getUserLocation(userId));
	}
	
	public UserRewardBean addUserReward(UUID userId) {
		return(userProxy.addUserReward(userId));
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
		log.debug("Building the user closest attractions detailed list according to the predefined attractions number to retrieve.");
		ClosestAttractionsList userClosestAttractionsList = new ClosestAttractionsList();
		VisitedLocationBean userLocation = locationProxy.getUserLocation(userId);
		TreeMap<Double,AttractionBean> filteredDistancesToAttractions = getTheXFirstEntries(locationProxy.getDistancesToAttractions(userLocation.getLocation()));
		List<AttractionDetails> attractionDetailsList = filteredDistancesToAttractions.entrySet().parallelStream().map(entry->{
			AttractionDetails attractionDetails = new ClosestAttractionsList().new AttractionDetails();
			attractionDetails.setAttractionName(entry.getValue().getAttractionName());
			attractionDetails.setAttractionLocation(new LocationBean (entry.getValue().getLatitude(),entry.getValue().getLongitude()));
			attractionDetails.setUserLocation(userLocation.getLocation());
			attractionDetails.setDistanceInMiles(entry.getKey());
			attractionDetails.setRewardPoints(rewardsProxy.getAttractionRewardPoints(userId, entry.getValue().getAttractionId()));
			return attractionDetails;
		}).collect(Collectors.toList());
		userClosestAttractionsList.setAttractionDetailsList(attractionDetailsList);
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

	public int getAttractionRetrievedNumber() {
		return attractionRetrievedNumber;
	}

	public void setAttractionRetrievedNumber(int attractionRetrievedNumber) {
		this.attractionRetrievedNumber = attractionRetrievedNumber;
	}
	
}

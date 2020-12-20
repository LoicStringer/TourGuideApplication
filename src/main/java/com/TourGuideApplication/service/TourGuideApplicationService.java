package com.TourGuideApplication.service;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.bean.AttractionBean;
import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.RewardsProxy;
import com.TourGuideApplication.responseentity.ClosestAttractionsList;
import com.TourGuideApplication.responseentity.ClosestAttractionsList.AttractionDetails;

@Service
public class TourGuideApplicationService {

	@Autowired
	private LocationProxy locationProxy;
	
	@Autowired
	private RewardsProxy rewardsProxy;
	
	@Value("${closestAttractionsRetrieved.number}")
	private int attractionRetrievedNumber;
	
	public ClosestAttractionsList getTheUserClosestAttractionsList(UUID userId) {
		ClosestAttractionsList userClosestAttractionsList = new ClosestAttractionsList();
		LocationBean userLocation = locationProxy.getUserLocation(userId);
		TreeMap<Double,AttractionBean> distancesToAttractions = getTheXFirstEntries(locationProxy.getDistancesToAttractions(userLocation));
		distancesToAttractions.forEach((k,v)->{
			AttractionDetails attractionDetails = new ClosestAttractionsList().new AttractionDetails();
			attractionDetails.setAttractionName(v.getAttractionName());
			attractionDetails.setAttractionLocation(new LocationBean (v.getLatitude(),v.getLongitude()));
			attractionDetails.setUserLocation(userLocation);
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

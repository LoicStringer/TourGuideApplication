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
import com.TourGuideApplication.responseentity.ClosestAttractionsList;
import com.TourGuideApplication.responseentity.ClosestAttractionsList.ClosestAttraction;

@Service
public class AttractionLocationService {
	
	@Autowired
	private UserLocationService userLocationService;
	
	@Autowired
	private AttractionRewardPointsService attractionRewardPointsService;
	
	@Autowired
	private LocationProxy locationProxy;
	
	@Value("${closestAttractionsRetrieved.number}")
	private int attractionRetrievedNumber;
	
	public ClosestAttractionsList getTheUserClosestAttractions(UUID userId) {
		ClosestAttractionsList userClosestAttractionsList = new ClosestAttractionsList();
		LocationBean userLocation = userLocationService.getUserLocation(userId).getLocation();
		TreeMap<Double,AttractionBean> distancesToAttractions = getTheXFirstEntries(locationProxy.getDistancesToAttractions(userLocation));
		distancesToAttractions.forEach((k,v)->{
			ClosestAttraction userClosestAttraction = new ClosestAttractionsList().new ClosestAttraction();
			userClosestAttraction.setAttractionName(v.getAttractionName());
			userClosestAttraction.setAttractionLocation(new LocationBean (v.getLatitude(),v.getLongitude()));
			userClosestAttraction.setUserLocation(userLocation);
			userClosestAttraction.setDistanceInMiles(k);
			userClosestAttraction.setRewardPoints(attractionRewardPointsService.getAttractionRewardPoints(userId, v.getAttractionId()));
			userClosestAttractionsList.addClosestAttraction(userClosestAttraction);
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

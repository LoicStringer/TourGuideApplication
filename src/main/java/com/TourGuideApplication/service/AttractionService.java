package com.TourGuideApplication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.bean.AttractionBean;
import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.model.User;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.responseentity.ClosestAttractionsList;
import com.TourGuideApplication.responseentity.ClosestAttractionsList.AttractionsList;

@Service
public class AttractionService {
	

	
	@Autowired
	private UserRewardService userRewardService;
	
	@Autowired
	private LocationProxy locationProxy;
	
	@Value("${closestAttractionsRetrieved.number}")
	private int attractionRetrievedNumber;
	
	public List<AttractionBean> getAllAttractions(){
		List<AttractionBean> attractionsList = locationProxy.getAttractions();
		return attractionsList;
	}
	
	public List<AttractionBean> getUnvisitedAttraction(User user){
		List<AttractionBean> unvisitedAttractionsList = new ArrayList<>(getAllAttractions());
		unvisitedAttractionsList.removeAll(getVisitedAttractions(user));
		return unvisitedAttractionsList;
	}
	
	public TreeMap<Double,AttractionBean> getDistancesToAttractionsMap(LocationBean locationBean){
		TreeMap<Double,AttractionBean> distancesToAttractionsMap = locationProxy.getDistancesToAttractions(locationBean);
		return distancesToAttractionsMap;
	}
	
	
	
	private List<AttractionBean> getVisitedAttractions (User user){
		List<AttractionBean> VisitedAttractions = user.getUserRewardsList().stream()
				.map(ur->ur.getAttractionbean())
				.collect(Collectors.toList());
		return VisitedAttractions;
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

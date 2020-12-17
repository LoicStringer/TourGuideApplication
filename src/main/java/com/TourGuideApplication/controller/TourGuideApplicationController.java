package com.TourGuideApplication.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.ProviderBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.form.UserTripPreferencesForm;
import com.TourGuideApplication.model.User;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.UserProxy;
import com.TourGuideApplication.responseentity.ClosestAttractionsList;
import com.TourGuideApplication.service.AttractionService;
import com.TourGuideApplication.service.TripDealsService;
import com.TourGuideApplication.service.UserLocationService;
import com.TourGuideApplication.service.UserRewardService;
import com.TourGuideApplication.service.UserService;
import com.TourGuideApplication.service.UserTripPreferencesService;

@RestController
public class TourGuideApplicationController {
	
	@Autowired
	private UserProxy userProxy;
	
	@Autowired
	private LocationProxy locationProxy;
	
	@Autowired
	private UserLocationService userLocationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRewardService userRewardService;
	
	@Autowired
	private AttractionService attractionLocationservice;
	
	@Autowired
	private TripDealsService tripdealsService;
	
	@Autowired
	private UserTripPreferencesService userTripPreferencesService;
	
	@GetMapping("/users/{userId}/location")
	public ResponseEntity<LocationBean> getUserLocation(@PathVariable UUID userId){
		
		VisitedLocationBean visitedLocationBean = locationProxy.getUserLocation(userId);
		LocationBean location = visitedLocationBean.getLocation();
		return ResponseEntity.ok(location);
	}
	
	@GetMapping("/users/visited-locations/latest")
	public ResponseEntity<Map<UUID,LocationBean>> getAllUsersLatestLocation(){
		return ResponseEntity.ok(userProxy.getEachUserLatestLocationList());
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> addUser(@RequestBody User user){
		return ResponseEntity.ok(userService.addUser(user));
	}
	
	@GetMapping("users/{userId}/attractions")
	public ResponseEntity<ClosestAttractionsList> getTheUserClosestAttractions(@PathVariable UUID userId){
		return ResponseEntity.ok(attractionLocationservice.getTheUserClosestAttractions(userId));
	}
	
	@PostMapping("/users/{userId}/trip-preferences")
	public ResponseEntity<UserTripPreferencesForm> addMyPreferences 
	(@PathVariable UUID userId,@RequestBody UserTripPreferencesForm userTripPreferencesForm){
		return ResponseEntity.ok(userTripPreferencesService.addUserTripPreferences(userId, userTripPreferencesForm));
	}
	
	
	@GetMapping("users/{id}/trip-deals")
	public ResponseEntity<List<ProviderBean>> getTripDeals(@PathVariable UUID id){
		return ResponseEntity.ok(tripdealsService.getTripDealsList(id));
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<User> getUser(@PathVariable UUID userId){
		return ResponseEntity.ok(userProxy.getUser(userId));
	}
}

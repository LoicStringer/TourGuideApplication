package com.TourGuideApplication.controller;

import java.util.List;
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
import com.TourGuideApplication.responseentity.ClosestAttractionsList;
import com.TourGuideApplication.responseentity.UsersLocationsList;
import com.TourGuideApplication.service.AttractionLocationService;
import com.TourGuideApplication.service.AttractionRewardPointsService;
import com.TourGuideApplication.service.TripDealsService;
import com.TourGuideApplication.service.UserLocationService;
import com.TourGuideApplication.service.UserService;
import com.TourGuideApplication.service.UserTripPreferencesService;

@RestController
public class Controller {
	
	@Autowired
	private UserLocationService userLocationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AttractionLocationService attractionLocationservice;
	
	@Autowired
	private AttractionRewardPointsService attractionRewardPointsService;
	
	@Autowired
	private TripDealsService tripdealsService;
	
	@Autowired
	private UserTripPreferencesService userTripPreferencesService;
	
	@GetMapping("/users/{userId}/location")
	public ResponseEntity<LocationBean> getUserLocation(@PathVariable UUID userId){
		VisitedLocationBean visitedLocationBean = userLocationService.getUserLocation(userId);
		LocationBean location = visitedLocationBean.getLocation();
		return ResponseEntity.ok(location);
	}
	
	@GetMapping("/users/locations")
	public ResponseEntity<UsersLocationsList> getAllUsersLastLocation(){
		return ResponseEntity.ok(userLocationService.getEachUsersLocationsList());
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> addUser(@RequestBody User user){
		return ResponseEntity.ok(userService.addUser(user));
	}
	
	@GetMapping("users/{userId}/attractions")
	public ResponseEntity<ClosestAttractionsList> getTheUserClosestAttractions(@PathVariable UUID userId){
		return ResponseEntity.ok(attractionLocationservice.getTheUserClosestAttractions(userId));
	}

	@GetMapping("/users/{userId}/attractions/{attractionId}/reward-points")
	public int getAttractionRewardPoints(@PathVariable("userId") UUID userId,@PathVariable("attractionId") UUID attractionId) {
		return attractionRewardPointsService.getAttractionRewardPoints(userId, attractionId);
	}
		
	@PostMapping("/users/{userId}/trip-preferences")
	public ResponseEntity<UserTripPreferencesForm> addMyPreferences 
	(@PathVariable ("userId") UUID userId,@RequestBody UserTripPreferencesForm userTripPreferencesForm){
		return ResponseEntity.ok(userTripPreferencesService.addUserTripPreferences(userId, userTripPreferencesForm));
	}
	
	
	@GetMapping("users/{id}/trip-deals")
	public ResponseEntity<List<ProviderBean>> getTripDeals(@PathVariable UUID id){
		return ResponseEntity.ok(tripdealsService.getTripDealsList(id));
	}
}

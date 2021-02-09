package com.TourGuideApplication.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.ProviderBean;
import com.TourGuideApplication.bean.UserBean;
import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.form.UserTripPreferencesForm;
import com.TourGuideApplication.responseentity.ClosestAttractionsList;
import com.TourGuideApplication.service.TourGuideApplicationService;

@RestController
public class TourGuideApplicationController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${closestAttractionsRetrieved.number}")
	private int attractionRetrievedNumber;
	
	@Autowired
	private TourGuideApplicationService tourGuideApplicationService;
	
	@PostMapping("/users")
	public ResponseEntity<UserBean> addUser(@RequestBody UserBean user){
		log.info("Processing the application endpoint to add user "+user.getUserName());
		return ResponseEntity.ok(tourGuideApplicationService.addUser(user));
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserBean> getUserBean(@PathVariable UUID userId){
		log.info("Processing the application endpoint to retriev user "+userId);
		return ResponseEntity.ok(tourGuideApplicationService.getUserBean(userId));
	}
	
	@GetMapping("/users/{userId}/locations/latest")
	public ResponseEntity<LocationBean> getUserLocation(@PathVariable UUID userId){
		log.info("Processing the application endpoint to retrieve the user "+userId+" latest location.");
		return ResponseEntity.ok(tourGuideApplicationService.getUserLocation(userId));
	}
	
	@GetMapping("/users/locations/latest")
	public ResponseEntity<Map<UUID,LocationBean>> getAllUsersLatestLocation(){
		log.info("Processing the application endpoint to retrieve the each user latest location list.");
		return ResponseEntity.ok(tourGuideApplicationService.getEachUserLatestLocationList());
	}
	
	@GetMapping("/users/{userId}/attractions")
	public ResponseEntity<ClosestAttractionsList> getClosestAttractionsList (@PathVariable UUID userId){
		log.info("Processing the application endpoint to retrieve the user "+ attractionRetrievedNumber +" closest attractions list.");
		return ResponseEntity.ok(tourGuideApplicationService.getTheUserClosestAttractionsList(userId));
	}
	
	@GetMapping("/users/{userId}/rewards")
	public ResponseEntity<List<UserRewardBean>> getUserRewardsList (@PathVariable UUID userId){
		log.info("Processing the application endpoint to retrioeve the user "+userId+" rewards list.");
		return ResponseEntity.ok(tourGuideApplicationService.getUserRewardsList(userId));
	}
	
	@PostMapping("/users/{userId}/trip-deals")
	public ResponseEntity<List<ProviderBean>> getUserTripDeals (@PathVariable UUID userId, @RequestBody UserTripPreferencesForm userTripPreferencesForm){
		log.info("Processing the application endpoint to retrieve a discounted trip deals list for user "+userId);
		return ResponseEntity.ok(tourGuideApplicationService.getUserTripDeals(userId,userTripPreferencesForm));
	}
	
}

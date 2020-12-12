package com.TourGuideApplication.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.model.User;
import com.TourGuideApplication.responseentity.ClosestAttractionsList;
import com.TourGuideApplication.responseentity.UsersLocationsList;
import com.TourGuideApplication.service.AttractionLocationService;
import com.TourGuideApplication.service.UserLocationService;
import com.TourGuideApplication.service.UserService;

@RestController
public class Controller {
	
	@Autowired
	private UserLocationService userLocationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AttractionLocationService attractionLocationservice;
	
	@GetMapping("/users/{id}/location")
	public ResponseEntity<LocationBean> getUserLocation(@PathVariable UUID id){
		VisitedLocationBean visitedLocationBean = userLocationService.getUserLocation(id);
		LocationBean location = visitedLocationBean.getLocation();
		return ResponseEntity.ok(location);
	}
	
	@GetMapping("/users/location")
	public ResponseEntity<UsersLocationsList> getAllUsersLastLocation(){
		return ResponseEntity.ok(userLocationService.getEachUsersLocationsList());
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> addUser(@RequestBody User user){
		return ResponseEntity.ok(userService.addUser(user));
	}
	
	@GetMapping("/{id}/attractions")
	public ResponseEntity<ClosestAttractionsList> getTheUserClosestAttractions(@PathVariable UUID id){
		return ResponseEntity.ok(attractionLocationservice.getTheUserClosestAttractions(id));
	}

}

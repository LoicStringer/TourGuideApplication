package com.TourGuideApplication.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.proxy.LocationProxy;

@RestController
public class Controller {
	
	@Autowired
	private LocationProxy locationProxy;
	
	@GetMapping("/users/{id}/location")
	public ResponseEntity<LocationBean> getUserLocation(@PathVariable UUID id){
		VisitedLocationBean visitedLocationBean = locationProxy.getUserLocation(id);
		LocationBean location = visitedLocationBean.getLocation();
		return ResponseEntity.ok(location);
	}
	

}

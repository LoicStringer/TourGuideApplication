package com.TourGuideApplication.proxy;

import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.model.User;

@FeignClient(name = "tourguide-user-service", url= "localhost:9001")
public interface UserProxy {

	@GetMapping("/users/{userId}")
	User getUser(@PathVariable ("userId") UUID userId);
	
	@PostMapping("/users/{userId}/visited-locations")
	void addUserVisitedLocation(@PathVariable ("userId")UUID userId, @RequestBody VisitedLocationBean visitedLocation);
	
	@GetMapping("/users/visited-locations/latest")
	Map<UUID,LocationBean> getEachUserLatestLocationList();
}

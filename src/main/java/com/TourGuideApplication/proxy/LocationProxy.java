package com.TourGuideApplication.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.TourGuideApplication.bean.VisitedLocationBean;

@FeignClient(name = "tourguide-location-service", url = "localhost:9004")
public interface LocationProxy {

	@GetMapping("/location")
	VisitedLocationBean getUserLocation(UUID id);
	
}

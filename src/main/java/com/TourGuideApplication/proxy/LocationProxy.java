package com.TourGuideApplication.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.TourGuideApplication.bean.VisitedLocationBean;

@FeignClient(name = "tourguide-location-service", url = "localhost:9004")
public interface LocationProxy {

	@GetMapping("{id}/location")
	VisitedLocationBean getUserLocation(@PathVariable("id") UUID id);
	
}

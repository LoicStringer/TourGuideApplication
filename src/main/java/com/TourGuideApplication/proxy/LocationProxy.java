package com.TourGuideApplication.proxy;

import java.util.TreeMap;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.TourGuideApplication.bean.AttractionBean;
import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.VisitedLocationBean;


@FeignClient(name = "${feign.client.location.name}", url = "${feign.client.location.url}")
public interface LocationProxy {

	@GetMapping("/users/{userId}/visited-locations/latest")
	VisitedLocationBean getUserLocation(@PathVariable("userId") UUID userId);
	
	@PostMapping("/attractions/distances")
	TreeMap<Double,AttractionBean> getDistancesToAttractions (@RequestBody LocationBean location);
	
}

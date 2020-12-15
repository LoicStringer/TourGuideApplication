package com.TourGuideApplication.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.TourGuideApplication.bean.ProviderBean;
import com.TourGuideApplication.dto.TripPricerDto;

@FeignClient(name = "tourguide-tripdeals-service", url= "localhost:9002")
public interface TripDealsProxy {

	@PostMapping("/trip-deals")
	List<ProviderBean> getTripDeals(@RequestBody TripPricerDto tripPricerDto); 
	
}

package com.TourGuideApplication.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.client.rewards.name}", url = "${feign.client.rewards.url}")
public interface RewardsProxy {


	@GetMapping("{userId}/attractions/{attractionId}/reward-points")
	int getAttractionRewardPoints(@PathVariable("userId") UUID userId,@PathVariable("attractionId") UUID attractionId) ;
	
}

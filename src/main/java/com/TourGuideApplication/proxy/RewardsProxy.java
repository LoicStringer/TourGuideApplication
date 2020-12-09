package com.TourGuideApplication.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "tourguide-rewards-service", url = "localhost:9003")
public interface RewardsProxy {

}

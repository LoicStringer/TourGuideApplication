package com.TourGuideApplication.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "tourguide-tipdeals-service", url = "localhost:9002")
public interface TripDealsProxy {

}

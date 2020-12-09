package com.TourGuideApplication.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "tourguide-user-service", url = "localhost:9001")
public interface UserProxy {

}

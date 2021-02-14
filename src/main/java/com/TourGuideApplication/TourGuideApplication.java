package com.TourGuideApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients("com.TourGuideApplication")
@EnableScheduling
public class TourGuideApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourGuideApplication.class, args);
	}

}

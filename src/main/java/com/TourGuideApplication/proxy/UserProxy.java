package com.TourGuideApplication.proxy;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.ProviderBean;
import com.TourGuideApplication.bean.UserBean;
import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.form.UserTripPreferencesForm;

@FeignClient(name = "tourguide-user-service", url= "localhost:9001")
public interface UserProxy {

	@GetMapping("/users")
	List<UUID> getAllUsersIdList();
	
	@GetMapping("/users/{userId}")
	UserBean getUserBean(@PathVariable ("userId") UUID userId);
	
	@PostMapping("/users")
	UserBean addUser(@RequestBody UserBean user);
	
	@GetMapping("/users/visited-locations/latest")
	Map<UUID,LocationBean> getEachUserLatestLocationList();

	@PostMapping("/users/{userId}/trip-preferences")
	void addUserTripPreferences(@PathVariable ("userId")UUID userId, @RequestBody  UserTripPreferencesForm userTripPreferencesForm);
	
	@GetMapping("/users/{userId}/trip-deals")
	List<ProviderBean> getUserTripDeals (@PathVariable ("userId")UUID userId);

	@GetMapping("/users/{userId}/rewards")
	List<UserRewardBean> getUserRewardList(@PathVariable ("userId")UUID userId);

	@PostMapping("/users/{userId}/rewards/latest")
	UserRewardBean addUserReward(@PathVariable ("userId")UUID userId);
	
}

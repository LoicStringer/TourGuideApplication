package com.TourGuideApplication.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.TourGuideApplication.bean.AttractionBean;
import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.ProviderBean;
import com.TourGuideApplication.bean.UserBean;
import com.TourGuideApplication.bean.UserRewardBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.form.UserTripPreferencesForm;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.RewardsProxy;
import com.TourGuideApplication.proxy.UserProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest
@TestPropertySource(properties="closestAttractionsRetrieved.number = 2")
@AutoConfigureMockMvc
class TourGuideApplicationServiceTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserProxy userProxy;

	@MockBean
	private LocationProxy locationProxy;

	@MockBean
	private RewardsProxy rewardsProxy;
	
	@Test
	void addUserTest() throws JsonProcessingException, Exception {
		UserBean userToAdd = new UserBean();
		userToAdd.setUserName("Tony");

		when(userProxy.addUser(userToAdd)).thenReturn(userToAdd);

		mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(userToAdd))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.userName").value("Tony"));
	}

	@Test
	void getUserTest() throws JsonProcessingException, Exception {
		UserBean userToGet = new UserBean();
		userToGet.setUserId(UUID.fromString("ae809adc-55f4-456e-84f6-94a04780a462"));
		userToGet.setUserName("Tony");

		when(userProxy.getUserBean(any(UUID.class))).thenReturn(userToGet);

		mockMvc.perform(get("/users/" + userToGet.getUserId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.userName").value("Tony"));
	}

	@Test
	void getUserLocationTest() throws Exception {
		UserBean user = new UserBean();
		user.setUserId(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"));
		user.setUserName("Tony");
		
		VisitedLocationBean visitedLocation = new VisitedLocationBean(user.getUserId(), new LocationBean(48.80, 2.40),
				new Date());

		when(locationProxy.getUserLocation(any(UUID.class))).thenReturn(visitedLocation);

		mockMvc.perform(get("/users/"+user.getUserId()+"/locations/latest"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.latitude").value("48.8"));
	}
	
	@Test
	void getAllUsersLatestLocationTest() throws Exception {
		Map<UUID,LocationBean> usersLastLocationMap = new HashMap<UUID,LocationBean> ();
		LocationBean location = new LocationBean(48.80,2.40);
		LocationBean locationBis = new LocationBean(2.40,48.80);
		UUID userId = UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5");
		UUID userBisId = UUID.fromString("0b0bb92f-f1f0-4b64-8460-60a896ff67ff");
		usersLastLocationMap.put(userId,location);
		usersLastLocationMap.put(userBisId, locationBis);
		
		when(userProxy.getEachUserLatestLocationList()).thenReturn(usersLastLocationMap);
		
		mockMvc.perform(get("/users/locations/latest"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(2))
		.andExpect(jsonPath("$.404729ba-ef10-49b6-a340-ee8a40a30fa5.latitude").value(48.80));
	
	}
	
	@Test
	void getUserTripDealsTest() throws Exception {
		UserTripPreferencesForm form = new UserTripPreferencesForm();
		UUID userId = UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5");
		List<ProviderBean> userTripDealsList = new ArrayList<ProviderBean>();
		ProviderBean provider = new ProviderBean(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"),"Belleville FairyTail",500.00);
		ProviderBean providerBis = new ProviderBean(UUID.fromString("0b0bb92f-f1f0-4b64-8460-60a896ff67ff"),"Menilmontant LeisurePark",600.00);
		userTripDealsList.add(provider);
		userTripDealsList.add(providerBis);
		
		when(userProxy.getUserTripDeals(any(UUID.class))).thenReturn(userTripDealsList);
		
		mockMvc.perform(post("/users/"+userId+"/trip-deals")
		.content(objectMapper.writeValueAsString(form))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(2))
		.andExpect(jsonPath("$.[0].providerName").value("Belleville FairyTail"));
	}
	
	@Test
	void getUserRewardsListTest() throws Exception {
		UUID userId = UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5");
		List<UserRewardBean> userRewardsList  =new ArrayList<UserRewardBean>();
		UserRewardBean userReward = new UserRewardBean();
		userReward.setRewardCentralPoints(5000);
		UserRewardBean userRewardBis = new UserRewardBean();
		userRewardBis.setRewardCentralPoints(6000);
		userRewardsList.add(userReward);
		userRewardsList.add(userRewardBis);
		
		when(userProxy.getUserRewardList(any(UUID.class))).thenReturn(userRewardsList);
		
		mockMvc.perform(get("/users/"+userId+"/rewards"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(2))
		.andExpect(jsonPath("$.[0].rewardCentralPoints").value(5000));
	}

	
	@Test
	void getTheUserClosestAttractionsListTest() throws Exception {
		UserBean user = new UserBean();
		user.setUserId(UUID.fromString("404729ba-ef10-49b6-a340-ee8a40a30fa5"));
		
		VisitedLocationBean visitedLocation = new VisitedLocationBean(user.getUserId(),new LocationBean(48.88,2.38),new Date());
		
		when(locationProxy.getUserLocation(user.getUserId())).thenReturn(visitedLocation);
		
		TreeMap<Double,AttractionBean> distancesToAttraction = new TreeMap<Double,AttractionBean>();
		AttractionBean attraction = new AttractionBean(UUID.randomUUID(),"Buttes Chaumont","Paris","France",48.8809,2.3828);
		AttractionBean attraction1 = new AttractionBean(UUID.randomUUID(),"Père Lachaise","Paris","France",48.8614,2.3933);
		distancesToAttraction.put(2.50,attraction);
		distancesToAttraction.put(3.50,attraction1);
		
		when(locationProxy.getDistancesToAttractions(any(LocationBean.class))).thenReturn(distancesToAttraction);
		when(rewardsProxy.getAttractionRewardPoints(user.getUserId(), attraction.getAttractionId())).thenReturn(5000);
		when(rewardsProxy.getAttractionRewardPoints(user.getUserId(), attraction1.getAttractionId())).thenReturn(5000);
		
		mockMvc.perform(get("/users/"+user.getUserId()+"/attractions"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.attractionDetailsList.[0].attractionName").value("Buttes Chaumont"))
		.andExpect(jsonPath("$.attractionDetailsList.[1].attractionName").value("Père Lachaise"));
	}
	
}


package com.TourGuideApplication.integration;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.TourGuideApplication.bean.LocationBean;
import com.TourGuideApplication.bean.UserBean;
import com.TourGuideApplication.bean.VisitedLocationBean;
import com.TourGuideApplication.proxy.LocationProxy;
import com.TourGuideApplication.proxy.RewardsProxy;
import com.TourGuideApplication.proxy.UserProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
}

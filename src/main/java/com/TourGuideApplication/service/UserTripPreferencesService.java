package com.TourGuideApplication.service;

import java.util.UUID;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.form.UserTripPreferencesForm;
import com.TourGuideApplication.model.UserTripPreferences;

@Service
public class UserTripPreferencesService {

	@Autowired
	private UserService userService;
	
	public UserTripPreferences convertUserPreferencesForm (UserTripPreferencesForm userTripPreferencesForm) {
		UserTripPreferences userTripPreferences = new UserTripPreferences();
		CurrencyUnit userTripPreferencesCurrency = Monetary.getCurrency(userTripPreferencesForm.getCurrency());
		userTripPreferences.setAttractionProximity(userTripPreferencesForm.getAttractionProximity());
		userTripPreferences.setCurrency(userTripPreferencesCurrency);
		userTripPreferences.setLowerPricePoint(Money.of(userTripPreferencesForm.getLowerPricePoint(),userTripPreferencesCurrency));
		userTripPreferences.setHighPricePoint(Money.of(userTripPreferencesForm.getHighPricePoint(), userTripPreferencesCurrency));
		userTripPreferences.setTripDuration(userTripPreferencesForm.getTripDuration());
		userTripPreferences.setNumberOfAdults(userTripPreferencesForm.getNumberOfAdults());
		userTripPreferences.setNumberOfChildren(userTripPreferencesForm.getNumberOfChildren());
		userTripPreferences.setTicketQuantity(userTripPreferencesForm.getTicketQuantity());
		return userTripPreferences;
	}
	
	public UserTripPreferencesForm addUserTripPreferences(UUID userId,UserTripPreferencesForm userTripPreferencesForm) {
		UserTripPreferences userTripPreferences = convertUserPreferencesForm(userTripPreferencesForm);
		userService.getUser(userId).setPreferences(userTripPreferences);
		return userTripPreferencesForm;
	}
}

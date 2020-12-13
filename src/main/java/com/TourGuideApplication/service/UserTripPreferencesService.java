package com.TourGuideApplication.service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import com.TourGuideApplication.form.UserTripPreferencesForm;
import com.TourGuideApplication.model.UserTripPreferences;

@Service
public class UserTripPreferencesService {

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
	
}

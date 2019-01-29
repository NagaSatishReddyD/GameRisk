package com.soen6441.risk;

import java.util.List;

/**
 * Country class contains the properties of the countries
 * @author Naga Satish Reddy
 *
 */
public class Country {
	
	String countryName;
	List<Country> adjacentCountries;
	
	public Country(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}

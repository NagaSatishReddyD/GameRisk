package com.soen6441.risk;

import java.util.ArrayList;
import java.util.List;

/**
 * Country class contains the properties of the countries
 * @author Naga Satish Reddy
 *
 */
public class Country {
	
	String countryName;
	String playerName;
	List<Country> adjacentCountries;
	
	public Country(String countryName) {
		this.countryName = countryName;
		this.adjacentCountries = new ArrayList<>();
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public List<Country> getAdjacentCountries() {
		return adjacentCountries;
	}

	public void setAdjacentCountries(List<Country> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}

	public void addAdjacentCountry(Country country) {
		this.adjacentCountries.add(country);
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}

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
	int armiesOnCountry;
	List<Country> adjacentCountries;
	
	public Country(String countryName) {
		this.countryName = countryName;
		this.adjacentCountries = new ArrayList<>();
		this.armiesOnCountry = 0;
	}
	
	
	public int getArmiesOnCountry() {
		return armiesOnCountry;
	}

	public void setArmiesOnCountry(int armiesOnCountry) {
		this.armiesOnCountry = armiesOnCountry;
	}

	public void incrementArmiesOnCountry(int incrementArmies) {
		this.armiesOnCountry += incrementArmies;
	}
	
	public void decreaseArmiesOnCountry(int decreaseQuantity) {
		this.armiesOnCountry -= decreaseQuantity;
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

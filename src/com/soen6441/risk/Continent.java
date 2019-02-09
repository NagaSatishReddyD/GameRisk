package com.soen6441.risk;

import java.util.ArrayList;
import java.util.List;

public class Continent {
	String continentName;
	int armiesGainedAfterConquer;
	List<Country> countriesInContinent;
	
	public Continent(String continentName, int armiesGain) {
		this.continentName = continentName;
		this.armiesGainedAfterConquer = armiesGain;
		countriesInContinent = new ArrayList<>();
	}

	
	public String getContinentName() {
		return continentName;
	}


	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}


	public int getArmiesGainedAfterConquer() {
		return armiesGainedAfterConquer;
	}


	public void setArmiesGainedAfterConquer(int armiesGainedAfterConquer) {
		this.armiesGainedAfterConquer = armiesGainedAfterConquer;
	}


	public List<Country> getCountriesInContinent() {
		return countriesInContinent;
	}

	public void setCountriesInContinent(List<Country> countriesInContinent) {
		this.countriesInContinent = countriesInContinent;
	}
	
	public void addCountryInContinent(Country country) {
		this.countriesInContinent.add(country);
	}
}

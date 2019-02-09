package com.soen6441.risk.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.soen6441.risk.Continent;
import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameConstants;

/**
 * RiskBoardModel is used to handle the actions in the board frame.
 * @author Naga Satish Reddy
 *
 */
public class RiskBoardModel {
	String imageName;
	Map<String, Continent> continentsMap;
	Map<String, Country> contriesMap;
	List<Country> countriesList;
	private List<Player> playersData;

	public void loadRequiredData() throws IOException {
		File configFile = new File(System.getProperty("user.dir")+"/resources/config.map");
		BufferedReader reader = null;
		try {
			int section = 0;
			String line;
			reader = new BufferedReader(new FileReader(configFile));
			while((line = reader.readLine()) != null) {
				if(line.trim().equals(RiskGameConstants.SECTION_ONE)) {
					section = 1;
				}else if(line.trim().equals(RiskGameConstants.SECTION_TWO)){
					section = 2;
				}else if(line.trim().equals(RiskGameConstants.SECTION_THREE)){
					section = 3;
				}else {
					findTheSectionToParseData(section, line);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Problem with the file. Couldn't read the file");
		} catch (IOException e) {
			System.out.println("Problem while reading the file data");
		}finally {
			if(reader != null)
				reader.close();
		}
	}

	/**
	 * findTheSectionToParseData gets the data from the config file and parses the data based on the section
	 * @param section, section=1([MAPS]), section=2([CONTINENTS]), section=3([TERRITORIES])
	 * @param line
	 */
	private void findTheSectionToParseData(int section, String line) {
		if(section == 1) {
			findImageName(line);
		}else if(section == 2) {
			createCountinents(line);
		}else {
			createContries(line);
		}
	}

	/**
	 * createContries is used to create the territories and linked to the respective continents;
	 * @param line
	 */
	private void createContries(String line) {
		if(contriesMap == null) {
			contriesMap = new HashMap<>();
			countriesList = new ArrayList<>();
		}
		String[] territoryDetails = line.split(",");
		String countryName = territoryDetails[0];
		String continentName = territoryDetails[3];
		Country country = new Country(countryName);
		int index = 4;
		while(index < territoryDetails.length) {
			String countryNameAdjacent = territoryDetails[index];
			if(contriesMap.containsKey(continentName)) {
				country.addAdjacentCountry(contriesMap.get(countryName));
			}else {
				Country adjacentCountry = new Country(countryName);
				country.addAdjacentCountry(country);
				contriesMap.put(countryNameAdjacent, adjacentCountry);
			}
			Continent continent = continentsMap.get(continentName);
			continent.addCountryInContinent(country);
			index++;
		}
		contriesMap.put(countryName, country);
		countriesList.add(country);
	}

	/**
	 * 
	 * @param line
	 */
	private void createCountinents(String line) {
		if(continentsMap==null) {
			continentsMap = new HashMap<>();
		}
		String[] continentArmies = line.split("=");
		Continent continent = new Continent(continentArmies[0], Integer.parseInt(continentArmies[1]));
		continentsMap.put(continentArmies[0], continent);
	}

	/**
	 * findImageName is used to find the image name need to be displayed on the screen
	 * @param line
	 */
	private void findImageName(String line) {
		if(line.contains("image")) {
			imageName = line.substring(line.indexOf("=")+1);
		}
	}

	public void assignCountriesToPlayers(int playersCount) {
		Random random = new Random();
		int index = 0;
		if(playersData == null)
			playersData = new ArrayList<>();
		while(index<playersCount) {
			String playerName = "Player "+ ++index;
			int initalArmiesAssigned = 5 * (10 - playersCount);
			playersData.add(new Player(playerName, initalArmiesAssigned));
		}
		List<Integer> assignedCountriesList= new ArrayList<>();
		for(int i = 0; i< countriesList.size();i++) {
			assignedCountriesList.add(i);
		}
		for(index = 0; assignedCountriesList.size() != 0;index++) {
			int countryIndex = random.nextInt(assignedCountriesList.size());
			Player player = playersData.get(index % playersCount);
			Country country = countriesList.get(assignedCountriesList.get(countryIndex));
			country.setPlayerName(player.getPlayerName());
			player.addTerritory(player, country);
			assignedCountriesList.remove(countryIndex);
		}
	}
}

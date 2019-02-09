package com.soen6441.risk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RiskGameInitialLogicalImpl class contains the logics needed to load the game before starting.
 * @author Naga Satish Reddy
 *
 */
public class RiskGameInitialLogicalImpl {

	public void loadRequiredData(String playersCount, List<Player> playersData, List<Country> countriesList) {
		this.assignCountriesToPlayers(Integer.parseInt(playersCount), playersData, countriesList);
	}
	
	/**
	 * assignCountriesToPlayers method assigns the territories or countries to player which starting the game.
	 * @param playersCount 
	 * @param countriesList 
	 * @param playersData 
	 */
	public void assignCountriesToPlayers(int playersCount, List<Player> playersData, List<Country> countriesList) {
		Random random = new Random();
		int index = 0;
		List<Integer> assignedCountriesList = new ArrayList<>();
		while(index<playersCount) {
			String playerName = "Player "+ ++index;
			int initalArmiesAssigned = 5 * (10 - playersCount);
			playersData.add(new Player(playerName, initalArmiesAssigned));
		}
		
		File countriesFile = new File(System.getProperty("user.dir")+RiskGameConstants.COUNTRIES_FILE);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(countriesFile));
			String[] countriesName = reader.readLine().trim().split(",");
			int countriesLength = countriesName.length;
			for(index = 0; index < countriesLength; index++) {
				Country country = new Country(countriesName[index].toUpperCase());
				countriesList.add(country);
				assignedCountriesList.add(index);
			}
			
			for(index = 0; assignedCountriesList.size() != 0;index++) {
				int countryIndex = random.nextInt(assignedCountriesList.size());
				Player player = playersData.get(index % playersCount);
				Country country = countriesList.get(assignedCountriesList.get(countryIndex));
				country.setPlayerName(player.getPlayerName());
				player.addTerritory(player, country);
				assignedCountriesList.remove(countryIndex);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Countries file not found...");
		} catch (IOException e) {
			System.out.println("Something goes wrong while reading countries file..");
		}		
	}
}

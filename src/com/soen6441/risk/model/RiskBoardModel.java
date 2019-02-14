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
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.soen6441.risk.Continent;
import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.view.RiskBoardView;

/**
 * RiskBoardModel is used to handle the actions in the board frame.
 * @author Naga Satish Reddy
 *
 */
public class RiskBoardModel {
	String imageName;
	Map<String, Continent> continentsMap;
	Map<String, Country> countriesMap;
	List<Country> countriesList;
	private List<Player> playersData;
	int currentPlayerIndex = 0;

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
			//			countriesMap = countriesList.stream().collect(Collectors.toMap(Country::getCountryName, Function.identity()));
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
		if(countriesMap == null) {
			countriesMap = new HashMap<>();
			countriesList = new ArrayList<>();
		}
		String[] territoryDetails = line.split(",");
		String countryName = territoryDetails[0];
		String continentName = territoryDetails[3];
		Country country = new Country(countryName);
		int index = 4;
		while(index < territoryDetails.length) {
			String countryNameAdjacent = territoryDetails[index];
			if(countriesMap.containsKey(countryNameAdjacent)) {
				country.addAdjacentCountry(countriesMap.get(countryNameAdjacent));
			}else {
				Country adjacentCountry = new Country(countryNameAdjacent);
				country.addAdjacentCountry(adjacentCountry);
				countriesMap.put(countryNameAdjacent, adjacentCountry);
			}
			index++;
		}
		countriesMap.put(countryName, country);
		countriesList.add(country);
		continentsMap.get(continentName).addCountryInContinent(country);
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

	/**
	 * updateTheBoardScreenData method is used to handle the actions done before the each turn for the player
	 * @param currentPlayerValue
	 * @param view
	 */
	public void updateTheBoardScreenData(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex % playersData.size());
		if(currentPlayer.getArmyCountAvailable() == 0) {
			int Reinforcement = (int) Math.floor(currentPlayer.getTerritoryOccupied().size() / 3);
			if(Reinforcement >= 3) {
				currentPlayer.setArmyCountAvailable(Reinforcement);
			}else {
				currentPlayer.setArmyCountAvailable(3);
			}
		}
		view.getReinforceBtn().setVisible(true);
		view.getMoveArmiesBtn().setVisible(false);
		view.getEndFortificationBtn().setVisible(false);
		view.getCurrentPlayerTurnLabel().setText(currentPlayer.getPlayerName()+" Turn !!");
		view.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
		updateCountriesComboBox(currentPlayer, view);
		updateAllCountriesData(view);
	}

	/**
	 * updateAllCountriesData updates the countries and continents details in the board view screen.
	 * @param view 
	 */
	private void updateAllCountriesData(RiskBoardView view) {
		StringBuilder continentsData = new StringBuilder();
		continentsMap.keySet().forEach(continentKey -> {
			continentsData.append("<h1>").append(continentKey).append("</h1>").append("<br/>");
			List<Country> countriesAvailable = continentsMap.get(continentKey).getCountriesInContinent();
			countriesAvailable.stream().forEach(country -> {
				String data = country.getCountryName()+"\t"+country.getArmiesOnCountry()+"\t"+country.getPlayerName();
				continentsData.append("<pre>").append(data).append("</pre>").append("<br/>");
			});
		});
		view.getContinentTextArea().setText(continentsData.toString());
	}

	/**
	 * updateCountriesComboBox method is used to update the countries list based on the player turn
	 * @param currentPlayer
	 * @param view
	 */
	private void updateCountriesComboBox(Player currentPlayer, RiskBoardView view) {
		List<String> playerCountriesNames = currentPlayer.getTerritoryOccupied().stream().map(countey -> countey.getCountryName()).collect(Collectors.toList());
		DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) view.getCountryComboBox().getModel();
		comboBoxModel.removeAllElements();
		playerCountriesNames.stream().forEach(comboBoxModel::addElement);
		view.getCountryComboBox().setModel(comboBoxModel);
	}

	/**
	 * getAdjacentCountriesForComboCountry method is used to load the adjacent countries data in the board.
	 * @param view
	 */
	public void getAdjacentCountriesForComboCountry(RiskBoardView view) {
		if(view.getCountryComboBox().getSelectedItem() != null) {
			Country selectedCountry = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
			List<Country> adjacentCountriesList = selectedCountry.getAdjacentCountries();
			DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) view.getAdjacentCountryComboBox().getModel();
			comboBoxModel.removeAllElements();
			adjacentCountriesList.stream().forEach(country -> comboBoxModel.addElement(country.getCountryName()));
			view.getAdjacentCountryComboBox().setModel(comboBoxModel);
		}
	}

	/**
	 * updateArmiesInCountries method is used to update the armies in the contries by using a dialog
	 * @param view
	 * @return
	 */
	public void updateArmiesInCountries(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		
	     Object [] possibilities = new Object [currentPlayer.getArmyCountAvailable()];
	     for(int index = 0; index < currentPlayer.getArmyCountAvailable(); index++) {
	    	 possibilities[index] = index+1;
	     }
	     Integer selectedValue = (Integer)JOptionPane.showInputDialog(view.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
	    		 JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
	     
	     Country country = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
	     country.setArmiesOnCountry(selectedValue);
	     currentPlayer.decrementArmy(selectedValue);
	     view.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
	     updateAllCountriesData(view);
	     if(currentPlayer.getArmyCountAvailable() == 0) {
	    	 JOptionPane.showMessageDialog(view.getBoardFrame(), "Next Player Turn");
	    	 nextPlayer(view);
	     }
	}
	
	/**
	 * updateTheFortificationData method is used to handle the actions done before the each player's fortification
	 * phase turn
	 * @param view
	 */
	public void updateFortificationData(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex % playersData.size());
		int armies = 0;
		for(int i = 0; i < currentPlayer.getTerritoryOccupied().size(); i++) {
			armies += currentPlayer.getTerritoryOccupied().get(i).getArmiesOnCountry();
		}
		view.getReinforceBtn().setVisible(false);
		view.getMoveArmiesBtn().setVisible(true);
		view.getEndFortificationBtn().setVisible(true);
		view.getCurrentPlayerTurnLabel().setText(currentPlayer.getPlayerName()+" Turn !!");
		view.getArmiesCountAvailableLabel().setText(String.valueOf(armies));
		updateCountriesComboBox(currentPlayer, view);
		updateAllCountriesData(view);
	}
	
	/**
	 * moveArmiesBetweenInCountries method is used to move armies between neighboring countries
	 * @param view
	 */
	public void moveArmiesBetweenCountries(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		int armies = 0;
		for(int i = 0; i < currentPlayer.getTerritoryOccupied().size(); i++) {
			armies += currentPlayer.getTerritoryOccupied().get(i).getArmiesOnCountry();
		}
	     Object [] possibilities = new Object [armies];
	     for(int index = 0; index < armies; index++) {
	    	 possibilities[index] = index+1;
	     }
	     Integer selectedValue = (Integer)JOptionPane.showInputDialog(view.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
	    		 JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
	     
	     Country country = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
	     Country adjCountry = countriesMap.get(view.getAdjacentCountryComboBox().getSelectedItem().toString());
	     if(selectedValue < country.getArmiesOnCountry()) {
	    	 country.decreaseArmiesOnCountry(selectedValue);
	    	 adjCountry.setArmiesOnCountry(selectedValue);
	     }else {
	    	 JOptionPane.showMessageDialog(view.getBoardFrame(), "Cannot move armies!");
	     }
	     view.getArmiesCountAvailableLabel().setText(String.valueOf(armies));
	     updateAllCountriesData(view);
	     
	}

	/**
	 * nextPlayer method is used to trigger next player chance.
	 * @param view, boardview object to access board components
	 */
	private void nextPlayer(RiskBoardView view) {
		currentPlayerIndex++;
		if(currentPlayerIndex < playersData.size()) {
			updateTheBoardScreenData(view);
		}else {
			currentPlayerIndex = 0;
			JOptionPane.showMessageDialog(view.getBoardFrame(), "Fortification phase begins!");
			updateFortificationData(view);
		}
		
	}
	
	/**
	 * nextPlayerFortification method is used to trigger next player fortification turn.
	 * @param view
	 */
	public void nextPlayerFortification(RiskBoardView view) {
		currentPlayerIndex++;
		if(currentPlayerIndex == playersData.size()) {
			currentPlayerIndex = 0;
			JOptionPane.showMessageDialog(view.getBoardFrame(), "Next Player Turn");
	    	updateTheBoardScreenData(view);
		}else {
			JOptionPane.showMessageDialog(view.getBoardFrame(), "Next Player Fortification Turn");
			updateFortificationData(view);
		}
		
	}
}

package com.soen6441.risk.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.soen6441.risk.Continent;
import com.soen6441.risk.Country;
import com.soen6441.risk.Dice;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.view.RiskBoardView;

/**
 * RiskBoardModel is used to handle the actions in the board frame.
 * @author Naga Satish Reddy, An Nguyen
 */
public class RiskBoardModel {
	String imageName;
	Map<String, Continent> continentsMap;
	Map<String, Country> countriesMap;
	List<Country> countriesList;
	private List<Player> playersData;
	int currentPlayerIndex = 0;
	boolean isInitialPhase = true;

	/**
	 * loadRequiredData method is used to inital load the riskBoardView screen data
	 * @param fileName, name of the file to be loaded to frame
	 * @param view, RiskBoardView object used to update the components of the this screen
	 * @throws IOException, this exception comes while some problem occurs while reading the file
	 */
	public void loadRequiredData(RiskBoardView view, String fileName) throws IOException {
		File configFile = new File(System.getProperty("user.dir")+"/resources/"+fileName+RiskGameConstants.MAP_FILE_EXTENSION);
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
					findTheSectionToParseData(section, line.trim(), view);
				}
			}
			verifyTheCountriesConnections();
		} catch (FileNotFoundException e) {
			showErrorMessage("Problem with the file. Couldn't read the file");
		} catch (IOException e) {
			showErrorMessage("Problem while reading the file data");
		}finally {
			if(reader != null)
				reader.close();
		}
	}

	/**
	 * verifyTheCountriesConnections method is used to check whether the countries or connected properly or not
	 */
	private void verifyTheCountriesConnections() {
		if(countriesList.size() > 1) {
			List<String> countriesNamesList = countriesList.stream().map(country -> country.getCountryName()).collect(Collectors.toList());
			int [][] countriesConnectedArray = new int[countriesNamesList.size()][countriesNamesList.size()];
			for(String countryName: countriesNamesList) {
				int rowIndex = countriesNamesList.indexOf(countryName);
				List<String> adjacentCountriesList = countriesMap.get(countryName).getAdjacentCountries().stream().map(country -> country.getCountryName()).collect(Collectors.toList());
				for(String adjacentCountryName : adjacentCountriesList) {
					int columnIndex = countriesNamesList.indexOf(adjacentCountryName);
					countriesConnectedArray[rowIndex][columnIndex] = 1;
				}
			}
			for(int row = 0; row <= countriesNamesList.size()/2; row++)
				for(int column = 0; column < countriesNamesList.size();column++) {
					if(countriesConnectedArray[row][column] != countriesConnectedArray[column][row]) {
						showErrorMessage("Some countries are not connected. Please check the config file");
					}
				}
		}else {
			showErrorMessage("Only one country exist. Please check the selected map file");
		}
	}

	/**
	 * createOrUpdateImage method is used to update the map data on the board
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	private void createOrUpdateImage(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		BufferedImage bufferedImage;
		Country currentCountry = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
		try {
			bufferedImage = ImageIO.read(new File(System.getProperty("user.dir")+"/resources/"+imageName));
			Graphics2D graphics = bufferedImage.createGraphics();
			for(Country country: countriesList) {
				graphics.setColor(currentPlayer.getPlayerName().equals(country.getPlayerName()) ? Color.RED: Color.BLACK);
				graphics.drawString(String.valueOf(country.getArmiesOnCountry()), country.getxCoordinate(), country.getyCoordinate());
				if(currentCountry.getCountryName().equals(country.getCountryName())) {
					graphics.fillOval((int)country.getxCoordinate(), (int)country.getyCoordinate(), 10, 10);
				}
			}
			Image scaledImage = bufferedImage.getScaledInstance(view.getMapPanel().getWidth(), view.getMapPanel().getHeight(), Image.SCALE_SMOOTH);
			ImageIcon mapImageIcon = new ImageIcon(scaledImage);
			view.getImageLabel().setIcon(mapImageIcon);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(view.getBoardFrame(), "Image File Not Found...");
		}
	}

	/**
	 * findTheSectionToParseData gets the data from the config file and parses the data based on the section
	 * @param section, section=1([MAPS]), section=2([CONTINENTS]), section=3([TERRITORIES])
	 * @param line, each line from the file
	 * @param view, RiskBoardView object used to update the components of the screen 
	 */
	private void findTheSectionToParseData(int section, String line, RiskBoardView view) {
		if(line.trim().equals("")) {
			return;
		}
		if(section == 1) {
			findImageName(line, view);
		}else if(section == 2) {
			createCountinents(line);
		}else {
			createCountries(line, view);
		}
	}

	/**
	 * createContries is used to create the territories and linked to the respective continents;
	 * @param line, country data line from the file
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	private void createCountries(String line, RiskBoardView view) {
		if(countriesMap == null) {
			countriesMap = new HashMap<>();
			countriesList = new ArrayList<>();
		}
		try {
			String[] territoryDetails = line.split(",");
			String countryName = territoryDetails[0];
			String continentName = territoryDetails[3];
			Country country;
			if(countriesMap.containsKey(countryName)) {
				country = countriesMap.get(countryName);
			}else {
				country = new Country(countryName);
			}
			country.setxCoordinate(Integer.parseInt(territoryDetails[1]));
			country.setyCoordinate(Integer.parseInt(territoryDetails[2]));
			country.setArmiesOnCountry(1);
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
		}catch (Exception e) {
			showErrorMessage("Problem while parsing the data");
		}
	}

	/**
	 * createCountinents method is to create the countinents by reading the data from the file.
	 * @param line, is the each continent information from the config file line by line
	 */
	public void createCountinents(String line) {
		if(continentsMap==null) {
			continentsMap = new HashMap<>();
		}
		String[] continentArmies = line.split("=");
		try {
			int value = Integer.parseInt(continentArmies[1]);
			if(value > 0) {
				Continent continent = new Continent(continentArmies[0], value);
				continentsMap.put(continentArmies[0], continent);
			}else {
				showErrorMessage("Continents Reinforcement can't be negative or zero");
			}
		}catch (Exception e) {
			showErrorMessage("Error while reading data");
		}
	}

	/**
	 * showErrorMessage method is to show the error dialog messages in the frame.
	 * @param message, string value which need to be shown in the message box
	 */
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
		System.exit(0);
	}

	/**
	 * findImageName is used to find the image name need to be displayed on the screen
	 * @param line, country data line from the file
	 * @param view, RiskBoardView object used to update the components of the screen 
	 */
	private void findImageName(String line, RiskBoardView view) {
		if(line.contains("image")) {
			imageName = line.substring(line.indexOf('=')+1);
		}
	}

	/**
	 * assignCountriesToPlayers method is used to assign the countries to the players randomly
	 * @param playersCount, count of the players how many players are playing the game
	 * @param view, RiskBoardView object used to update the components of the screen
	 * @throws NoSuchAlgorithmException, when instance is not create about the random
	 */
	public void assignCountriesToPlayers(int playersCount, RiskBoardView view) throws NoSuchAlgorithmException {
		Random random = SecureRandom.getInstanceStrong();
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
		for(index = 0; !assignedCountriesList.isEmpty();index++) {
			int countryIndex = random.nextInt(assignedCountriesList.size());
			Player player = playersData.get(index % playersCount);
			Country country = countriesList.get(assignedCountriesList.get(countryIndex));
			country.setPlayerName(player.getPlayerName());
			player.addTerritory(player, country);
			assignedCountriesList.remove(countryIndex);
		}
		for(int i = 0; i < playersData.size();i++) {
			playersData.get(i).setArmyCountAvailable(playersData.get(i).getArmyCountAvailable() - playersData.get(i).getTerritoryOccupied().size());
		}
	}

	/**
	 * updateTheBoardScreenData method is used to handle the actions done before the each turn for the player
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	public void updateTheBoardScreenData(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		if(currentPlayer.getArmyCountAvailable() == 0) {
			setTheBonusArmiesToPlayer(currentPlayer);
			isInitialPhase = false;
		}
		enableDisableButtons(RiskGameConstants.REINFORCEMENT_PHASE, view);
		view.getCurrentPlayerTurnLabel().setText(currentPlayer.getPlayerName()+" Turn !!");
		view.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
		updateCountriesComboBox(currentPlayer, view);
		createOrUpdateImage(view);
	}

	/**
	 * updateCountriesComboBox method is used to update the countries list based on the player turn
	 * @param currentPlayer, value of the currentplayer index
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	private void updateCountriesComboBox(Player currentPlayer, RiskBoardView view) {
		List<String> playerCountriesNames = currentPlayer.getTerritoryOccupied().stream().map(Country::getCountryName).collect(Collectors.toList());
		DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) view.getCountryComboBox().getModel();
		comboBoxModel.removeAllElements();
		playerCountriesNames.stream().forEach(comboBoxModel::addElement);
		view.getCountryComboBox().setModel(comboBoxModel);
	}

	/**
	 * getAdjacentCountriesForComboCountry method is used to load the adjacent countries data in the board.
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	public void getAdjacentCountriesForComboCountry(RiskBoardView view) {
		if(view.getCountryComboBox().getSelectedItem() != null) {
			Player currentPlayer = playersData.get(currentPlayerIndex);
			Country selectedCountry = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
			List<Country> adjacentCountriesList = selectedCountry.getAdjacentCountries();
			DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) view.getAdjacentCountryComboBox().getModel();
			comboBoxModel.removeAllElements();
			if(view.getAttackBtn().isVisible()) {
				adjacentCountriesList.stream().filter(country -> !country.getPlayerName().equals(currentPlayer.getPlayerName())).forEach(country -> comboBoxModel.addElement(country.getCountryName()));
			}else {
				adjacentCountriesList.stream().forEach(country -> comboBoxModel.addElement(country.getCountryName()));
			}
			view.getAdjacentCountryComboBox().setModel(comboBoxModel);
			createOrUpdateImage(view);
		}
	}

	/**
	 * updateArmiesInCountries method is used to update the armies in the contries by using a dialog
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	public void updateArmiesInCountries(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex);

		Object [] possibilities = new Object [currentPlayer.getArmyCountAvailable()];
		for(int index = 0; index < currentPlayer.getArmyCountAvailable(); index++) {
			possibilities[index] = index+1;
		}
		Integer selectedValue = (Integer)JOptionPane.showInputDialog(view.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
				JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);

		if(selectedValue != null) {
			Country country = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
			country.incrementArmiesOnCountry(selectedValue);
			currentPlayer.decrementArmy(selectedValue);
			view.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
			createOrUpdateImage(view);
			if(currentPlayer.getArmyCountAvailable() == 0) {
				nextPlayer(view);
			}
		}
	}

	/**
	 * setTheBonusArmiesToPlayer method for find the inital Armies setup for the player.
	 * @param currentPlayer, current player object
	 */
	private void setTheBonusArmiesToPlayer(Player currentPlayer) {
		currentPlayer.incrementArmy(countArmiesBasedOnTerritories(currentPlayer));
	}

	/**
	 * countArmiesBasedOnTerritories method to calculate the armies count based on the player territories
	 * @param currentPlayer, current player object
	 * @return count, how many armies are given to players for each round
	 */
	private int countArmiesBasedOnTerritories(Player currentPlayer) {
		return getBonusArmiesOnTerritories(currentPlayer)+ getBonusArmiesOnContinent(currentPlayer);
	}

	/**
	 * getBonusArmiesOnContinent method is used to find the bonus armies based on the continent
	 * @param currentPlayer, current player object who is playing
	 * @return bonusArmies, armies if a player concurred entire continent
	 */
	private int getBonusArmiesOnContinent(Player currentPlayer) {
		int bonusArmies = 0;
		for(String continentKey : continentsMap.keySet()) {
			List<Country> contriesList = continentsMap.get(continentKey).getCountriesInContinent();
			Map<String, List<Country>> playersCountryData = contriesList.stream().collect(Collectors.groupingBy(Country::getPlayerName));
			if(playersCountryData.containsKey(currentPlayer.getPlayerName()) && playersCountryData.get(currentPlayer.getPlayerName()).size() == contriesList.size()) {
				bonusArmies += continentsMap.get(continentKey).getArmiesGainedAfterConquer();
			}
		}
		return bonusArmies;
	}

	/**
	 * getBonusArmiesOnTerritories method is used to find the bonus armies based on the territories
	 * @param currentPlayer, current player object who is playing
	 * @return bonusArmies, armies based on the territories conquered
	 */
	private int getBonusArmiesOnTerritories(Player currentPlayer) {
		int bonusArmies = currentPlayer.getTerritoryOccupied().size() / 3;
		return bonusArmies < 3 ? 3 : bonusArmies;
	}

	/**
	 * moveArmiesBetweenInCountries method is used to move armies between neighboring countries
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	public void moveArmiesBetweenCountries(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		Country country = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
		Country adjacentCountry = countriesMap.get(view.getAdjacentCountryComboBox().getSelectedItem().toString());
		if(country.getArmiesOnCountry() == 0){
			JOptionPane.showMessageDialog(view.getBoardFrame(), "No armies on selected country to move");
		}else if(!isCountriesOwnedByPlayers(country, adjacentCountry)) {
			JOptionPane.showMessageDialog(view.getBoardFrame(), "Selected Adjacent Country is owned by another player");
		}else {
			Object [] possibilities = new Object [country.getArmiesOnCountry()];
			for(int index = 0; index < country.getArmiesOnCountry(); index++) {
				possibilities[index] = index+1;
			}
			Integer selectedValue = (Integer)JOptionPane.showInputDialog(view.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
					JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);

			if(selectedValue != null) {
				country.decreaseArmiesOnCountry(selectedValue);
				adjacentCountry.incrementArmiesOnCountry(selectedValue);
				view.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
				createOrUpdateImage(view);
			}
		}
	}

	/**
	 * isCountriesOwnedByPlayers method is used to check whether the two countries are owned by the same player or not.
	 * @param country
	 * @param adjacentCountry
	 * @return
	 */
	private boolean isCountriesOwnedByPlayers(Country country, Country adjacentCountry) {
		return country.getPlayerName().trim().equalsIgnoreCase(adjacentCountry.getPlayerName().trim());
	}

	/**
	 * nextPlayer method is used to trigger next player chance.
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	private void nextPlayer(RiskBoardView view) {
		if(isInitialPhase) {
			currentPlayerIndex++;
			currentPlayerIndex = currentPlayerIndex%playersData.size();
			JOptionPane.showMessageDialog(view.getBoardFrame(), "Next Player Turn");
			updateTheBoardScreenData(view);
		}else {
			enableDisableButtons(RiskGameConstants.ATTACK_PHASE, view);
		}
	}

	/**
	 * enableDisableButtons method is to enable the required buttons based on the phase
	 * @param view, RiskBoardView object used to update the components of the screen 
	 * @param string
	 */
	private void enableDisableButtons(String phase, RiskBoardView view) {
		view.getReinforceBtn().setVisible(false);
		view.getAttackBtn().setVisible(false);
		view.getEndAttackButton().setVisible(false);
		view.getMoveArmiesBtn().setVisible(false);
		view.getEndFortificationBtn().setVisible(false);
		switch (phase) {
		case RiskGameConstants.REINFORCEMENT_PHASE:
			view.getReinforceBtn().setVisible(true);
			break;
		case RiskGameConstants.ATTACK_PHASE:
			view.getAttackBtn().setVisible(true);
			view.getEndAttackButton().setVisible(true);
			break;
		case RiskGameConstants.FORTIFICATION_PHASE:
			view.getMoveArmiesBtn().setVisible(true);
			view.getEndFortificationBtn().setVisible(true);
			break;
		}
	}

	/**
	 * nextPlayerFortification method is used to trigger next player fortification turn.
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	public void endFortificationPhase(RiskBoardView view) {
		isInitialPhase = true;
		nextPlayer(view);
	}

	/**
	 * attackBetweenCountries method is to attack between to players territories which are adjacent
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	public void attackBetweenCountries(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex);

		Object [] possibilities = {1,2,3};
		
		Integer selectedValue = (Integer)JOptionPane.showInputDialog(view.getBoardFrame(),"How many armies do you want to use to attack", "Armies To Attack",
				JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);

		if(selectedValue != null) {
			try {
				Integer[] currentPlayerDice = new Dice().diceRoll(selectedValue);
			} catch (NoSuchAlgorithmException e) {
				showErrorMessage("Problem while creating throwing dice");
			}
			showErrorMessage("Attack Phase under development");
		}
	}

	public void endAttackPhase(RiskBoardView view) {
		enableDisableButtons(RiskGameConstants.FORTIFICATION_PHASE, view);
	}

	public String getImageName() {
		return imageName;
	}

	public Map<String, Continent> getContinentsMap() {
		return continentsMap;
	}

	public Map<String, Country> getCountriesMap() {
		return countriesMap;
	}

	public List<Country> getCountriesList() {
		return countriesList;
	}

	public List<Player> getPlayersData() {
		return playersData;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public boolean isInitialPhase() {
		return isInitialPhase;
	}


}

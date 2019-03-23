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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.soen6441.risk.Card;
import com.soen6441.risk.Continent;
import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.view.RiskBoardView;

/**
 * <b>RiskBoardModel</b>
 * RiskBoardModel class contains the logics for the events done on the {@link RiskBoardModel} by the player
 * @author Naga Satish Reddy
 * @author An Nguyen
 */
public class RiskBoardModel{
	private String imageName;
	private Map<String, Continent> continentsMap;
	private Map<String, Country> countriesMap;
	private List<Country> countriesList;
	private List<Player> playersData;
	private Map<String, Player> playersMap;
	private int currentPlayerIndex = 0;
	private boolean isInitialPhase = true;
	private List<Card> cardsList;
	private boolean isOcuppiedTerritory = false;
	int cardExchangeCount = 0;

	/**
	 * loadRequiredData method is used to inital load the riskBoardView screen data
	 * @param fileName, name of the file to be loaded to frame
	 */
	public void loadRequiredData(String fileName){
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
			int section = 0;
			String line;
			while(Objects.nonNull(line = reader.readLine())) {
				if(line.trim().equals(RiskGameConstants.SECTION_ONE)) {
					section = 1;
				}else if(line.trim().equals(RiskGameConstants.SECTION_TWO)){
					section = 2;
				}else if(line.trim().equals(RiskGameConstants.SECTION_THREE)){
					section = 3;
				}else {
					findTheSectionToParseData(section, line.trim());
				}
			}
			verifyTheMapIsConnected();
			createCardsData();
		} catch (FileNotFoundException e) {
			showErrorMessage("Problem with the file. Couldn't read the file", true);
		} catch (IOException e) {
			showErrorMessage("Problem while reading the file data", true);
		}
	}

	/**
	 * verifyTheMapIsConnected method checks the map is connected or not.
	 */
	private void verifyTheMapIsConnected() {
		verifyTheCountriesConnections();
		verifyTheContinentsConnections();
	}

	/**
	 * verifyTheContinentsConnections method checks the continents are connected or not
	 */
	private void verifyTheContinentsConnections() {
		List<String> continentsNamesList = continentsMap.keySet().stream().collect(Collectors.toList());
		List<String> connectedContinentsList = new ArrayList<>();
		connectedContinentsList.add(continentsNamesList.get(0));
		int index = 0;
		while(index < connectedContinentsList.size()) {
			addAdjacentCotinentsToList(connectedContinentsList, connectedContinentsList.get(index));
			index++;
		}
		if(continentsNamesList.size() != connectedContinentsList.size()) {
			showErrorMessage("Continents are not connected. Please check the config file", true);
		}
	}

	/**
	 * addAdjacentCotinentsToList method checks the adjacent continent name and adds the name to the connectedContinentsList
	 * @param continentName, current continent name where we checks with the connected the continent.
	 * @param connectedContinentsList, list of continents connected.
	 */
	private void addAdjacentCotinentsToList(List<String> connectedContinentsList, String continentName) {
		List<String> countriesNamesList = continentsMap.get(continentName).getCountriesInContinent().stream().map(country -> country.getCountryName()).collect(Collectors.toList());
		for(String countryName : countriesNamesList) {
			List<String> adjacentCountriesNameList = countriesMap.get(countryName).getAdjacentCountries().stream().map(country -> country.getCountryName()).collect(Collectors.toList());
			while(!adjacentCountriesNameList.isEmpty()) {
				String adjacentCountryName = adjacentCountriesNameList.get(0);
				if(!countriesNamesList.contains(adjacentCountryName)) {
					findContinentName(adjacentCountryName, connectedContinentsList);
				}
				adjacentCountriesNameList.remove(0);
			}
		}
	}

	/**
	 * findContinentName method checks the adjacentCountryName belongs to which continent and adds the continent name in the connectedContinentsList.
	 * @param adjacentCountryName, adjacent country name to which the continent is checked.
	 * @param connectedContinentsList, list of continents connected
	 */
	private void findContinentName(String adjacentCountryName, List<String> connectedContinentsList) {
		for(Entry<String, Continent> continent : continentsMap.entrySet()) {
			List<String> countriesNamesList = continent.getValue().getCountriesInContinent().stream().map(country -> country.getCountryName()).collect(Collectors.toList());
			if(countriesNamesList.contains(adjacentCountryName) && !connectedContinentsList.contains(continent.getKey())) {
				connectedContinentsList.add(continent.getKey());
			}
		}
	}

	/**
	 * createCardsData creates the cards structure used to play the game
	 */
	private void createCardsData() {
		String [] armiesTypes = {"Infantry","Cavalry","Artillery"};
		AtomicInteger counter = new AtomicInteger(0);
		cardsList = new ArrayList<>();
		countriesList.stream().forEach(country -> {
			cardsList.add(new Card(country.getCountryName(), armiesTypes[counter.get()% armiesTypes.length]));
			counter.addAndGet(1);
		});
	}

	/**
	 * verifyTheCountriesConnections method checks whether the territories are joined to the respective adjacent territories or not.
	 * if not connected it throws the error.
	 */
	private void verifyTheCountriesConnections() {
		if(countriesList.size() > 1) {
			List<String> countriesNamesList = countriesList.stream().map(Country::getCountryName).collect(Collectors.toList());
			int [][] countriesConnectedArray = new int[countriesNamesList.size()][countriesNamesList.size()];
			for(String countryName: countriesNamesList) {
				int rowIndex = countriesNamesList.indexOf(countryName);
				List<String> adjacentCountriesList = countriesMap.get(countryName).getAdjacentCountries().stream().map(Country::getCountryName).collect(Collectors.toList());
				for(String adjacentCountryName : adjacentCountriesList) {
					int columnIndex = countriesNamesList.indexOf(adjacentCountryName);
					countriesConnectedArray[rowIndex][columnIndex] = 1;
				}
			}
			for(int row = 0; row <= countriesNamesList.size()/2; row++)
				for(int column = 0; column < countriesNamesList.size();column++) {
					if(countriesConnectedArray[row][column] != countriesConnectedArray[column][row]) {
						showErrorMessage("Some countries are not connected. Please check the config file", true);
					}
				}
		}else {
			showErrorMessage("Only one country exist. Please check the selected map file", true);
		}
	}

	/**
	 * createOrUpdateImage method is used to update the map data on the board. It shows the armies available on the each country
	 * and the currentPlayer territories data will be shown in red color
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	public void createOrUpdateImage(RiskBoardView view) {
		if(Objects.nonNull(view.getAdjacentCountryComboBox().getSelectedItem())) {
			Player currentPlayer = playersData.get(currentPlayerIndex);
			BufferedImage bufferedImage;
			Country currentCountry = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
			Country adjacentCountry = countriesMap.get(view.getAdjacentCountryComboBox().getSelectedItem().toString());
			try {
				bufferedImage = ImageIO.read(new File(System.getProperty("user.dir")+"/resources/"+imageName));
				Graphics2D graphics = bufferedImage.createGraphics();
				for(Country country: countriesList) {
					graphics.setColor(Color.BLACK);
					graphics.fillOval((int)adjacentCountry.getxCoordinate(), (int)adjacentCountry.getyCoordinate(), 10, 10);
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
	}

	/**
	 * findTheSectionToParseData gets the data from the config file and parses the data based on the section
	 * @param section, section=1([MAPS]), section=2([CONTINENTS]), section=3([TERRITORIES])
	 * @param line, each line from the file
	 */
	private void findTheSectionToParseData(int section, String line) {
		if(line.trim().equals("")) {
			return;
		}
		if(section == 1) {
			findImageName(line);
		}else if(section == 2) {
			createCountinents(line);
		}else {
			createCountries(line);
		}
	}

	/**
	 * createContries is used to create the territories and linked to the adjacent territories
	 * @param line, country data line from the file
	 */
	private void createCountries(String line) {
		if(Objects.isNull(countriesMap)) {
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
			showErrorMessage("Problem while parsing the data",true);
		}
	}

	/**
	 * createCountinents method is to create the continents by reading the data from the file.
	 * @param line, is the each continent information from the map file line by line
	 */
	public void createCountinents(String line) {
		if(Objects.isNull(continentsMap)) {
			continentsMap = new HashMap<>();
		}
		String[] continentArmies = line.split("=");
		try {
			int value = Integer.parseInt(continentArmies[1]);
			if(value > 0) {
				Continent continent = new Continent(continentArmies[0], value);
				continentsMap.put(continentArmies[0], continent);
			}else {
				showErrorMessage("Continents Reinforcement can't be negative or zero", true);
			}
		}catch (Exception e) {
			showErrorMessage("Error while reading data", true);
		}
	}

	/**
	 * showErrorMessage method is to show the error dialog messages in the frame.
	 * @param isGameToBeStopped, is used to check whether the game to be stopped or not.
	 * @param message, string value which need to be shown in the message box and stops the game
	 */
	private void showErrorMessage(String message, boolean isGameToBeStopped) {
		JOptionPane.showMessageDialog(null, message);
		if(isGameToBeStopped)
			System.exit(0);
	}

	/**
	 * findImageName is used to find the image name need to be displayed on the screen
	 * @param line, country data line from the file
	 */
	public void findImageName(String line) {
		if(line.contains("image")) {
			imageName = line.substring(line.indexOf('=')+1);
		}
	}

	/**
	 * assignCountriesToPlayers method is used to assign the countries to the players randomly
	 * @param playersCount, count of the players how many players are playing the game
	 */
	public void assignCountriesToPlayers(int playersCount) {
		Random random;
		try {
			random = SecureRandom.getInstanceStrong();
			int index = 0;
			if(Objects.isNull(playersData)) {
				playersData = new ArrayList<>();
				playersMap = new HashMap<>();
			}
			while(index<playersCount) {
				String playerName = "Player "+ ++index;
				int initalArmiesAssigned = 5 * (10 - playersCount);
				Player player =  new Player(playerName, initalArmiesAssigned);
				playersData.add(player);
				playersMap.put(playerName, player);
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
		} catch (NoSuchAlgorithmException e) {
			showErrorMessage("Problem while assigning players. Please restart the game\n"+e.getMessage(), true);
		}
	}

	/**
	 * updateTheBoardScreenData method is used to handle the actions done before the each turn for the player
	 * @param phase, current phase of the player it can be {@link RiskGameConstants#REINFORCEMENT_PHASE}, {@link RiskGameConstants#ATTACK_PHASE}
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 */
	public void updateTheBoardScreenData(RiskBoardView riskBoardView, String phase) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		if(currentPlayer.getArmyCountAvailable() == 0 && phase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			setTheBonusArmiesToPlayer(currentPlayer, riskBoardView);
			isInitialPhase = false;
		}
		updatePlayersInfo(playersData, riskBoardView);
		if(phase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			riskBoardView.getPhaseInfo().setText(RiskGameConstants.REINFORCEMENT_PHASE_INFO);
		}else if(phase.equals(RiskGameConstants.ATTACK_PHASE)) {
			riskBoardView.getPhaseInfo().setText(RiskGameConstants.ATTACK_PHASE_INFO);
		}else if(phase.equals(RiskGameConstants.FORTIFICATION_PHASE)) {
			riskBoardView.getPhaseInfo().setText(RiskGameConstants.FORTIFICATION_PHASE_INFO);
		}
		riskBoardView.getCurrentPhase().setText(phase + " phase");
		enableDisableButtons(phase, riskBoardView);
		riskBoardView.getCurrentPlayerTurnLabel().setText(currentPlayer.getPlayerName()+" Turn !!");
		riskBoardView.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
		riskBoardView.getCardsCountLabel().setText(String.valueOf(currentPlayer.getPlayerCards().size()));
		updateCountriesComboBox(currentPlayer, riskBoardView);
		createOrUpdateImage(riskBoardView);
	}

	/**
	 * canExchangeCards method to decide whether the player has to exchange the cards or not. 
	 * If the player has maximum cards then the player definitely has to exchange.
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 * @return bonusArmies, bonus armies based on the exchange cards.
	 */
	private int canExchangeCards(RiskBoardView riskBoardView) {
		Player player = playersData.get(currentPlayerIndex);
		int bonusAmies = 0;
		String message;
		while(checkExchangeSet(player)) {
			if(player.getPlayerCards().size() >= 5) {
				message = "You have to exchange cards. It is mandatory.";
			}else {
				message = "Do you want to exchnage cards?";
			}
			int selectedValue = JOptionPane.showConfirmDialog(riskBoardView.getBoardFrame(), message, "Cards Exchange", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if(selectedValue == 0) {
				bonusAmies += getExchangeCardsArmies(player);
			}else {
				if(player.getPlayerCards().size() < 5)
					break;
			}
		}
		return bonusAmies;
	}

	/**
	 * getExchangeCardsArmies methods to the exchange the cards
	 * @param currentPlayer, current player object 
	 * @return armies count, bonusArmiesToExchange.
	 */
	public int getExchangeCardsArmies(Player currentPlayer) {
		List<Card> playersCards = currentPlayer.getPlayerCards();
		Map<String, List<Card>> cardsData = currentPlayer.getPlayerCards().stream().collect(Collectors.groupingBy(Card::getArmyType));
		if(cardsData.size() == 3) {
			List<String> cardsArmyName = new ArrayList<>();
			int index = 0;
			while(index < playersCards.size()) {
				if(!cardsArmyName.contains(playersCards.get(index).getArmyType())) {
					cardsArmyName.add(playersCards.get(index).getArmyType());
					playersCards.remove(index);
				}else {
					index++;
				}
			}
			if(++cardExchangeCount < 6) {
				return 4 + ((cardExchangeCount - 1) * 2);
			}else {
				return 5 * (cardExchangeCount - 3);
			}
		}else {
			for(Entry<String, List<Card>> cards : cardsData.entrySet()) {
				if(cards.getValue().size() >= 3) {
					int count = 1;
					int index = 0;
					while(index < playersCards.size()) {
						if(playersCards.get(0).getArmyType().equals(cards.getValue().get(0).getArmyType())) {
							playersCards.remove(index);
							count++;
						}else {
							index++;
						}
						if(count == 3)
							break;
					}
					if(++cardExchangeCount < 6) {
						return 4 + ((cardExchangeCount - 1) * 2);
					}else {
						return 5 * (cardExchangeCount - 3);
					}
				}
			}
		}
		return 0;
	}

	/**
	 * checkExchangeSet method to check the whether the cards has the perfect set to exchange cards.
	 * @param currentPlayer, value of the currentplayer index
	 * @return true, if the data has a perfect set to exchange the cards.
	 */
	public boolean checkExchangeSet(Player currentPlayer) {
		Map<String, List<Card>> cardsData = currentPlayer.getPlayerCards().stream().collect(Collectors.groupingBy(Card::getArmyType));
		if(cardsData.size() == 3) {
			return true;
		}else {
			for(Entry<String, List<Card>> card : cardsData.entrySet()) {
				if(card.getValue().size() >=3)
					return true;
			}
		}
		return false;
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
		if(Objects.nonNull(view.getCountryComboBox().getSelectedItem())) {
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
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 */
	public void updateArmiesInCountries(RiskBoardView riskBoardView) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		Country country = countriesMap.get(riskBoardView.getCountryComboBox().getSelectedItem().toString());
		currentPlayer.reinforceArmyToCountry(country, riskBoardView);
		createOrUpdateImage(riskBoardView);
		if(currentPlayer.getArmyCountAvailable() == 0) {
			nextPlayer(riskBoardView);
		}
	}

	/**
	 * setTheBonusArmiesToPlayer method for find the inital Armies setup for the player.
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 * @param currentPlayer, current player object
	 */
	private void setTheBonusArmiesToPlayer(Player currentPlayer, RiskBoardView riskBoardView) {
		currentPlayer.incrementArmy(countArmiesBasedOnTerritories(currentPlayer, riskBoardView));
	}

	/**
	 * countArmiesBasedOnTerritories method to calculate the armies count based on the player territories
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 * @param currentPlayer, current player object
	 * @return count, how many armies are given to players for each round
	 */
	public int countArmiesBasedOnTerritories(Player currentPlayer, RiskBoardView riskBoardView) {
		return getBonusArmiesOnTerritories(currentPlayer)+ getBonusArmiesOnContinent(currentPlayer) + canExchangeCards(riskBoardView);
	}

	/**
	 * getBonusArmiesOnContinent method is used to find the bonus armies based on the continent
	 * @param currentPlayer, current player object who is playing @see {@link Player}
	 * @return bonusArmies, armies if a player concurred entire continent
	 */
	public int getBonusArmiesOnContinent(Player currentPlayer) {
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
	 * @param currentPlayer, current player object who is playing @see {@link Player}
	 * @return bonusArmies, armies based on the territories conquered
	 */
	public int getBonusArmiesOnTerritories(Player currentPlayer) {
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
			Object [] possibilities = new Object [country.getArmiesOnCountry() - 1];
			for(int index = 0; index < possibilities.length; index++) {
				possibilities[index] = index+1;
			}
			Integer selectedValue = (Integer)JOptionPane.showInputDialog(view.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
					JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);

			if(Objects.nonNull(selectedValue)) {
				country.decreaseArmiesOnCountry(selectedValue);
				adjacentCountry.incrementArmiesOnCountry(selectedValue);
				view.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
				createOrUpdateImage(view);
			}
		}
	}

	/**
	 * isCountriesOwnedByPlayers method is used to check whether the two countries are owned by the same player or not.
	 * @param country, one of the country from the {@link RiskBoardView#getCountryComboBox()}
	 * @param adjacentCountry, one of the country from the {@link RiskBoardView#getAdjacentCountryComboBox()}
	 * @return true, if the both country and adjacent countries belong to current player
	 * 		   false, if the both country and adjacent countries doesn't belong to current player  
	 */
	public boolean isCountriesOwnedByPlayers(Country country, Country adjacentCountry) {
		return country.getPlayerName().trim().equalsIgnoreCase(adjacentCountry.getPlayerName().trim());
	}

	/**
	 * nextPlayer method is used to trigger next player chance.
	 * if isInitialPhase is true it just allows the player to Reinforce their armies,
	 * if isInitalPhase is false then each player can move to the Attack Phase.
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	private void nextPlayer(RiskBoardView view) {
		if(isInitialPhase) {
			currentPlayerIndex++;
			currentPlayerIndex = currentPlayerIndex%playersData.size();
			JOptionPane.showMessageDialog(view.getBoardFrame(), "Next Player Turn");
			updateTheBoardScreenData(view, RiskGameConstants.REINFORCEMENT_PHASE);
		}else {
			enableDisableButtons(RiskGameConstants.ATTACK_PHASE, view);
			updateTheBoardScreenData(view, RiskGameConstants.ATTACK_PHASE);
		}
	}

	/**
	 * enableDisableButtons method is to enable the required buttons based on the phase
	 * In <b>Reinforcement phase</b> only {@link RiskBoardView#getReinforceBtn()}button will be enabled
	 * In <b>Attack Phase</b>  {@link RiskBoardView#getAttackBtn()} and {@link RiskBoardView#getEndAttackButton()} will be enabled
	 * In <b>Fortification Phase</b> {@link RiskBoardView#getEndFortificationBtn()} and {@link RiskBoardView#getMoveArmiesBtn()} will be enabled
	 * @param view, RiskBoardView object used to update the components of the screen 
	 * @param phase, currentPhase in which the player has to play.
	 * if the player has to play reinforcement phase then phase value will be {@link RiskGameConstants#REINFORCEMENT_PHASE}
	 * if the player has to play attack phase then phase value will be {@link RiskGameConstants#ATTACK_PHASE}
	 * if the player has to play fortification phase then value will be {@link RiskGameConstants#FORTIFICATION_PHASE}
	 */
	public void enableDisableButtons(String phase, RiskBoardView view) {
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
	 * endFortificationPhase method is used to trigger next player fortification turn.
	 * @param view, instance of {@link RiskBoardView} object
	 */
	public void endFortificationPhase(RiskBoardView view) {
		isInitialPhase = true;
		nextPlayer(view);
	}

	/**
	 * attackBetweenCountries method is to attack between to players territories which are adjacent
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 */
	public void attackBetweenCountries(RiskBoardView riskBoardView) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		Country currentPlayerCountry = countriesMap.get(riskBoardView.getCountryComboBox().getSelectedItem().toString());
		Country opponentPlayerCountry = countriesMap.get(riskBoardView.getAdjacentCountryComboBox().getSelectedItem().toString());
		Player opponentPlayer = playersMap.get(opponentPlayerCountry.getPlayerName());
		boolean isWon = currentPlayer.attackBetweenCountries(currentPlayerCountry, opponentPlayerCountry, riskBoardView, opponentPlayer);
		if(!isOcuppiedTerritory)
			isOcuppiedTerritory = isWon;
		updateTheBoardScreenData(riskBoardView, RiskGameConstants.ATTACK_PHASE);
		isPlayerWonTheGame();
	}

	/**
	 * getCard method checks whether the attacker player gets the cards if so it allows the player to get a card,
	 * if the attacker removed all the opponent player from the game he gets all his cards.
	 * @param riskBoardView,instance of {@link RiskBoardView} object
	 */
	private void getCard(RiskBoardView riskBoardView) {
		if(isOcuppiedTerritory) {
			int selectedCardNumber = -1;
			do {
				Object [] possibilities = new Object [cardsList.size()];
				for(int index = 0; index < possibilities.length; index++) {
					possibilities[index] = index+1;
				}
				if(possibilities.length >= 1) {
					selectedCardNumber = (Integer)JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"You have won the a card. Please take your card",
							"Cards Selection",JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
				}
				if(selectedCardNumber > 0) {
					Card selectedCard = cardsList.get(selectedCardNumber);
					cardsList.remove(selectedCardNumber);
					playersData.get(currentPlayerIndex).addCardToPlayer(selectedCard);
				}
			}while(selectedCardNumber == 0);
			isOcuppiedTerritory = false;
		}
	}

	/**
	 * isPlayerWonTheGame method checks whether the player won the game or not.
	 * if player own the game it announces and stop the game
	 */
	private void isPlayerWonTheGame() {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		if(currentPlayer.getTerritoryOccupied().size() == countriesList.size()) {
			showErrorMessage(currentPlayer.getPlayerName()+" WON THE GAME", true);
		}
	}

	/**
	 * updatePlayersInfo method is used to update the information about percentage of map controlled, 
	 * continents controlled and total armies controlled by all player
	 * @param players, list of all the players in the game
	 * @param view, instance of {@link RiskBoardView} object
	 */
	private void updatePlayersInfo(List<Player> players, RiskBoardView view) {
		for(int i = 0; i < players.size(); i++) {
			updatePlayerInfo(players.get(i), i, view);
		}
	}

	/**
	 * updatePlayerInfo method is used to update the information of one player
	 * @param player, player that has information to be updated
	 * @param index, player's index
	 * @param view, instance of {@link RiskBoardView} object
	 */
	private void updatePlayerInfo(Player player, int index, RiskBoardView view) {
		double percent;
		String listOfContinent;
		int totalArmies;
		NumberFormat format = NumberFormat.getPercentInstance(Locale.US);
		switch(index) {
		case 0:
			view.getPlayer1Label().setText(player.getPlayerName());
			percent = (double) player.getTerritoryOccupied().size()/countriesList.size();
			view.getPlayer1MapPercentage().setText(format.format(percent));
			listOfContinent = getOwnedContinent(player).trim();
			if(listOfContinent.equals("")) {
				view.getPlayer1ContinentControl().setText(RiskGameConstants.NO_CONTINENTS_OWNED);
			}else {
				view.getPlayer1ContinentControl().setText(listOfContinent);
			}
			totalArmies = getTotalArmies(player);
			view.getPlayer1TotalArmies().setText(Integer.toString(totalArmies));
			break;

		case 1:
			view.getPlayer2Label().setText(player.getPlayerName());
			percent = (double)player.getTerritoryOccupied().size()/countriesList.size();
			view.getPlayer2MapPercentage().setText(format.format(percent));
			listOfContinent = getOwnedContinent(player).trim();
			if(listOfContinent.equals("")) {
				view.getPlayer2ContinentControl().setText(RiskGameConstants.NO_CONTINENTS_OWNED);
			}else {
				view.getPlayer2ContinentControl().setText(listOfContinent);
			}
			totalArmies = getTotalArmies(player);
			view.getPlayer2TotalArmies().setText(Integer.toString(totalArmies));
			break;	

		case 2:
			view.getPlayer3Label().setText(player.getPlayerName());
			percent =(double) player.getTerritoryOccupied().size()/countriesList.size();
			view.getPlayer3MapPercentage().setText(format.format(percent));
			listOfContinent = getOwnedContinent(player).trim();
			if(listOfContinent.equals("")) {
				view.getPlayer3ContinentControl().setText(RiskGameConstants.NO_CONTINENTS_OWNED);
			}else {
				view.getPlayer3ContinentControl().setText(listOfContinent);
			}
			totalArmies = getTotalArmies(player);
			view.getPlayer3TotalArmies().setText(Integer.toString(totalArmies));
			break;	

		case 3:
			view.getPlayer4Label().setText(player.getPlayerName());
			percent = (double)player.getTerritoryOccupied().size()/countriesList.size();
			view.getPlayer4MapPercentage().setText(format.format(percent));
			listOfContinent = getOwnedContinent(player).trim();
			if(listOfContinent.equals("")) {
				view.getPlayer4ContinentControl().setText(RiskGameConstants.NO_CONTINENTS_OWNED);
			}else {
				view.getPlayer4ContinentControl().setText(listOfContinent);
			}
			totalArmies = getTotalArmies(player);
			view.getPlayer4TotalArmies().setText(Integer.toString(totalArmies));
			break;	

		case 4:
			view.getPlayer5Label().setText(player.getPlayerName());
			percent = (double)player.getTerritoryOccupied().size()/countriesList.size();
			view.getPlayer5MapPercentage().setText(format.format(percent));
			listOfContinent = getOwnedContinent(player).trim();
			if(listOfContinent.equals("")) {
				view.getPlayer5ContinentControl().setText(RiskGameConstants.NO_CONTINENTS_OWNED);
			}else {
				view.getPlayer5ContinentControl().setText(listOfContinent);
			}
			totalArmies = getTotalArmies(player);
			view.getPlayer5TotalArmies().setText(Integer.toString(totalArmies));
			break;	

		case 5:
			view.getPlayer6Label().setText(player.getPlayerName());
			percent = (double)player.getTerritoryOccupied().size()/countriesList.size();
			view.getPlayer6MapPercentage().setText(format.format(percent));
			listOfContinent = getOwnedContinent(player).trim();
			if(listOfContinent.equals("")) {
				view.getPlayer6ContinentControl().setText(RiskGameConstants.NO_CONTINENTS_OWNED);
			}else {
				view.getPlayer6ContinentControl().setText(listOfContinent);
			}
			totalArmies = getTotalArmies(player);
			view.getPlayer6TotalArmies().setText(Integer.toString(totalArmies));
			break;	

		}
	}

	/**
	 * getOwnedContinent method is used to get the continents owned a player
	 * @param player, player that needs to get the information
	 * @return ownedContinent, the continents owned by a specific player
	 */
	public String getOwnedContinent(Player player) {
		StringBuilder ownedContinent = new StringBuilder();
		for(Entry<String, Continent> continentKey : continentsMap.entrySet()) {
			List<Country> contriesList = continentKey.getValue().getCountriesInContinent();
			Map<String, List<Country>> playersCountryData = contriesList.stream().collect(Collectors.groupingBy(Country::getPlayerName));
			if(playersCountryData.containsKey(player.getPlayerName()) && 
					playersCountryData.get(player.getPlayerName()).size() == contriesList.size()) {
				ownedContinent.append(continentKey.getKey()).append(" ");
			}
		}
		return ownedContinent.toString();
	}

	/**
	 * getTotalArmies method is used to get the total armies owned by a specific player
	 * @param player, player that needs to get the information
	 * @return result, the total armies owned by a specific player
	 */
	public int getTotalArmies(Player player) {
		int result = 0;
		for(int i = 0; i < player.getTerritoryOccupied().size(); i++) {
			result += player.getTerritoryOccupied().get(i).getArmiesOnCountry();
		}
		return result;
	}

	/**
	 * Enables the buttons required for the fortification phase and all the other buttons will be hidden
	 * {@link RiskBoardView#getEndFortificationBtn()} and {@link RiskBoardView#getMoveArmiesBtn()} will be enabled
	 * @param riskBoardView, instance of {@link RiskBoardView} object
	 */
	public void endAttackPhase(RiskBoardView riskBoardView) {
		getCard(riskBoardView);
		enableDisableButtons(RiskGameConstants.FORTIFICATION_PHASE, riskBoardView);
		updateTheBoardScreenData(riskBoardView, RiskGameConstants.FORTIFICATION_PHASE);
	}

	/**
	 * Return the CountriesMap which contains {@link Country#getCountryName()} as key and respective {@link Country} object as value
	 * @return {@link RiskBoardModel#getContinentsMap()}
	 */
	public Map<String, Continent> getContinentsMap() {
		return continentsMap;
	}

	/**
	 * Return the CountriesList which contains list of {@link Country} objects read from the map file while loading the board
	 * @return {@link RiskBoardModel#getCountriesList()}
	 */
	public List<Country> getCountriesList() {
		return countriesList;
	}

	/**
	 * Return the playersData which contains list of {@link Player} datas
	 * @return {@link RiskBoardModel#getPlayersData()}
	 */
	public List<Player> getPlayersData() {
		return playersData;
	}

	/**
	 * Returns the whether it is inital phase or not
	 * true, if this is the first round for the player.
	 * false, if the player turn is second or more.
	 * @return true, if it initial phase.
	 *        false, if it not initial phase
	 */
	public boolean isInitialPhase() {
		return isInitialPhase;
	}

	/**
	 * Returns the name of the image file
	 * @return imageName, the name of the image file
	 */
	public String getImageName() {
		return imageName;
	}
	
	/**
	 * Return the complete list of cards
	 * @return cardsList, the list contains all of the cards
	 */
	public List<Card> getCardsList() {
		return cardsList;
	}
}

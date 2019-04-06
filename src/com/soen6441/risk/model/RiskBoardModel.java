package com.soen6441.risk.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
import com.soen6441.risk.playerstrategy.AggressiveStrategy;
import com.soen6441.risk.playerstrategy.BenevolentStrategy;
import com.soen6441.risk.playerstrategy.CheaterStrategy;
import com.soen6441.risk.playerstrategy.HumanStrategy;
import com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface;
import com.soen6441.risk.playerstrategy.RandomStrategy;
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
	private boolean isGameResume = false;
	private String isGamePhase = RiskGameConstants.REINFORCEMENT_PHASE;

	/**
	 * loadRequiredData method is used to inital load the riskBoardView screen data
	 * @param isLoadGame, true if the method is triggered from RiskBoardController
	 *                    false if triggered from other methods
	 * @param fileName, name of the file to be loaded to frame
	 */
	public void loadRequiredData(String fileName, boolean isLoadGame){
		File loadFile = new File(fileName);
		String loadingFileName = fileName.substring(fileName.lastIndexOf("/")+1);
		File saveFile = new File(System.getProperty("user.dir")+RiskGameConstants.RESOURCES_FOLDER+"/Save/save_"+loadingFileName);
		boolean loadSaveGame = false;
		if(isLoadGame && saveFile.exists()) {
			int result = JOptionPane.showConfirmDialog(null, "Do you want to load saved game?","Resume Game",JOptionPane.OK_CANCEL_OPTION);
			if(result == 0) {
				loadSaveGame = true;
			}else {
				saveFile.delete();
			}
		}
		try(BufferedReader reader = new BufferedReader(new FileReader(loadFile))) {
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
					findTheSectionToParseData(section, line.trim(), loadSaveGame);
				}
			}
			if(!loadSaveGame) {
				verifyTheMapIsConnected();
				createCardsData();
			}else {
				loadGameFromSaveFile(saveFile);
			}
		} catch (FileNotFoundException e) {
			showErrorMessage("Problem with the file. Couldn't read the file", true);
		} catch (IOException e) {
			showErrorMessage("Problem while reading the file data", true);
		}
	}

	/**
	 * loadGameFromSaveFile method loads the data from the save file
	 * @param saveFile, game loading file which was saved.
	 * 
	 */
	private void loadGameFromSaveFile(File saveFile) {
		isGameResume = true;
		playersData =  new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
			String line;
			int lineCounter = 1;
			while(Objects.nonNull(line = reader.readLine())) {
				if(lineCounter == 1) {
					currentPlayerIndex = Integer.parseInt(line.split("=")[1].trim());
				}else if(lineCounter == 2) {
					isGamePhase = line.split("=")[1].trim();
				}else if(lineCounter == 4) {
					createCardsFromSaveFile(line);
				}else if(lineCounter > 5) {
					createPlayersFromSaveFile(line, lineCounter);
				}
				lineCounter++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * createPlayersFromSaveFile method creates the players from the saved file
	 * @param lineCounter, line number of the file 
	 * @param line, line from the save file
	 */
	private void createPlayersFromSaveFile(String line, int lineCounter) {
		int currentIndex = lineCounter / 6 - 1;
		if(lineCounter % 6 == 0) {
			Player player = new Player(line, 0);
			playersData.add(player);
		}
		if(lineCounter % 6 == 1) {
			playersData.get(currentIndex).setArmyCountAvailable(Integer.parseInt(line.split("=")[1].trim()));
		}else if(lineCounter % 6 == 4) {
			assignCountriesToPlayer(line, playersData.get(currentIndex));
		}else if(lineCounter % 6 == 5) {
			assignCardsToPlayers(line, playersData.get(currentIndex));
		}
	}

	/**
	 * @param line
	 * @param player
	 */
	private void assignCardsToPlayers(String line, Player player) {
		for(String cardTerritoryName: line.split(",")) {
			for(int index = 0; index < cardsList.size(); index++) {
				if(cardsList.get(index).getTerritoryName().equals(cardTerritoryName.trim())) {
					Card selectedCard = cardsList.get(index);
					cardsList.remove(index);
					player.addCardToPlayer(selectedCard);
					break;
				}
			}
		}
	}

	/**
	 * assignCountriesToPlayer method loads the data from the file.
	 * @param line
	 * @param player
	 */
	private void assignCountriesToPlayer(String line, Player player) {
		for(String contryName: line.split(":")) {
			String[] countryData = contryName.split(",");
			countriesMap.get(countryData[0].trim()).setPlayerName(player.getPlayerName());
			countriesMap.get(countryData[0].trim()).setArmiesOnCountry(Integer.parseInt(countryData[1]));
			player.addTerritory(countriesMap.get(countryData[0].trim()));
		}
	}

	/**
	 * createCardsFromSaveFile method create the cards from the save file 
	 * @param cardsInformation contains the cards information from the previous game.
	 */
	private void createCardsFromSaveFile(String cardsInformation) {
		cardsList = new ArrayList<>();
		for(String cardData : cardsInformation.split(":")) {
			String[] cardDataArray = cardData.split(",");
			Card card = new Card(cardDataArray[1].trim(), cardDataArray[0].trim());
			cardsList.add(card);
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
		List<String> countriesNamesList = continentsMap.get(continentName).getCountriesInContinent().stream().map(Country::getCountryName).collect(Collectors.toList());
		for(String countryName : countriesNamesList) {
			List<String> adjacentCountriesNameList = countriesMap.get(countryName).getAdjacentCountries().stream().map(Country::getCountryName).collect(Collectors.toList());
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
			List<String> countriesNamesList = continent.getValue().getCountriesInContinent().stream().map(Country::getCountryName).collect(Collectors.toList());
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
		boolean hasAdjacentOpponent = false;
		if(Objects.nonNull(view.getAdjacentCountryComboBox().getSelectedItem())) {
			hasAdjacentOpponent  = true;
		}
		Player currentPlayer = playersData.get(currentPlayerIndex);
		BufferedImage bufferedImage;
		Country currentCountry = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
		Country adjacentCountry = null;
		if(hasAdjacentOpponent)
			adjacentCountry = countriesMap.get(view.getAdjacentCountryComboBox().getSelectedItem().toString());
		try {
			bufferedImage = ImageIO.read(new File(System.getProperty("user.dir")+"/resources/"+imageName));
			Graphics2D graphics = bufferedImage.createGraphics();
			for(Country country: countriesList) {
				graphics.setColor(Color.BLACK);
				if(hasAdjacentOpponent)
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

	/**
	 * findTheSectionToParseData gets the data from the config file and parses the data based on the section
	 * @param loadSaveGame, true if the game to be loaded from saved game.
	 *                      false if the game to be loaded from normal file
	 * @param section, section=1([MAPS]), section=2([CONTINENTS]), section=3([TERRITORIES])
	 * @param line, each line from the file
	 */
	private void findTheSectionToParseData(int section, String line, boolean loadSaveGame) {
		if(line.trim().equals("")) {
			return;
		}
		if(section == 1) {
			findImageName(line);
		}else if(section == 2) {
			createCountinents(line);
		}else {
			createCountries(line, loadSaveGame);
		}
	}

	/**
	 * createContries is used to create the territories and linked to the adjacent territories
	 * @param loadSaveGame
	 * @param line, country data line from the file
	 */
	private void createCountries(String line, boolean loadSaveGame) {
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
	 * @param behaviors, players behaviours 
	 * @param playersCount, count of the players how many players are playing the game
	 */
	public void assignCountriesToPlayers(int playersCount, String[] behaviors) {
		Random random;
		try {
			random = SecureRandom.getInstanceStrong();
			int index = 0;
			if(Objects.isNull(playersData)) {
				playersData = new ArrayList<>();
				playersMap = new HashMap<>();
				while(index<playersCount) {
					String playerName = "Player "+ (index+1);
					int initalArmiesAssigned = 5 * (10 - playersCount);
					Player player =  new Player(playerName, initalArmiesAssigned);
					player.setPlayerStrategy(getStrategyOfPlayer(behaviors[index]));
					player.setPlayerStrategyName(behaviors[index++]);
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
					player.addTerritory(country);
					assignedCountriesList.remove(countryIndex);
				}
				for(int i = 0; i < playersData.size();i++) {
					playersData.get(i).setArmyCountAvailable(playersData.get(i).getArmyCountAvailable() - playersData.get(i).getTerritoryOccupied().size());
				}
			}
		} catch (NoSuchAlgorithmException e) {
			showErrorMessage("Problem while assigning players. Please restart the game\n"+e.getMessage(), true);
		}
	}

	/**
	 * @param string
	 * @return
	 */
	public PlayerBehaviourStrategyInterface getStrategyOfPlayer(String playerStrategy) {
		PlayerBehaviourStrategyInterface playerBehaviourStrategy;
		switch (playerStrategy) {
		case RiskGameConstants.HUMAN:
			playerBehaviourStrategy = new HumanStrategy();
			break;
		case RiskGameConstants.AGGRESSIVE:
			playerBehaviourStrategy = new AggressiveStrategy();
			break;
		case RiskGameConstants.BENEVOLENT:
			playerBehaviourStrategy = new BenevolentStrategy();
			break;
		case RiskGameConstants.RANDOM:
			playerBehaviourStrategy = new RandomStrategy();
			break;
		case RiskGameConstants.CHEATER:
			playerBehaviourStrategy = new CheaterStrategy();
			break;
		default:
			playerBehaviourStrategy = new HumanStrategy();
			break;
		}
		return playerBehaviourStrategy;
	}

	/**
	 * updateTheBoardScreenData method is used to handle the actions done before the each turn for the player
	 * @param phase, current phase of the player it can be {@link RiskGameConstants#REINFORCEMENT_PHASE}, {@link RiskGameConstants#ATTACK_PHASE}
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 */
	public void updateTheBoardScreenData(RiskBoardView riskBoardView) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		if(!isGameResume  && currentPlayer.getArmyCountAvailable() == 0 && isGamePhase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			setTheBonusArmiesToPlayer(currentPlayer, riskBoardView);
			isInitialPhase = false;
		}
		updatePlayersInfo(playersData, riskBoardView);
		if(isGamePhase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			riskBoardView.getPhaseInfo().setText(RiskGameConstants.REINFORCEMENT_PHASE_INFO);
		}else if(isGamePhase.equals(RiskGameConstants.ATTACK_PHASE)) {
			riskBoardView.getPhaseInfo().setText(RiskGameConstants.ATTACK_PHASE_INFO);
		}else if(isGamePhase.equals(RiskGameConstants.FORTIFICATION_PHASE)) {
			riskBoardView.getPhaseInfo().setText(RiskGameConstants.FORTIFICATION_PHASE_INFO);
		}
		riskBoardView.getCurrentPhase().setText(isGamePhase + " phase");
		enableDisableButtons(isGamePhase, riskBoardView);
		riskBoardView.getCurrentPlayerTurnLabel().setText(currentPlayer.getPlayerName()+" Turn !!");
		riskBoardView.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
		riskBoardView.getCardsCountLabel().setText(String.valueOf(currentPlayer.getPlayerCards().size()));
		if(currentPlayer.getPlayerStrategy() instanceof HumanStrategy) {
			updateCountriesComboBox(currentPlayer, riskBoardView);
			updateCardsTextArea(currentPlayer, riskBoardView);
			createOrUpdateImage(riskBoardView);
		}
		if(!(currentPlayer.getPlayerStrategy() instanceof HumanStrategy))
			playerStrategyActions(currentPlayer, riskBoardView);
	}

	/**
	 * playerStrategyActions method checks the player strategy and takes appropriate actions.
	 * @param riskBoardView 
	 * @param currentPlayer, currentPlayer object
	 */
	private void playerStrategyActions(Player currentPlayer, RiskBoardView riskBoardView) {
		if(!currentPlayer.getTerritoryOccupied().isEmpty()) {
			if(currentPlayer.getPlayerStrategy() instanceof AggressiveStrategy) {
				implementAggressiveStrategy(currentPlayer, riskBoardView);
			}else if(currentPlayer.getPlayerStrategy() instanceof BenevolentStrategy) {
				implementBenevolentStrategy(currentPlayer, riskBoardView);
			}else if(currentPlayer.getPlayerStrategy() instanceof RandomStrategy) {
				implementRandomBehaviour(currentPlayer, riskBoardView);
			}else if(currentPlayer.getPlayerStrategy() instanceof CheaterStrategy) {
				implementCheaterStrategy(currentPlayer, riskBoardView);
			}
		}else {
			System.out.println(currentPlayer.getPlayerName()+" turn skipped as player out of game");
			currentPlayerIndex++;
			currentPlayerIndex = currentPlayerIndex%playersData.size();
		}
		updateTheBoardScreenData(riskBoardView);
	}

	/**
	 * @param currentPlayer
	 * @param riskBoardView
	 */
	private void implementCheaterStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		if(isInitialPhase) {
			placeArmiesToCountries(currentPlayer, riskBoardView);
		}else if(isGamePhase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			placeArmiesCheaterStrategy(currentPlayer, riskBoardView);
			placeArmiesToCountries(currentPlayer, riskBoardView);
			isGamePhase = RiskGameConstants.ATTACK_PHASE;
		}else if(isGamePhase.equalsIgnoreCase(RiskGameConstants.ATTACK_PHASE)) {
			attackForCheaterStrategy(currentPlayer,riskBoardView);
		}else if(isGamePhase.equals(RiskGameConstants.FORTIFICATION_PHASE)) {
			fortificationCheaterStrategy(currentPlayer,riskBoardView);
		}
	}


	/**
	 * @param currentPlayer
	 * @param riskBoardView
	 */
	private void placeArmiesCheaterStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		currentPlayer.getTerritoryOccupied().stream().forEach(country ->{
			country.setArmiesOnCountry(country.getArmiesOnCountry()*2);
		});
	}

	/**
	 * @param currentPlayer
	 * @param riskBoardView
	 */
	private void implementRandomBehaviour(Player currentPlayer, RiskBoardView riskBoardView) {
		if(isInitialPhase) {
			placeArmiesToCountries(currentPlayer, riskBoardView);
		}else if(isGamePhase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			placeArmiesToCountries(currentPlayer, riskBoardView);
			isGamePhase = RiskGameConstants.ATTACK_PHASE;
		} else if(isGamePhase.equalsIgnoreCase(RiskGameConstants.ATTACK_PHASE)) {
			attackForRandomStrategy(currentPlayer,riskBoardView);
		}else if(isGamePhase.equals(RiskGameConstants.FORTIFICATION_PHASE)) {
			fortificationRandomStrategy(currentPlayer,riskBoardView);
		}
	}


	/**
	 * @param currentPlayer 
	 * @param riskBoardView
	 */
	private void fortificationRandomStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		Random random;
		try {
			random = SecureRandom.getInstanceStrong();
			int numberOfFortification = random.nextInt(5);
			for(int index = 0; index < numberOfFortification; index++) {
				Country currentCountry = currentPlayer.getTerritoryOccupied().get(random.nextInt(currentPlayer.getTerritoryOccupied().size()));
				Country adjacentCountry = currentCountry.getAdjacentCountries().get(random.nextInt(currentCountry.getAdjacentCountries().size()));
				currentPlayer.moveArmiesBetweenCountries(currentCountry, adjacentCountry, riskBoardView);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		isGamePhase = RiskGameConstants.REINFORCEMENT_PHASE;
		currentPlayerIndex++;
		currentPlayerIndex = currentPlayerIndex%playersData.size();
	}

	/**
	 * @param currentPlayer2 
	 * @param riskBoardView
	 */
	private void attackForRandomStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		try {
			Random random = SecureRandom.getInstanceStrong();
			int numberOfAttacks = random.nextInt(5);
			for(int index = 0; index < numberOfAttacks; index++) {
				Country currentPlayerCountry = currentPlayer.getTerritoryOccupied().get(random.nextInt(currentPlayer.getTerritoryOccupied().size()));
				Country opponentPlayerCountry = currentPlayerCountry.getAdjacentCountries().get(random.nextInt(currentPlayerCountry.getAdjacentCountries().size()));
				Player opponentPlayer = playersMap.get(opponentPlayerCountry.getPlayerName());
				if(!currentPlayer.getPlayerName().equals(opponentPlayer.getPlayerName())) {
					boolean isWon = currentPlayer.attackBetweenCountries(currentPlayerCountry, opponentPlayerCountry, riskBoardView, opponentPlayer);
					if(!isOcuppiedTerritory)
						isOcuppiedTerritory = isWon;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		isPlayerWonTheGame();
		getCard(riskBoardView);
	}

	/**
	 * @param currentPlayer
	 * @param riskBoardView
	 */
	private void implementBenevolentStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		if(isInitialPhase) {
			placeArmiesToCountries(currentPlayer, riskBoardView);
		}else if(isGamePhase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			placeArmiesForBenevolentStrategy(currentPlayer, riskBoardView);
		}else if(isGamePhase.equalsIgnoreCase(RiskGameConstants.ATTACK_PHASE)) {
			isGamePhase = RiskGameConstants.FORTIFICATION_PHASE;
		}else if(isGamePhase.equals(RiskGameConstants.FORTIFICATION_PHASE)) {
			fortificationBenevolentStrategy(currentPlayer,riskBoardView);
		}
	}

	/**
	 * @param currentPlayer 
	 * @param riskBoardView
	 */
	private void fortificationBenevolentStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		Map<Integer, List<Country>> armiesCountriesList = currentPlayer.getTerritoryOccupied().stream().map(data -> data).collect(Collectors.groupingBy(Country::getArmiesOnCountry));
		List<Country> countriesWithLowArmies = new ArrayList<>();
		int armiesCount = Integer.MAX_VALUE;
		for(Entry<Integer, List<Country>>armiesCountry : armiesCountriesList.entrySet()) {
			if(armiesCount > armiesCountry.getKey()) {
				countriesWithLowArmies = armiesCountry.getValue();
				armiesCount = armiesCountry.getKey();
			}
		}
		for (Country country : countriesWithLowArmies) {
			currentPlayer.moveArmiesBetweenCountries(country, null, riskBoardView);
		}
		isGamePhase = RiskGameConstants.REINFORCEMENT_PHASE;
		currentPlayerIndex++;
		currentPlayerIndex = currentPlayerIndex%playersData.size();
	}

	/**
	 * @param currentPlayer
	 * @param riskBoardView
	 */
	private void placeArmiesForBenevolentStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		if(!currentPlayer.getTerritoryOccupied().isEmpty()) {
			sortTerritoryBasedOnArmies(currentPlayer, true);
			currentPlayer.reinforceArmyToCountry(currentPlayer.getTerritoryOccupied().get(0), riskBoardView, isInitialPhase);
		}
		isGamePhase = RiskGameConstants.ATTACK_PHASE;
	}

	/**
	 * implementTheAggressiveStrategy method implements the aggressive strategy.
	 * @param riskBoardView 
	 * @param currentPlayer, current player object references
	 */
	private void implementAggressiveStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		if(isInitialPhase) {
			placeArmiesToCountries(currentPlayer, riskBoardView);
		}else if(isGamePhase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			placeArmiesForAggressiveStrategy(currentPlayer, riskBoardView);
		}else if(isGamePhase.equals(RiskGameConstants.ATTACK_PHASE)) {
			attackForAggressiveStrategy(riskBoardView);
		}else if(isGamePhase.equals(RiskGameConstants.FORTIFICATION_PHASE)) {
			fortificationAggressiveStrategy(currentPlayer, riskBoardView);
		}
	}

	/**
	 * @param currentPlayer
	 * @param riskBoardView
	 */
	private void fortificationAggressiveStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		sortTerritoryBasedOnArmies(currentPlayer, false);
		if(!currentPlayer.getTerritoryOccupied().isEmpty()) {
			Country highestArmiesCountry = currentPlayer.getTerritoryOccupied().get(0);
			for(Country adjacentCountry:highestArmiesCountry.getAdjacentCountries()) {
				currentPlayer.moveArmiesBetweenCountries(highestArmiesCountry, adjacentCountry, riskBoardView);
			}
		}
		isGamePhase = RiskGameConstants.REINFORCEMENT_PHASE;
		currentPlayerIndex++;
		currentPlayerIndex = currentPlayerIndex%playersData.size();
	}

	/**
	 * @param currentPlayer 
	 * @param riskBoardView
	 */
	public void fortificationCheaterStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		for(Country currentCountry: currentPlayer.getTerritoryOccupied()) {
			currentPlayer.moveArmiesBetweenCountries(currentCountry, null, riskBoardView);
		}
		isGamePhase = RiskGameConstants.REINFORCEMENT_PHASE;
		currentPlayerIndex++;
		currentPlayerIndex = currentPlayerIndex%playersData.size();
	}

	/**
	 * @param currentPlayer 
	 * @param riskBoardView
	 */
	private void attackForCheaterStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		List<String> currentCountriesNamesList = currentPlayer.getTerritoryOccupied().stream().map(Country::getCountryName).collect(Collectors.toList());
		for(String countryName :currentCountriesNamesList) {
			Country currentPlayerCountry = countriesMap.get(countryName);
			List<Country> adjacentCountryList = currentPlayerCountry.getAdjacentCountries();
			for(Country opponentPlayerCountry : adjacentCountryList) {
				if(!currentPlayer.getPlayerName().equals(opponentPlayerCountry.getPlayerName())) {
					boolean isWon = currentPlayer.attackBetweenCountries(currentPlayerCountry, opponentPlayerCountry, riskBoardView, playersMap.get(opponentPlayerCountry.getPlayerName()));
					if(!isOcuppiedTerritory)
						isOcuppiedTerritory = isWon;
				}
			}
		}
		isPlayerWonTheGame();
		getCard(riskBoardView);
	}

	/**
	 * @param riskBoardView
	 */
	private void attackForAggressiveStrategy(RiskBoardView riskBoardView) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		if(!currentPlayer.getTerritoryOccupied().isEmpty()) {
			sortTerritoryBasedOnArmies(currentPlayer, false);
			Country currentPlayerCountry = currentPlayer.getTerritoryOccupied().get(0);
			for(Country opponentPlayerCountry : currentPlayerCountry.getAdjacentCountries()) {
				if(!currentPlayerCountry.getPlayerName().equals(opponentPlayerCountry.getPlayerName())) {
					boolean isWon = currentPlayer.attackBetweenCountries(currentPlayerCountry, opponentPlayerCountry, 
							riskBoardView, playersMap.get(opponentPlayerCountry.getPlayerName()));
					if(!isOcuppiedTerritory)
						isOcuppiedTerritory = isWon;
				}
			}
		}
		isPlayerWonTheGame();
		getCard(riskBoardView);
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
		isGamePhase = RiskGameConstants.ATTACK_PHASE;
		updateTheBoardScreenData(riskBoardView);
		isPlayerWonTheGame();
	}
	/**
	 * @param currentPlayer
	 * @param riskBoardView
	 */
	private void placeArmiesForAggressiveStrategy(Player currentPlayer, RiskBoardView riskBoardView) {
		if(!currentPlayer.getTerritoryOccupied().isEmpty()) {
			sortTerritoryBasedOnArmies(currentPlayer, false);
			currentPlayer.reinforceArmyToCountry(currentPlayer.getTerritoryOccupied().get(0), riskBoardView, isInitialPhase);
		}
		isGamePhase = RiskGameConstants.ATTACK_PHASE;
	}

	/**
	 * updateArmiesInCountries method is used to update the armies in the contries by using a dialog
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 */
	public void updateArmiesInCountries(RiskBoardView riskBoardView) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		Country country = countriesMap.get(riskBoardView.getCountryComboBox().getSelectedItem().toString());
		currentPlayer.reinforceArmyToCountry(country, riskBoardView, isInitialPhase);
		createOrUpdateImage(riskBoardView);
		if(currentPlayer.getArmyCountAvailable() == 0) {
			nextPlayer(riskBoardView);
		}
	}

	/**
	 * sortTerritoryBasedOnArmies methods sorts the territories based on the armies on the country.
	 * @param currentPlayer
	 * @param isAscendingOrder 
	 * @return
	 */
	private void sortTerritoryBasedOnArmies(Player currentPlayer, boolean isAscendingOrder) {
		if(isAscendingOrder)
			currentPlayer.getTerritoryOccupied().sort(Comparator.comparing(Country::getArmiesOnCountry));
		else
			currentPlayer.getTerritoryOccupied().sort(Comparator.comparing(Country::getArmiesOnCountry).reversed());
	}

	/**
	 * placeArmiesToCountries method places the armies on to the countries randomly in the initial phase.
	 * @param currentPlayer
	 * @param riskBoardView 
	 * 
	 */
	private void placeArmiesToCountries(Player currentPlayer, RiskBoardView riskBoardView) {
		Random random;
		try {
			random = SecureRandom.getInstanceStrong();
			while(currentPlayer.getArmyCountAvailable() != 0)
				currentPlayer.reinforceArmyToCountry(currentPlayer.getTerritoryOccupied().get(random.nextInt(currentPlayer.getTerritoryOccupied().size())), riskBoardView, isInitialPhase);
			if(isInitialPhase)
				nextPlayer(riskBoardView);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * updateCardsTextArea method updates the cards which are available to the user on the view.
	 * @param currentPlayer, currentPlayer of the currentplayer.
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 */
	private void updateCardsTextArea(Player currentPlayer, RiskBoardView riskBoardView) {
		StringBuilder cardsBuilder = new StringBuilder();
		List<Card> currentPlayerCards = currentPlayer.getPlayerCards();
		if(!currentPlayerCards.isEmpty()) {
			for(Card card:currentPlayerCards) {
				cardsBuilder.append(card.getArmyType()).append("(").append(card.getTerritoryName()).append(")").append(" ");
			}
		}else {
			cardsBuilder.append("No cards obtained yet ");
		}
		riskBoardView.getCardsObtainedTextArea().setText(cardsBuilder.toString());
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
			int selectedValue = -1;
			if(player.getPlayerCards().size() >= 5) {
				message = "You have to exchange cards. It is mandatory.";
				selectedValue = 0;
			}else {
				message = "Do you want to exchnage cards?";
			}
			if(player.getPlayerStrategy() instanceof HumanStrategy)
				selectedValue = JOptionPane.showConfirmDialog(riskBoardView.getBoardFrame(), message, "Cards Exchange", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

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
					int count = 0;
					int index = 0;
					while(index < playersCards.size()) {
						if(playersCards.get(index).getArmyType().equals(cards.getValue().get(0).getArmyType())) {
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
		for(Entry<String, Continent> continentKey : continentsMap.entrySet()) {
			List<Country> contriesList = continentKey.getValue().getCountriesInContinent();
			Map<String, List<Country>> playersCountryData = contriesList.stream().collect(Collectors.groupingBy(Country::getPlayerName));
			if(playersCountryData.containsKey(currentPlayer.getPlayerName()) && playersCountryData.get(currentPlayer.getPlayerName()).size() == contriesList.size()) {
				bonusArmies += continentKey.getValue().getArmiesGainedAfterConquer();
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
	 * @param riskBoardview, RiskBoardView object used to update the components of the screen
	 */
	public void moveArmiesBetweenCountries(RiskBoardView riskBoardview) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		Country country = countriesMap.get(riskBoardview.getCountryComboBox().getSelectedItem().toString());
		Country adjacentCountry = countriesMap.get(riskBoardview.getAdjacentCountryComboBox().getSelectedItem().toString());
		currentPlayer.moveArmiesBetweenCountries(country, adjacentCountry, riskBoardview);
		createOrUpdateImage(riskBoardview);
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
			Player player = playersData.get(currentPlayerIndex);
			if(player.getPlayerStrategy() instanceof HumanStrategy)
				JOptionPane.showMessageDialog(view.getBoardFrame(), "Next Player Turn");
			isGamePhase = RiskGameConstants.REINFORCEMENT_PHASE;
			updateTheBoardScreenData(view);
		}else {
			isGamePhase = RiskGameConstants.ATTACK_PHASE;
			enableDisableButtons(RiskGameConstants.ATTACK_PHASE, view);
			updateTheBoardScreenData(view);
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
	 * getCard method checks whether the attacker player gets the cards if so it allows the player to get a card,
	 * if the attacker removed all the opponent player from the game he gets all his cards.
	 * @param riskBoardView,instance of {@link RiskBoardView} object
	 */
	private void getCard(RiskBoardView riskBoardView) {
		int selectedCardNumber = -1;
		Player player = playersData.get(currentPlayerIndex);
		if(isOcuppiedTerritory && player.getPlayerStrategy() instanceof HumanStrategy) {
			do {
				Object [] possibilities = new Object [cardsList.size()];
				for(int index = 0; index < possibilities.length; index++) {
					possibilities[index] = index+1;
				}
				if(possibilities.length >= 1) {
					selectedCardNumber = (Integer)JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"You have won the a card. Please take your card",
							"Cards Selection",JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
				}
			}while(selectedCardNumber == 0);
		}
		if(isOcuppiedTerritory && !(player.getPlayerStrategy() instanceof HumanStrategy)) {
			Random random;
			try {
				random = SecureRandom.getInstanceStrong();
				selectedCardNumber = random.nextInt(cardsList.size());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(selectedCardNumber > 0) {
			Card selectedCard = cardsList.get(selectedCardNumber);
			cardsList.remove(selectedCardNumber);
			playersData.get(currentPlayerIndex).addCardToPlayer(selectedCard);
			System.out.println(player.getPlayerName()+" got card "+ selectedCard.getArmyType()+" "+selectedCard.getTerritoryName());
		}
		isOcuppiedTerritory = false;
		isGamePhase = RiskGameConstants.FORTIFICATION_PHASE;
	}

	/**
	 * isPlayerWonTheGame method checks whether the player won the game or not.
	 * if player own the game it announces and stop the game
	 */
	public void isPlayerWonTheGame() {
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
		isGamePhase = RiskGameConstants.FORTIFICATION_PHASE;
		updateTheBoardScreenData(riskBoardView);
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

	/**
	 * Set the phase of the game
	 * @param isGamePhase, the variable contains the current phase
	 */
	public void setIsGamePhase(String isGamePhase) {
		this.isGamePhase = isGamePhase;
	}

	/**
	 * @param view
	 * @param mapFileName 
	 */
	public void saveGameCurrentState(RiskBoardView view, String mapFileName) {
		JOptionPane.showMessageDialog(null, "Save is under development");
		if(Objects.nonNull(mapFileName)) {
			File saveFile = new File(System.getProperty("user.dir")+RiskGameConstants.RESOURCES_FOLDER+"/Save/"+"save_"+mapFileName+RiskGameConstants.MAP_FILE_EXTENSION);
			try {
				if(!saveFile.exists()) {
					saveFile.createNewFile();
				}
				FileWriter writer = new FileWriter(saveFile);

				savePlayersData(writer, view);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * savePlayersData method saves the players data to save the game.
	 * @param view 
	 * @param writer, writer object to write to the file
	 * @throws IOException 
	 */
	private void savePlayersData(FileWriter writer, RiskBoardView view) throws IOException {
		writer.write("Current Player Index="+currentPlayerIndex+"\n");
		writer.write("CurrentPhase="+getCurrentPhase(view)+"\n");
		writer.write("[CARDS]\n");
		StringBuilder cardsData = new StringBuilder();
		cardsList.stream().forEach(data -> {
			cardsData.append(data.getArmyType()).append(",").append(data.getTerritoryName()).append(":");
		});
		writer.write(cardsData.toString()+"\n\n");
		for(Player player:playersData) {
			writer.write(player.getPlayerName()+"\n");
			writer.write("Army Count ="+player.getArmyCountAvailable()+"\n");
			writer.write("Conutry Count = "+player.getTerritoryOccupied().size()+"\n");
			writer.write("Cards Count = "+player.getPlayerCards().size()+"\n");
			StringBuilder countryNames = new StringBuilder();
			for( Country country:player.getTerritoryOccupied()) {
				countryNames.append(country.getCountryName()).append(",").append(country.getArmiesOnCountry()).append(":");
			}
			writer.write(countryNames.toString().substring(0, countryNames.toString().length() - 1)+"\n");
			String cardsNames = player.getPlayerCards().stream().map(Card :: getTerritoryName).collect(Collectors.joining(","));
			writer.write(cardsNames+"\n");
		}
	}

	/**
	 * getCurrentPhase method gets the current phase of the player.
	 * @param riskBoardView 
	 * @return currentPhase.
	 */
	private String getCurrentPhase(RiskBoardView riskBoardView) {
		if(riskBoardView.getReinforceBtn().isVisible())
			return RiskGameConstants.REINFORCEMENT_PHASE;
		else if(riskBoardView.getAttackBtn().isVisible())
			return RiskGameConstants.ATTACK_PHASE;
		else if(riskBoardView.getEndFortificationBtn().isVisible())
			return RiskGameConstants.FORTIFICATION_PHASE;
		return null;
	}
}

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
import java.util.Observable;
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
 * <b>RiskBoardModel</b>
 * RiskBoardModel class contains the logics for the events done on the {@link RiskBoardModel} by the player
 * @author Naga Satish Reddy
 * @author An Nguyen
 */
public class RiskBoardModel extends Observable{
	private String imageName;
	private Map<String, Continent> continentsMap;
	private Map<String, Country> countriesMap;
	private List<Country> countriesList;
	private List<Player> playersData;
	private Map<String, Player> playersMap;
	private int currentPlayerIndex = 0;
	private boolean isInitialPhase = true;
	private ImageIcon mapImageIcon;

	/**
	 * loadRequiredData method is used to inital load the riskBoardView screen data
	 * @param fileName, name of the file to be loaded to frame
	 */
	public void loadRequiredData(String fileName){
		File configFile = new File(fileName);
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
					findTheSectionToParseData(section, line.trim());
				}
			}
			verifyTheCountriesConnections();
		} catch (FileNotFoundException e) {
			showErrorMessage("Problem with the file. Couldn't read the file", true);
		} catch (IOException e) {
			showErrorMessage("Problem while reading the file data", true);
		}finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					showErrorMessage("Problem while clsoing the file \n"+fileName, true);
				}
		}
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
		if(view.getAdjacentCountryComboBox().getSelectedItem() != null) {
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
				mapImageIcon = new ImageIcon(scaledImage);
				setChanged();
				notifyObservers(this);
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
			showErrorMessage("Problem while parsing the data",true);
		}
	}

	/**
	 * createCountinents method is to create the continents by reading the data from the file.
	 * @param line, is the each continent information from the map file line by line
	 */
	public void createCountinents(String line) {
		if(continentsMap == null) {
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
	private void findImageName(String line) {
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
			if(playersData == null) {
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
	 * @param view, RiskBoardView object used to update the components of the screen
	 */
	public void updateTheBoardScreenData(RiskBoardView view, String phase) {
		Player currentPlayer = playersData.get(currentPlayerIndex);
		if(currentPlayer.getArmyCountAvailable() == 0 && phase.equals(RiskGameConstants.REINFORCEMENT_PHASE)) {
			setTheBonusArmiesToPlayer(currentPlayer);
			isInitialPhase = false;
		}
		enableDisableButtons(phase, view);
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
	public int countArmiesBasedOnTerritories(Player currentPlayer) {
		return getBonusArmiesOnTerritories(currentPlayer)+ getBonusArmiesOnContinent(currentPlayer);
	}

	/**
	 * getBonusArmiesOnContinent method is used to find the bonus armies based on the continent
	 * @param currentPlayer, current player object who is playing @see {@link Player}
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
	 * @param currentPlayer, current player object who is playing @see {@link Player}
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
	 * @param country, one of the country from the {@link RiskBoardView#getCountryComboBox()}
	 * @param adjacentCountry, one of the country from the {@link RiskBoardView#getAdjacentCountryComboBox()}
	 * @return true, if the both country and adjacent countries belong to current player
	 * 		   false, if the both country and adjacent countries doesn't belong to current player  
	 */
	private boolean isCountriesOwnedByPlayers(Country country, Country adjacentCountry) {
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
	 * endFortificationPhase method is used to trigger next player fortification turn.
	 * @param view, instance of {@link RiskBoardView} object
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

		Country currentPlayerCountry = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
		Country opponentPlayerCountry = countriesMap.get(view.getAdjacentCountryComboBox().getSelectedItem().toString());
		
		int currentPlayerDicesToRoll = getNumberOfDicesPlayerWantsToThrow(currentPlayerCountry.getArmiesOnCountry() - 1, view);
		int opponentPlayerDicesToRoll = getNumberOfDicesPlayerWantsToThrow(opponentPlayerCountry.getArmiesOnCountry(), view);
		
		if(currentPlayerDicesToRoll != 0 && opponentPlayerDicesToRoll != 0) {
			int currentPlayerAttackingArmies = currentPlayerDicesToRoll;
			currentPlayerCountry.decreaseArmiesOnCountry(currentPlayerAttackingArmies);
			int opponentDefendingArmies = opponentPlayerDicesToRoll;
			opponentPlayerCountry.decreaseArmiesOnCountry(opponentDefendingArmies);
			while(currentPlayerAttackingArmies != 0 && opponentDefendingArmies != 0) {
				Integer[] currentPlayerDice;
				try {
					currentPlayerDice = new Dice().diceRoll(currentPlayerDicesToRoll);
					Integer[] opponentPlayerDice = new Dice().diceRoll(opponentPlayerDicesToRoll);
					if(currentPlayerDice[0] > opponentPlayerDice[0])
						opponentDefendingArmies--;
					else
						currentPlayerAttackingArmies--;
					if(opponentPlayerDice.length > 1 && currentPlayerDice.length > 1) {
						if(currentPlayerDice[1] > opponentPlayerDice[1])
							opponentDefendingArmies--;
						else
							currentPlayerAttackingArmies--;
					}
					if(currentPlayerAttackingArmies > 0 && opponentPlayerCountry.getArmiesOnCountry() == 0) {
						playersMap.get(opponentPlayerCountry.getPlayerName()).getTerritoryOccupied().remove(opponentPlayerCountry);
						currentPlayer.getTerritoryOccupied().add(opponentPlayerCountry);
						opponentPlayerCountry.setPlayerName(currentPlayer.getPlayerName());
						opponentPlayerCountry.setArmiesOnCountry(currentPlayerAttackingArmies);
						JOptionPane.showMessageDialog(view.getBoardFrame(), currentPlayer.getPlayerName()+" WON ");
					}else if(currentPlayerAttackingArmies == 0 && opponentPlayerCountry.getArmiesOnCountry() > 0){
						opponentPlayerCountry.setArmiesOnCountry(opponentDefendingArmies);
						JOptionPane.showMessageDialog(view.getBoardFrame(), opponentPlayerCountry.getPlayerName()+" WON ");
					}
					updateTheBoardScreenData(view, RiskGameConstants.ATTACK_PHASE);
				} catch (NoSuchAlgorithmException e) {
					showErrorMessage("Problem while throwing dices", false);
				}
			}
			if(currentPlayer.getTerritoryOccupied().size() == countriesList.size()) {
				JOptionPane.showMessageDialog(view.getBoardFrame(), currentPlayer.getPlayerName()+" WON THE GAME");
				System.exit(0);
			}
		}else {
			showErrorMessage("Dices should be selected to attack", false);
		}
	}

	/**
	 * getNumberOfDicesPlayerWantsToThrow method is used to get the number of armies an player wants to use in the attack phase
	 * @param riskBoardView,instance of {@link RiskBoardView} object
	 * @param playerAttackingArmies, number armies available to attack or defend on the country.
	 * @return numberOfDices, number of dices to be used to throw.
	 */
	private int getNumberOfDicesPlayerWantsToThrow(Integer playerAttackingArmies, RiskBoardView riskBoardView) {
		Object [] possibilities = new Object [playerAttackingArmies > 3 ? 3: playerAttackingArmies];
		for(int index = 0; index < possibilities.length; index++) {
			possibilities[index] = index+1;
		}
		Integer dicesToThrow = 0;
		if(possibilities.length > 1) {
			dicesToThrow = (Integer)JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"How many dices you want to thow", "Dices To Throw",
					JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
		}
		return dicesToThrow != null ? dicesToThrow : 0;
	}

	/**
	 * Enables the buttons required for the fortification phase and all the other buttons will be hidden
	 * {@link RiskBoardView#getEndFortificationBtn()} and {@link RiskBoardView#getMoveArmiesBtn()} will be enabled
	 * @param view, instance of {@link RiskBoardView} object
	 */
	public void endAttackPhase(RiskBoardView view) {
		enableDisableButtons(RiskGameConstants.FORTIFICATION_PHASE, view);
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

	public ImageIcon getMapImageIcon() {
		return mapImageIcon;
	}
}

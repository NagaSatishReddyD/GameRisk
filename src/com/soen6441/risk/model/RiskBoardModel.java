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

	public void loadRequiredData(RiskBoardView view) throws IOException {
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
					findTheSectionToParseData(section, line, view);
				}
			}
			createOrUpdateImage(view);
		} catch (FileNotFoundException e) {
			System.out.println("Problem with the file. Couldn't read the file");
		} catch (IOException e) {
			System.out.println("Problem while reading the file data");
		}finally {
			if(reader != null)
				reader.close();
		}
	}

	private void createOrUpdateImage(RiskBoardView view) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(System.getProperty("user.dir")+"/resources/"+imageName));
			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.setColor(Color.BLACK);
			for(Country country: countriesList) {
				graphics.drawString(String.valueOf(country.getArmiesOnCountry()), country.getxCoordinate(), country.getyCoordinate());
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
	 * @param line
	 * @param view 
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
			createContries(line, view);
		}
	}

	/**
	 * createContries is used to create the territories and linked to the respective continents;
	 * @param line
	 * @param view 
	 */
	private void createContries(String line, RiskBoardView view) {
		if(countriesMap == null) {
			countriesMap = new HashMap<>();
			countriesList = new ArrayList<>();
		}
		String[] territoryDetails = line.split(",");
		String countryName = territoryDetails[0];
		String continentName = territoryDetails[3];
		Country country = new Country(countryName);
		country.setxCoordinate(Integer.parseInt(territoryDetails[1]));
		country.setyCoordinate(Integer.parseInt(territoryDetails[2]));
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
	 * @param view 
	 */
	private void findImageName(String line, RiskBoardView view) {
		if(line.contains("image")) {
			imageName = line.substring(line.indexOf('=')+1);
		}
	}

	public void assignCountriesToPlayers(int playersCount) throws NoSuchAlgorithmException {
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
	}

	/**
	 * updateTheBoardScreenData method is used to handle the actions done before the each turn for the player
	 * @param currentPlayerValue
	 * @param view
	 */
	public void updateTheBoardScreenData(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex % playersData.size());
		if(currentPlayer.getArmyCountAvailable() == 0) {
			int reinforcement = (int) Math.floor(currentPlayer.getTerritoryOccupied().size() / (double)3);
			if(reinforcement >= 3) {
				currentPlayer.setArmyCountAvailable(reinforcement);
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
		createOrUpdateImage(view);
	}

	/**
	 * updateCountriesComboBox method is used to update the countries list based on the player turn
	 * @param currentPlayer
	 * @param view
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

		if(selectedValue != null) {
			Country country = countriesMap.get(view.getCountryComboBox().getSelectedItem().toString());
			country.setArmiesOnCountry(selectedValue);
			currentPlayer.decrementArmy(selectedValue);
			view.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
			createOrUpdateImage(view);
			if(currentPlayer.getArmyCountAvailable() == 0) {
				JOptionPane.showMessageDialog(view.getBoardFrame(), "Next Player Turn");
				nextPlayer(view);
			}
		}
	}

	/**
	 * updateTheFortificationData method is used to handle the actions done before the each player's fortification
	 * phase turn
	 * @param view
	 */
	public void updateFortificationData(RiskBoardView view) {
		Player currentPlayer = playersData.get(currentPlayerIndex % playersData.size());
		view.getReinforceBtn().setVisible(false);
		view.getMoveArmiesBtn().setVisible(true);
		view.getEndFortificationBtn().setVisible(true);
		view.getCurrentPlayerTurnLabel().setText(currentPlayer.getPlayerName()+" Turn !!");
		view.getArmiesCountAvailableLabel().setText(String.valueOf(currentPlayer.getArmyCountAvailable()));
		updateCountriesComboBox(currentPlayer, view);
		createOrUpdateImage(view);
	}

	/**
	 * moveArmiesBetweenInCountries method is used to move armies between neighboring countries
	 * @param view
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

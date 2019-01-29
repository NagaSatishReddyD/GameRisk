package com.soen6441.risk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardFrame extends JFrame {

	private JPanel contentPane;
	BoardFrame frame;
	JLabel playerTurnLabel;
	
	List<Player> playersData = new ArrayList<>();
	/**
	 * Create the frame.
	 * @param playersCount 
	 */
	public BoardFrame(String playersCount) {
		frame = this;
		System.out.println(playersCount);
		frame.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1800, 900);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		playerTurnLabel = new JLabel("");
		contentPane.add(playerTurnLabel);
		assignCountriesToPlayers(Integer.parseInt(playersCount));
		startGame();
	}
	
	/**
	 * startGame method starts the actual game after the initial setup.
	 */
	private void startGame() {
		initialeReinforcementSetup();
	}

	private void initialeReinforcementSetup() {
		playerTurnLabel.setText("Player ");
	}

	/**
	 * assignCountriesToPlayers method assigns the territories or countries to player which starting the game.
	 * @param playersCount 
	 */
	public void assignCountriesToPlayers(int playersCount) {
		int index = 0;
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
				Country country = new Country(countriesName[index]);
				Player player = playersData.get(index % playersCount);
				player.addTerritory(player, country);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Countries file not found...");
		} catch (IOException e) {
			System.out.println("Something goes wrong while reading countries file..");
		}
		
		//printing players assigned countries
		playersData.stream().forEach(player -> {
			System.out.println("------------------------");
			System.out.println(player.getPlayerName());
			player.getTerritoryOccupied().stream().forEach(data -> System.out.println(data.getCountryName()));
			System.out.println("------------------------");
		});
	}

}

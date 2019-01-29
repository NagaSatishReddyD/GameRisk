package com.soen6441.risk;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BoardFrame extends JFrame {

	private JPanel contentPane;
	BoardFrame frame;
	
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		assignCountriesToPlayers(Integer.parseInt(playersCount));
		
	}
	
	/**
	 * assignCountriesToPlayers method assigns the territories or countries to player which starting the game.
	 * @param playersCount 
	 */
	public void assignCountriesToPlayers(int playersCount) {
		int index = 0;
		while(index<playersCount) {
			playersData.add(new Player("Player "+ ++index));
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

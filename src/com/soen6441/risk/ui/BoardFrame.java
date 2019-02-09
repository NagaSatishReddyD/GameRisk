package com.soen6441.risk.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameInitialLogicalImpl;

/**
 * BoardFrame class is the main frame of the class and has the event handlers of the triggerings of the class
 * @author Naga Satish Reddy
 *
 */
public class BoardFrame extends JFrame {

	private JPanel contentPane;
	BoardFrame frame;
	JLabel playerTurnLabel;
	List<Country> countriesList = new ArrayList<>();
	
	List<Player> playersData = new ArrayList<>();
	RiskGameInitialLogicalImpl initialLogicImpl = new RiskGameInitialLogicalImpl();
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
		initialLogicImpl.loadRequiredData(playersCount, playersData, countriesList);
		
		//printing players assigned countries
				playersData.stream().forEach(player -> {
					System.out.println("------------------------");
					System.out.println(player.getPlayerName());
					player.getTerritoryOccupied().stream().forEach(data -> System.out.println(data.getCountryName()));
					System.out.println("------------------------");
				});
		
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
}

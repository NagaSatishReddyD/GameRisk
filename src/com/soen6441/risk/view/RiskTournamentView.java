package com.soen6441.risk.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.soen6441.risk.RiskGameConstants;

/**
 * RiskTournamentView class contains components for user to choose the input for tournament mode
 * @author An Nguyen
 */
public class RiskTournamentView extends JFrame{
	JFrame tournamentFrame;
	private JLabel mapCountLabel;
	private JLabel gameCountLabel;
	private JComboBox gameCountCombo;
	private JComboBox mapCountCombo;
	private JLabel playerCountLabel;
	private JComboBox playerCountCombo;
	private JLabel player1Label;
	private JLabel player2Label;
	private JLabel player3Label;
	private JLabel player4Label;
	private JComboBox player1StrategyCombo;
	private JComboBox player2StrategyCombo;
	private JComboBox player3StrategyCombo;
	private JComboBox player4StrategyCombo;
	private JButton runTournamentButton;
	private JLabel turnCountLabel;
	private JComboBox turnCountCombo;
	/**
	 * The constructor will create the frame that contains components to allow
	 * users to choose each player strategy behavior to play the game
	 * <p>
	 * This frame will be visible after the user click the Tournament Mode button in RiskGameView frame
	 * </p>
	 */
	public RiskTournamentView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] behavior = {RiskGameConstants.AGGRESSIVE, RiskGameConstants.BENEVOLENT, 
				RiskGameConstants.RANDOM, RiskGameConstants.CHEATER};
		
		String[] gamesAndMapsCount = {"1", "2", "3", "4", "5"};
		String[] playerCount = {"2", "3", "4"};
		String[] turnCounts = {
				"10", "11", "12", "13", "14", "15", "16", "17", 
				"18", "19", "20", "21", "22", "23", "24", "25", 
				"26", "27", "28", "29", "30", "31", "32", "33", 
				"34", "35", "36", "37", "38", "39", "40", "41", 
				"42", "43", "44", "45", "46", "47", "48", "49", 
				"50", };
		tournamentFrame = new JFrame("Tournament Mode");
		tournamentFrame.setVisible(true);
		tournamentFrame.getContentPane().setLayout(null);
		tournamentFrame.setSize(300, 400);
		
		mapCountLabel = new JLabel("Number of maps");
		mapCountLabel.setBounds(6, 34, 114, 16);
		tournamentFrame.getContentPane().add(mapCountLabel);
		
		gameCountLabel = new JLabel("Number of games");
		gameCountLabel.setBounds(6, 62, 114, 16);
		tournamentFrame.getContentPane().add(gameCountLabel);
		
		gameCountCombo = new JComboBox(gamesAndMapsCount);
		gameCountCombo.setBounds(137, 58, 125, 27);
		tournamentFrame.getContentPane().add(gameCountCombo);
		
		mapCountCombo = new JComboBox(gamesAndMapsCount);
		mapCountCombo.setBounds(137, 30, 125, 27);
		tournamentFrame.getContentPane().add(mapCountCombo);
		
		playerCountLabel = new JLabel("Player numbers");
		playerCountLabel.setBounds(6, 132, 114, 16);
		tournamentFrame.getContentPane().add(playerCountLabel);
		
		playerCountCombo = new JComboBox(playerCount);
		playerCountCombo.setBounds(137, 128, 125, 27);
		tournamentFrame.getContentPane().add(playerCountCombo);
		
		player1Label = new JLabel("Player 1");
		player1Label.setBounds(6, 171, 61, 16);
		tournamentFrame.getContentPane().add(player1Label);
		
		player2Label = new JLabel("Player 2");
		player2Label.setBounds(6, 210, 61, 16);
		tournamentFrame.getContentPane().add(player2Label);
		
		player3Label = new JLabel("Player 3");
		player3Label.setBounds(6, 248, 61, 16);
		player3Label.setVisible(false);
		tournamentFrame.getContentPane().add(player3Label);
		
		player4Label = new JLabel("Player 4");
		player4Label.setBounds(6, 287, 61, 16);
		player4Label.setVisible(false);
		tournamentFrame.getContentPane().add(player4Label);
		
		player1StrategyCombo = new JComboBox(behavior);
		player1StrategyCombo.setBounds(137, 167, 125, 27);
		tournamentFrame.getContentPane().add(player1StrategyCombo);
		
		player2StrategyCombo = new JComboBox(behavior);
		player2StrategyCombo.setBounds(137, 206, 125, 27);
		tournamentFrame.getContentPane().add(player2StrategyCombo);
		
		player3StrategyCombo = new JComboBox(behavior);
		player3StrategyCombo.setBounds(137, 244, 125, 27);
		player3StrategyCombo.setVisible(false);
		tournamentFrame.getContentPane().add(player3StrategyCombo);
		
		player4StrategyCombo = new JComboBox(behavior);
		player4StrategyCombo.setBounds(137, 283, 125, 27);
		player4StrategyCombo.setVisible(false);
		tournamentFrame.getContentPane().add(player4StrategyCombo);
		
		runTournamentButton = new JButton("Run Tournament");
		runTournamentButton.setBounds(65, 323, 140, 29);
		tournamentFrame.getContentPane().add(runTournamentButton);
		
		turnCountLabel = new JLabel("Number of turns");
		turnCountLabel.setBounds(6, 92, 114, 16);
		tournamentFrame.getContentPane().add(turnCountLabel);
		
		turnCountCombo = new JComboBox(turnCounts);
		turnCountCombo.setBounds(137, 88, 125, 27);
		tournamentFrame.getContentPane().add(turnCountCombo);	
	}
	/**
	 * Return the turnCountLabel object set on this frame
	 * @return the turnCountLabel of this frame
	 */
	public JLabel getTurnCountLabel() {
		return turnCountLabel;
	}
	
	/**
	 * Return the turnCountCombo object set on this frame
	 * @return the turnCountCombo of this frame
	 */
	public JComboBox getTurnCountCombo() {
		return turnCountCombo;
	}
	
	/**
	 * Return the tournamentFrame object set on this frame
	 * @return the tournamentFrame object
	 */
	public JFrame getTournamentFrame() {
		return tournamentFrame;
	}
	
	/**
	 * Return the mapCountLabel object set on this frame
	 * @return the mapCountLabel of this frame
	 */
	public JLabel getMapCountLabel() {
		return mapCountLabel;
	}
	
	/**
	 * Return the gameCountLabel object set on this frame
	 * @return the gameCountLabel of this frame
	 */
	public JLabel getGameCountLabel() {
		return gameCountLabel;
	}
	
	/**
	 * Return the gameCountCombo object set on this frame
	 * @return the gameCountCombo of this frame
	 */
	public JComboBox getGameCountCombo() {
		return gameCountCombo;
	}
	
	/**
	 * Return the mapCountCombo object set on this frame
	 * @return the mapCountCombo of this frame
	 */
	public JComboBox getMapCountCombo() {
		return mapCountCombo;
	}
	
	/**
	 * Return the playerCountLabel object set on this frame
	 * @return the playerCountLabel of this frame
	 */
	public JLabel getPlayerCountLabel() {
		return playerCountLabel;
	}
	
	/**
	 * Return the playerCountCombo object set on this frame
	 * @return the playerCountCombo of this frame
	 */
	public JComboBox getPlayerCountCombo() {
		return playerCountCombo;
	}
	
	/**
	 * Return the player1Label object set on this frame
	 * @return the player1Label of this frame
	 */
	public JLabel getPlayer1Label() {
		return player1Label;
	}
	
	/**
	 * Return the player2Label object set on this frame
	 * @return the player2Label of this frame
	 */
	public JLabel getPlayer2Label() {
		return player2Label;
	}
	
	/**
	 * Return the player3Label object set on this frame
	 * @return the player3Label of this frame
	 */
	public JLabel getPlayer3Label() {
		return player3Label;
	}
	
	/**
	 * Return the player4Label object set on this frame
	 * @return the player4Label of this frame
	 */
	public JLabel getPlayer4Label() {
		return player4Label;
	}
	
	/**
	 * Return the player1StrategyCombo object set on this frame
	 * @return the player1StrategyCombo of this frame
	 */
	public JComboBox getPlayer1StrategyCombo() {
		return player1StrategyCombo;
	}
	
	/**
	 * Return the player2StrategyCombo object set on this frame
	 * @return the player2StrategyCombo of this frame
	 */
	public JComboBox getPlayer2StrategyCombo() {
		return player2StrategyCombo;
	}
	
	/**
	 * Return the player3StrategyCombo object set on this frame
	 * @return the player3StrategyCombo of this frame
	 */
	public JComboBox getPlayer3StrategyCombo() {
		return player3StrategyCombo;
	}
	
	/**
	 * Return the player4StrategyCombo object set on this frame
	 * @return the player4StrategyCombo of this frame
	 */
	public JComboBox getPlayer4StrategyCombo() {
		return player4StrategyCombo;
	}
	
	/**
	 * Return the runTournamentButton object set on this frame
	 * @return the runTournamentButton of this frame
	 */
	public JButton getRunTournamentButton() {
		return runTournamentButton;
	}
	
}

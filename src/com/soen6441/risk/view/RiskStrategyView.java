package com.soen6441.risk.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.soen6441.risk.RiskGameConstants;

/**
 * RiskStrategyView class contains components for users to choose each player behavior
 * @author An Nguyen
 */
public class RiskStrategyView extends JFrame{
	private JFrame strategyFrame;
	private JButton loadGameButton;
	private JLabel player1Label;
	private JLabel player2Label;
	private JLabel player3Label;
	private JLabel player4Label;
	private JLabel player5Label;
	private JLabel player6Label;
	private JComboBox<String> player1StrategyCombo;
	private JComboBox<String> player2StrategyCombo;
	private JComboBox<String> player3StrategyCombo;
	private JComboBox<String> player4StrategyCombo;
	private JComboBox<String> player5StrategyCombo;
	private JComboBox<String> player6StrategyCombo;

	/**
	 * The constructor will create the frame that contains components to allow
	 * users to choose each player strategy behavior to play the game
	 * <p>
	 * This frame will be visible after the user click the Next button in RiskPlayerView frame
	 * </p>
	 */
	public RiskStrategyView() {
		
		strategyFrame = new JFrame("Strategy Selection");
		strategyFrame.setSize(400,250);
		strategyFrame.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		strategyFrame.getContentPane().setLayout(null);
		
		String[] behavior = {RiskGameConstants.HUMAN, RiskGameConstants.AGGRESSIVE, RiskGameConstants.BENEVOLENT, 
				RiskGameConstants.RANDOM, RiskGameConstants.CHEATER};
		loadGameButton = new JButton("Load Game");
		loadGameButton.setBounds(152, 181, 117, 29);
		strategyFrame.getContentPane().add(loadGameButton);
		
		player1Label = new JLabel("Player 1");
		player1Label.setBounds(24, 6, 69, 16);
		strategyFrame.getContentPane().add(player1Label);
		
		player2Label = new JLabel("Player 2");
		player2Label.setBounds(24, 34, 61, 16);
		strategyFrame.getContentPane().add(player2Label);
		
		player3Label = new JLabel("Player 3");
		player3Label.setBounds(24, 62, 61, 16);
		strategyFrame.getContentPane().add(player3Label);
		
		player4Label = new JLabel("Player 4");
		player4Label.setBounds(24, 90, 61, 16);
		strategyFrame.getContentPane().add(player4Label);
		
		player5Label = new JLabel("Player 5");
		player5Label.setBounds(24, 118, 61, 16);
		strategyFrame.getContentPane().add(player5Label);
		
		player6Label = new JLabel("Player 6");
		player6Label.setBounds(24, 146, 61, 16);
		strategyFrame.getContentPane().add(player6Label);
		
		player1StrategyCombo = new JComboBox<>(behavior);
		player1StrategyCombo.setBounds(152, 2, 117, 27);
		strategyFrame.getContentPane().add(player1StrategyCombo);
		
		player2StrategyCombo = new JComboBox<>(behavior);
		player2StrategyCombo.setBounds(152, 30, 117, 27);
		strategyFrame.getContentPane().add(player2StrategyCombo);
		
		player3StrategyCombo = new JComboBox<>(behavior);
		player3StrategyCombo.setBounds(152, 58, 117, 27);
		strategyFrame.getContentPane().add(player3StrategyCombo);
		
		player4StrategyCombo = new JComboBox<>(behavior);
		player4StrategyCombo.setBounds(152, 86, 117, 27);
		strategyFrame.getContentPane().add(player4StrategyCombo);
		
		player5StrategyCombo = new JComboBox<>(behavior);
		player5StrategyCombo.setBounds(152, 114, 117, 27);
		strategyFrame.getContentPane().add(player5StrategyCombo);
		
		player6StrategyCombo = new JComboBox<>(behavior);
		player6StrategyCombo.setBounds(152, 142, 117, 27);
		strategyFrame.getContentPane().add(player6StrategyCombo);
	}

	/**
	 * Return the loadGameButton object set on this frame
	 * @return the loadGameButton of this frame
	 */
	public JButton getLoadGameButton() {
		return loadGameButton;
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
	 * Return the player5Label object set on this frame
	 * @return the player5Label of this frame
	 */
	public JLabel getPlayer5Label() {
		return player5Label;
	}

	/**
	 * Return the player6Label object set on this frame
	 * @return the player6Label of this frame
	 */
	public JLabel getPlayer6Label() {
		return player6Label;
	}

	/**
	 * Return player1StrategyComBo object set on this frame
	 * @return player1StrategyCombo object of this frame
	 */
	public JComboBox<String> getPlayer1StrategyCombo() {
		return player1StrategyCombo;
	}

	/**
	 * Return player2StrategyComBo object set on this frame
	 * @return player2StrategyCombo object of this frame
	 */
	public JComboBox<String> getPlayer2StrategyCombo() {
		return player2StrategyCombo;
	}

	/**
	 * Return player3StrategyComBo object set on this frame
	 * @return player3StrategyCombo object of this frame
	 */
	public JComboBox<String> getPlayer3StrategyCombo() {
		return player3StrategyCombo;
	}

	/**
	 * Return player4StrategyComBo object set on this frame
	 * @return player4StrategyCombo object of this frame
	 */
	public JComboBox<String> getPlayer4StrategyCombo() {
		return player4StrategyCombo;
	}

	/**
	 * Return player5StrategyComBo object set on this frame
	 * @return player5StrategyCombo object of this frame
	 */
	public JComboBox<String> getPlayer5StrategyCombo() {
		return player5StrategyCombo;
	}

	/**
	 * Return player6StrategyComBo object set on this frame
	 * @return player6StrategyCombo object of this frame
	 */
	public JComboBox<String> getPlayer6StrategyCombo() {
		return player6StrategyCombo;
	}
	
	/**
	 * Return the frame object set on this frame
	 * @return the frame object
	 */
	public JFrame getStrategyFrame() {
		return strategyFrame;
	}

}

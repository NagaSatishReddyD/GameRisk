package com.soen6441.risk.view;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * <b> RiskGameView</b>
 * RiskGameView class cotains the components of the for players to start the game or add a new map options.
 * <p>
 * <b>Start Game</b>
 * Start Game boots the {@link RiskPlayerView} frame
 * <b>Add Map</b>
 * Add Map boots the {@link RiskAddMapView} frame
 * </p>
 * @author Naga Satish Reddy
 */
public class RiskGameView extends JFrame {

	private JFrame gameStartframe;
	private JButton startGameButton;
	private JButton loadMapButton;
	private JButton tournamentButton;

	/**
	 * This constructor creates a new frame which contains the components of two buttons
	 * to start the game and to add a new map to the game.
	 * <p>
	 * <b>Start Game</b>
	 * Start Game boots the {@link RiskPlayerView} frame
	 * <b>Add Map</b>
	 * Add Map boots the {@link RiskAddMapView} frame
	 * </p>
	 */
	public RiskGameView() {
		
		gameStartframe = new JFrame("Risk Game");
		gameStartframe.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameStartframe.setSize(500, 200);
		gameStartframe.getContentPane().setLayout(null);
		
		startGameButton = new JButton("Single Mode");
		startGameButton.setSize(97, 21);
		startGameButton.setLocation(189, 27);
		
		gameStartframe.getContentPane().add(startGameButton);
		
		loadMapButton = new JButton("Add Map");
		loadMapButton.setBounds(189, 101, 97, 21);
		gameStartframe.getContentPane().add(loadMapButton);
		
		tournamentButton = new JButton("Tournament Mode");
		tournamentButton.setBounds(150, 60, 175, 29);
		gameStartframe.getContentPane().add(tournamentButton);
	}

	/**
	 * Returns the tournamentButton object set on this frame
	 * @return the tournamentButton object of this frame
	 */
	public JButton getTournamentButton() {
		return tournamentButton;
	}

	/**
	 * Returns the loadMapButton object set on this frame.
	 * @return the loadMapButton object of this frame
	 */
	public JButton getLoadMapButton() {
		return loadMapButton;
	}

	/**
	 * Returns the frame object set on this frame.
	 * @return the frame object of this frame
	 */
	public JFrame getGameStartframe() {
		return gameStartframe;
	}

	/**
	 * Returns the startGameButton object set on this frame.
	 * @return the startGameButton object of this frame
	 */
	public JButton getStartGameButton() {
		return startGameButton;
	}
}

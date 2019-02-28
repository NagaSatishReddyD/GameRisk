package com.soen6441.risk.view;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * RiskGameView is class contains the view of the frame of the initial game start
 * @author Naga Satish Reddy
 * @since 1.0
 */
public class RiskGameView extends JFrame {

	private JFrame gameStartframe;
	private JButton startGameButton;
	private JButton loadMapButton;

	/**
	 * RiskGameView constructor creates the frame of the initial game start frame
	 */
	public RiskGameView() {
		
		gameStartframe = new JFrame("Risk Game");
		gameStartframe.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameStartframe.setSize(500, 200);
		gameStartframe.getContentPane().setLayout(null);
		
		startGameButton = new JButton("Start Button");
		startGameButton.setSize(97, 21);
		startGameButton.setLocation(189, 27);
		
		gameStartframe.getContentPane().add(startGameButton);
		
		loadMapButton = new JButton("Add Map");
		loadMapButton.setBounds(189, 58, 97, 21);
		gameStartframe.getContentPane().add(loadMapButton);
	}

	/**
	 * getLoadMapButton method is used to get the instance of the loadMapButton.
	 * @return loadMapButton instance
	 */
	public JButton getLoadMapButton() {
		return loadMapButton;
	}

	/**
	 * getGameStartframe method is used to get the instance of the starting game frame
	 * @return JFrame instance
	 */
	public JFrame getGameStartframe() {
		return gameStartframe;
	}

	/**
	 * getStartGameButton method is used to get the instance of the start game button.
	 * @return startGameButton instance
	 */
	public JButton getStartGameButton() {
		return startGameButton;
	}
}

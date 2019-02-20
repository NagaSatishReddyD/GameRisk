package com.soen6441.risk.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * RiskGameView is class contains the view of the frame of the initail game start
 * @author Naga Satish Reddy
 *
 */
public class RiskGameView extends JFrame {

	private JFrame gameStartframe;
	private JButton startGameButton;

	/**
	 * Create the frame.
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
		
		JButton loadMapButton = new JButton("Add Map");
		loadMapButton.setBounds(189, 58, 97, 21);
		gameStartframe.getContentPane().add(loadMapButton);
	}

	public JFrame getGameStartframe() {
		return gameStartframe;
	}

	public void setGameStartframe(JFrame gameStartframe) {
		this.gameStartframe = gameStartframe;
	}

	public JButton getStartGameButton() {
		return startGameButton;
	}

	public void setStartGameButton(JButton startGameButton) {
		this.startGameButton = startGameButton;
	}
}

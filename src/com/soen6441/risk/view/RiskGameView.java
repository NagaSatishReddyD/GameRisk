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

	private JPanel contentPane;
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
		contentPane = new JPanel();
		
		startGameButton = new JButton("Start Button");
		contentPane.add(startGameButton, BorderLayout.CENTER);
		
		gameStartframe.add(contentPane);
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
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

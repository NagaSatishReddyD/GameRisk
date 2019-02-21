package com.soen6441.risk.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RiskPlayerView extends JFrame {

	private JFrame playerStartframe;
	private JComboBox playerCountCombo;
	private JButton loadGameButton;


	/**
	 * Create the frame.
	 */
	public RiskPlayerView() {
		playerStartframe = new JFrame("Risk Game");
		playerStartframe.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerStartframe.setSize(500, 200);
		playerStartframe.getContentPane().setLayout(null);
		
		JLabel playerCountLabel = new JLabel("Player Count");
		playerCountLabel.setBounds(166, 21, 97, 21);
		playerStartframe.getContentPane().add(playerCountLabel);
		
		String [] playersNumbers = {"2","3","4","5","6"};
		playerCountCombo = new JComboBox(playersNumbers);
		playerCountCombo.setSelectedIndex(0);
		playerCountCombo.setBounds(286, 21, 97, 21);
		playerStartframe.getContentPane().add(playerCountCombo);
		
		
		loadGameButton = new JButton("Load Game");
		loadGameButton.setBounds(170, 62, 213, 21);
		playerStartframe.getContentPane().add(loadGameButton);
	}

	public JFrame getPlayerStartframe() {
		return playerStartframe;
	}


	public void setPlayerStartframe(JFrame playerStartframe) {
		this.playerStartframe = playerStartframe;
	}


	public JComboBox getPlayerCountCombo() {
		return playerCountCombo;
	}


	public void setPlayerCountCombo(JComboBox playerCountCombo) {
		this.playerCountCombo = playerCountCombo;
	}


	public JButton getLoadGameButton() {
		return loadGameButton;
	}


	public void setLoadGameButton(JButton loadGameButton) {
		this.loadGameButton = loadGameButton;
	}
}

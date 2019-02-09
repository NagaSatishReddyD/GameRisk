package com.soen6441.risk.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RiskPlayerView extends JFrame {

	private JPanel contentPane;
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
		contentPane = new JPanel();
		
		
		playerStartframe.getContentPane().add(contentPane);
		
		JLabel playerCountLabel = new JLabel("Player Count");
		String [] playersNumbers = {"2","3","4","5","6"};
		playerCountCombo = new JComboBox(playersNumbers);
		playerCountCombo.setSelectedIndex(0);
		
		loadGameButton = new JButton("Load Game");
		
		contentPane.add(playerCountLabel);
		contentPane.add(playerCountCombo);
		contentPane.add(loadGameButton);
	}


	public JPanel getContentPane() {
		return contentPane;
	}


	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
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

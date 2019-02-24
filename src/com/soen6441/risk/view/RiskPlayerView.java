package com.soen6441.risk.view;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RiskPlayerView extends JFrame {

	private JFrame playerStartframe;
	private JComboBox playerCountCombo;
	private JButton loadGameButton;
	private JComboBox mapComboBox;


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
		playerCountCombo.setBounds(286, 21, 121, 21);
		playerStartframe.getContentPane().add(playerCountCombo);
		
		
		loadGameButton = new JButton("Load Game");
		loadGameButton.setBounds(166, 118, 213, 21);
		playerStartframe.getContentPane().add(loadGameButton);
		
		JLabel mapLabel = new JLabel("Choose map");
		mapLabel.setBounds(166, 74, 78, 16);
		playerStartframe.getContentPane().add(mapLabel);
		
		String[] filesName = null;
		filesName = getAllMapFiles(filesName);
		mapComboBox = new JComboBox(filesName);
		
		mapComboBox.setBounds(286, 70, 121, 27);
		playerStartframe.getContentPane().add(mapComboBox);
	}

	public JComboBox getMapComboBox() {
		return mapComboBox;
	}

	public void setMapComboBox(JComboBox mapComboBox) {
		this.mapComboBox = mapComboBox;
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
	
	public String[] getAllMapFiles(String[] filesName) {
		File directory = new File(System.getProperty("user.dir")+"/resources/");
		File[] files = directory.listFiles((dir,name) -> name.endsWith(".map"));
		filesName = new String[files.length];
		for(int i = 0; i < files.length; i++) {
			filesName[i] = files[i].getName();
		}
		return filesName;
	}
}

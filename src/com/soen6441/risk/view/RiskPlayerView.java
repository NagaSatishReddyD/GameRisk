package com.soen6441.risk.view;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.soen6441.risk.RiskGameConstants;

/**
 * RiskPlayerView class contains the components for players to select the player count and map to start the game
 * @author Naga Satish Reddy
 *
 */
public class RiskPlayerView extends JFrame {

	private JFrame playerStartframe;
	private JComboBox playerCountCombo;
	private JButton loadGameButton;
	private JComboBox mapComboBox;


	/**
	 * RiskPlayerView constructor creates the frame
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
		
		String[] filesName = getAllMapFiles();
		mapComboBox = new JComboBox(filesName);
		
		mapComboBox.setBounds(286, 70, 121, 27);
		playerStartframe.getContentPane().add(mapComboBox);
	}
	
	/**
	 * getAllMapFiles method is to get the all the map names from the resources folder
	 * @return fileNames, the file names string array from the resources folder
	 */
	public String[] getAllMapFiles() {
		File directory = new File(System.getProperty("user.dir")+"/resources/");
		File[] files = directory.listFiles((dir,name) -> name.endsWith(RiskGameConstants.MAP_FILE_EXTENSION));
		String[] filesName = new String[files.length];
		for(int i = 0; i < files.length; i++) {
			filesName[i] = files[i].getName().substring(0, files[i].getName().lastIndexOf('.'));
		}
		return filesName;
	}

	/**
	 * getMapComboBox method is to get the MapComboBox component
	 * @return JComboBox, MapComboBox instance
	 */
	public JComboBox getMapComboBox() {
		return mapComboBox;
	}

	/**
	 * getPlayerStartframe method is to get the frame component
	 * @return JFrame, player start frame instance
	 */
	public JFrame getPlayerStartframe() {
		return playerStartframe;
	}

	/**
	 * 
	 * @return
	 */
	public JComboBox getPlayerCountCombo() {
		return playerCountCombo;
	}

	public JButton getLoadGameButton() {
		return loadGameButton;
	}

}

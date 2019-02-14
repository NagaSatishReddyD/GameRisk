package com.soen6441.risk.view;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RiskBoardView extends JFrame {

	private JPanel contentPane;
	private JFrame boardFrame;
	private JComboBox countryComboBox;
	private JLabel currentPlayerTurnLabel;
	private JComboBox adjacentCountryComboBox;
	private Label armiesCountAvailableLabel;
	private JButton reinforceBtn;
	private JButton attackBtn;
	private JButton moveArmiesBtn;
	private JButton endFortificationBtn;
	private JEditorPane continentTextArea;

	/**
	 * Create the frame.
	 */
	public RiskBoardView() {
		boardFrame = new JFrame("Risk Game");
		boardFrame.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimention = Toolkit.getDefaultToolkit().getScreenSize();
		boardFrame.setSize(dimention.width, dimention.height);
		contentPane = new JPanel();

		boardFrame.getContentPane().add(contentPane);
		contentPane.setLayout(null);

		JPanel continentPanel = new JPanel();
		continentPanel.setBounds(616, 6, 387, 799);
		contentPane.add(continentPanel);
		continentPanel.setLayout(null);

		continentTextArea = new JEditorPane("text/html","");
		continentTextArea.setBounds(10, 5, 367, 784);
		continentTextArea.setEditable(false);
		URL url= RiskBoardView.class.getResource("test.htm");
		try {   
			continentTextArea.setPage(url);
		} catch (IOException e) { 
		}
		JScrollPane jScrollPane = new JScrollPane(continentTextArea);
		jScrollPane.setBounds(10, 5, 367, 784);

		continentPanel.add(jScrollPane);

		currentPlayerTurnLabel = new JLabel("Player 1 ");
		currentPlayerTurnLabel.setBounds(23, 17, 110, 16);
		contentPane.add(currentPlayerTurnLabel);

		countryComboBox = new JComboBox();
		countryComboBox.setBounds(183, 75, 129, 27);
		contentPane.add(countryComboBox);

		adjacentCountryComboBox = new JComboBox();
		adjacentCountryComboBox.setBounds(183, 172, 129, 27);
		contentPane.add(adjacentCountryComboBox);

		reinforceBtn = new JButton("Reinforce");
		reinforceBtn.setBounds(364, 12, 117, 29);
		contentPane.add(reinforceBtn);

		attackBtn = new JButton("Attack");
		attackBtn.setBounds(364, 74, 117, 29);
		attackBtn.setVisible(false);
		contentPane.add(attackBtn);

		moveArmiesBtn = new JButton("Move Armies");
		moveArmiesBtn.setBounds(364, 146, 117, 29);
		moveArmiesBtn.setVisible(false);
		contentPane.add(moveArmiesBtn);

		JLabel adjacentCountriesLabel = new JLabel("Adjacent Countries");
		adjacentCountriesLabel.setBounds(23, 177, 138, 16);
		contentPane.add(adjacentCountriesLabel);

		JLabel numberOfArmiesLabel = new JLabel("Number Of Armies");
		numberOfArmiesLabel.setBounds(10, 295, 132, 22);
		contentPane.add(numberOfArmiesLabel);

		armiesCountAvailableLabel = new Label();
		armiesCountAvailableLabel.setBounds(148, 295, 88, 27);
		contentPane.add(armiesCountAvailableLabel);

		Label ownCountriesLabel = new Label("Own Countries");
		ownCountriesLabel.setBounds(23, 75, 119, 22);
		contentPane.add(ownCountriesLabel);
		
		endFortificationBtn = new JButton("End Fortification");
		endFortificationBtn.setBounds(364, 215, 117, 29);
		endFortificationBtn.setVisible(false);
		contentPane.add(endFortificationBtn);
	}


	public JLabel getCurrentPlayerTurnLabel() {
		return currentPlayerTurnLabel;
	}


	public void setCurrentPlayerTurnLabel(JLabel currentPlayerTurnLabel) {
		this.currentPlayerTurnLabel = currentPlayerTurnLabel;
	}


	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JFrame getBoardFrame() {
		return boardFrame;
	}

	public void setBoardFrame(JFrame boardFrame) {
		this.boardFrame = boardFrame;
	}

	public JComboBox getCountryComboBox() {
		return countryComboBox;
	}

	public void setCountryComboBox(JComboBox countryComboBox) {
		this.countryComboBox = countryComboBox;
	}


	public JComboBox getAdjacentCountryComboBox() {
		return adjacentCountryComboBox;
	}


	public void setAdjacentCountryComboBox(JComboBox adjacentCountryComboBox) {
		this.adjacentCountryComboBox = adjacentCountryComboBox;
	}


	public Label getArmiesCountAvailableLabel() {
		return armiesCountAvailableLabel;
	}


	public void setArmiesCountAvailableLabel(Label armiesCountAvailableLabel) {
		this.armiesCountAvailableLabel = armiesCountAvailableLabel;
	}


	public JButton getReinforceBtn() {
		return reinforceBtn;
	}


	public void setReinforceBtn(JButton reinforceBtn) {
		this.reinforceBtn = reinforceBtn;
	}


	public JButton getAttackBtn() {
		return attackBtn;
	}


	public void setAttackBtn(JButton attackBtn) {
		this.attackBtn = attackBtn;
	}


	public JButton getMoveArmiesBtn() {
		return moveArmiesBtn;
	}


	public void setMoveArmiesBtn(JButton moveArmiesBtn) {
		this.moveArmiesBtn = moveArmiesBtn;
	}
	
	public JButton getEndFortificationBtn() {
		return endFortificationBtn;
	}

	public void setEndFortificationBtn(JButton endFortificationBtn) {
		this.endFortificationBtn = endFortificationBtn;
	}

	public JEditorPane getContinentTextArea() {
		return continentTextArea;
	}


	public void setContinentTextArea(JEditorPane continentTextArea) {
		this.continentTextArea = continentTextArea;
	}
}

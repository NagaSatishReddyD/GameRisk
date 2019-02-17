package com.soen6441.risk.view;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RiskBoardView extends JFrame {

	private JFrame boardFrame;
	private JComboBox countryComboBox;
	private JLabel currentPlayerTurnLabel;
	private JComboBox adjacentCountryComboBox;
	private Label armiesCountAvailableLabel;
	private JButton reinforceBtn;
	private JButton attackBtn;
	private JButton moveArmiesBtn;
	private JButton endFortificationBtn;
	private JPanel mapPanel;
	private JLabel imageLabel;
	private JButton endAttackButton;

	/**
	 * Create the frame.
	 */
	public RiskBoardView() {
		boardFrame = new JFrame("Risk Game");
		boardFrame.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimention = Toolkit.getDefaultToolkit().getScreenSize();
		boardFrame.setSize(dimention.width, dimention.height);
		boardFrame.getContentPane().setLayout(null);

		currentPlayerTurnLabel = new JLabel("Player 1 ");
		currentPlayerTurnLabel.setBounds(23, 17, 110, 16);
		boardFrame.getContentPane().add(currentPlayerTurnLabel);

		countryComboBox = new JComboBox();
		countryComboBox.setBounds(183, 75, 129, 27);
		boardFrame.getContentPane().add(countryComboBox);

		adjacentCountryComboBox = new JComboBox();
		adjacentCountryComboBox.setBounds(183, 172, 129, 27);
		boardFrame.getContentPane().add(adjacentCountryComboBox);

		reinforceBtn = new JButton("Reinforce");
		reinforceBtn.setBounds(364, 12, 117, 29);
		boardFrame.getContentPane().add(reinforceBtn);

		attackBtn = new JButton("Attack");
		attackBtn.setBounds(364, 74, 117, 29);
		attackBtn.setVisible(false);
		boardFrame.getContentPane().add(attackBtn);

		moveArmiesBtn = new JButton("Move Armies");
		moveArmiesBtn.setBounds(364, 187, 117, 29);
		moveArmiesBtn.setVisible(false);
		boardFrame.getContentPane().add(moveArmiesBtn);

		JLabel adjacentCountriesLabel = new JLabel("Adjacent Countries");
		adjacentCountriesLabel.setBounds(23, 177, 138, 16);
		boardFrame.getContentPane().add(adjacentCountriesLabel);

		JLabel numberOfArmiesLabel = new JLabel("Number Of Armies");
		numberOfArmiesLabel.setBounds(10, 295, 132, 22);
		boardFrame.getContentPane().add(numberOfArmiesLabel);

		armiesCountAvailableLabel = new Label();
		armiesCountAvailableLabel.setBounds(148, 295, 88, 27);
		boardFrame.getContentPane().add(armiesCountAvailableLabel);

		Label ownCountriesLabel = new Label("Own Countries");
		ownCountriesLabel.setBounds(23, 75, 119, 22);
		boardFrame.getContentPane().add(ownCountriesLabel);
		
		endFortificationBtn = new JButton("End Fortification");
		endFortificationBtn.setBounds(364, 247, 117, 29);
		endFortificationBtn.setVisible(false);
		boardFrame.getContentPane().add(endFortificationBtn);
		
		mapPanel = new JPanel();
		mapPanel.setBounds(552, 24, 1300, 850);
		imageLabel = new JLabel();
		mapPanel.add(imageLabel);
		boardFrame.getContentPane().add(mapPanel);
		
		endAttackButton = new JButton("Attack Finished");
		endAttackButton.setBounds(364, 136, 117, 27);
		boardFrame.getContentPane().add(endAttackButton);
	}

	public JLabel getImageLabel() {
		return imageLabel;
	}

	public void setImageLabel(JLabel imageLabel) {
		this.imageLabel = imageLabel;
	}

	public JLabel getCurrentPlayerTurnLabel() {
		return currentPlayerTurnLabel;
	}


	public void setCurrentPlayerTurnLabel(JLabel currentPlayerTurnLabel) {
		this.currentPlayerTurnLabel = currentPlayerTurnLabel;
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

	public JPanel getMapPanel() {
		return mapPanel;
	}

	public void setMapPanel(JPanel mapPanel) {
		this.mapPanel = mapPanel;
	}

	public JButton getEndAttackButton() {
		return endAttackButton;
	}

	public void setEndAttackButton(JButton endAttackButton) {
		this.endAttackButton = endAttackButton;
	}
	
}

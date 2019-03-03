package com.soen6441.risk.view;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <b>RiskBoardView</b>
 * RiskBoardView class contains the main board of the game which contains the
 * map and the buttons to play the game.
 * <p>
 * <b>Buttons</b>
 * <b>Reinforcement:</b> To place armies in their respective countries which is available in @see {@link RiskBoardView#getCountryComboBox()}
 * <b>Attack:</b> To attack between the countries which is in @see {@link RiskBoardView#getCountryComboBox()} and @see {@link RiskBoardView#getAdjacentCountryComboBox()}
 * <b>End Attack:</b> To finish the attack phase @see {@link RiskBoardView#getEndAttackButton()}
 * <b>Fortification:</b> To move armies between the countries among between the countries in 
 * between the countries in the combo boxes between @see {@link RiskBoardView#getCountryComboBox()} and @see {@link RiskBoardView#getAdjacentCountryComboBox()}
 * <b>End Fortification:</b> To end the fortification pahse and next player turn starts @see {@link RiskBoardView#getEndFortificationBtn()}
 * <p>
 * <b>Map</b>
 * Shows the image of the map and the armies on to view on the map based on the player actions.
 * </p>  
 * </p>
 * @author Naga Satish Reddy
 *
 */
public class RiskBoardView extends JFrame {

	private JFrame boardFrame;
	private JComboBox<String> countryComboBox;
	private JLabel currentPlayerTurnLabel;
	private JComboBox<String> adjacentCountryComboBox;
	private Label armiesCountAvailableLabel;
	private JButton reinforceBtn;
	private JButton attackBtn;
	private JButton moveArmiesBtn;
	private JButton endFortificationBtn;
	private JPanel mapPanel;
	private JLabel imageLabel;
	private JButton endAttackButton;

	/**
	 * This constructor create the new frame which contains the components which is used to play the game
	 * like mapview and buttons to play the game
	 * <p>
	 * <b>Buttons</b>
	 * <b>Reinforcement:</b> To place armies in their respective countries which is available in @see {@link RiskBoardView#getCountryComboBox()}
	 * <b>Attack:</b> To attack between the countries which is in @see {@link RiskBoardView#getCountryComboBox()} and @see {@link RiskBoardView#getAdjacentCountryComboBox()}
	 * <b>End Attack:</b> To finish the attack phase @see {@link RiskBoardView#getEndAttackButton()}
	 * <b>Fortification:</b> To move armies between the countries among between the countries in 
	 * between the countries in the combo boxes between @see {@link RiskBoardView#getCountryComboBox()} and @see {@link RiskBoardView#getAdjacentCountryComboBox()}
	 * <b>End Fortification:</b> To end the fortification pahse and next player turn starts @see {@link RiskBoardView#getEndFortificationBtn()}
	 * <p>
	 * <b>Map</b>
	 * Shows the image of the map and the armies on to view on the map based on the player actions.
	 * </p>  
	 * </p>
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

		countryComboBox = new JComboBox<>();
		countryComboBox.setBounds(183, 75, 129, 27);
		boardFrame.getContentPane().add(countryComboBox);

		adjacentCountryComboBox = new JComboBox<>();
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
		numberOfArmiesLabel.setBounds(23, 295, 119, 22);
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

	/**
	 * Returns the imageLabel object set on this frame, labelImage contains the bufferedImage data.
	 * @return the loadMapButton object of this frame
	 */
	public JLabel getImageLabel() {
		return imageLabel;
	}

	/**
	 * Returns the currentPlayerTurn object set on this frame, it shows the current player turn
	 * @return the currentPlayerTurn object of this frame
	 */
	public JLabel getCurrentPlayerTurnLabel() {
		return currentPlayerTurnLabel;
	}

	/**
	 * Returns the frame object set on this frame.
	 * @return the frame object of this frame
	 */
	public JFrame getBoardFrame() {
		return boardFrame;
	}

	/**
	 * Returns the countryComboBox object set on this frame.
	 * @return the countryComboBox object of this frame
	 */
	public JComboBox<String> getCountryComboBox() {
		return countryComboBox;
	}

	/**
	 * Returns the adjacentCountryComboBox object set on this frame.
	 * @return the adjacentCountryComboBox object of this frame
	 */
	public JComboBox<String> getAdjacentCountryComboBox() {
		return adjacentCountryComboBox;
	}

	/**
	 * Returns the armiesCountrAvailable object set on this frame.
	 * @return the armiesCountrAvailable object of this frame
	 */
	public Label getArmiesCountAvailableLabel() {
		return armiesCountAvailableLabel;
	}

	/**
	 * Returns the reinforceButton object set on this frame.
	 * @return the reinforceButton object of this frame
	 */
	public JButton getReinforceBtn() {
		return reinforceBtn;
	}

	/**
	 * Returns the attackButton object set on this frame.
	 * @return the attackButton object of this frame
	 */
	public JButton getAttackBtn() {
		return attackBtn;
	}

	/**
	 * Returns the moveArmiesButton object set on this frame.
	 * @return the moveArmiesButton object of this frame
	 */
	public JButton getMoveArmiesBtn() {
		return moveArmiesBtn;
	}

	/**
	 * Returns the endFortificationButton object set on this frame.
	 * @return the endFortificationButton object of this frame
	 */
	public JButton getEndFortificationBtn() {
		return endFortificationBtn;
	}

	/**
	 * Returns the mapPanel object set on this frame, this panel contains the map image
	 * @return the mapPanel object of this frame
	 */
	public JPanel getMapPanel() {
		return mapPanel;
	}

	/**
	 * Returns the endAttackButton object set on this frame.
	 * @return the endAttackButton object of this frame
	 */
	public JButton getEndAttackButton() {
		return endAttackButton;
	}
}

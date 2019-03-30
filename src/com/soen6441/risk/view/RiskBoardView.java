package com.soen6441.risk.view;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
 * @author Naga Satish Reddy
 *
 */
public class RiskBoardView extends JFrame{

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
	private JLabel currentPhase;
	private JLabel phaseInfo;
	private JLabel player1Label;
	private JLabel player1MapPercentage;
	private JLabel player1ContinentControl;
	private JLabel player1TotalArmies;
	private JLabel player2Label;
	private JLabel player2MapPercentage;
	private JLabel player2ContinentControl;
	private JLabel player2TotalArmies;
	private JLabel player3Label;
	private JLabel player3MapPercentage;
	private JLabel player3ContinentControl;
	private JLabel player3TotalArmies;
	private JLabel player4Label;
	private JLabel player4MapPercentage;
	private JLabel player4ContinentControl;
	private JLabel player4TotalArmies;
	private JLabel player5Label;
	private JLabel player5MapPercentage;
	private JLabel player5ContinentControl;
	private JLabel player5TotalArmies;
	private JLabel player6Label;
	private JLabel player6MapPercentage;
	private JLabel player6ContinentControl;
	private JLabel player6TotalArmies;
	private JLabel cardsCountLabel;
	private JTextArea cardsObtainedTextArea;
	
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
		mapPanel.setBounds(552, 24, 1300, 667);
		imageLabel = new JLabel();
		mapPanel.add(imageLabel);
		boardFrame.getContentPane().add(mapPanel);
		
		endAttackButton = new JButton("Attack Finished");
		endAttackButton.setBounds(364, 136, 117, 27);
		boardFrame.getContentPane().add(endAttackButton);
		
		currentPhase = new JLabel("Reinforcement phase");
		currentPhase.setBounds(183, 17, 169, 16);
		boardFrame.getContentPane().add(currentPhase);
		
		phaseInfo = new JLabel();
		phaseInfo.setBounds(23, 45, 517, 16);
		boardFrame.getContentPane().add(phaseInfo);
		
		player1Label = new JLabel();
		player1Label.setBounds(23, 339, 61, 16);
		boardFrame.getContentPane().add(player1Label);
		
		player1MapPercentage = new JLabel();
		player1MapPercentage.setBounds(23, 379, 61, 16);
		boardFrame.getContentPane().add(player1MapPercentage);
		
		player1ContinentControl = new JLabel();
		player1ContinentControl.setBounds(180, 379, 172, 16);
		boardFrame.getContentPane().add(player1ContinentControl);
		
		player1TotalArmies = new JLabel();
		player1TotalArmies.setBounds(381, 379, 61, 16);
		boardFrame.getContentPane().add(player1TotalArmies);
		
		player2Label = new JLabel();
		player2Label.setBounds(23, 422, 61, 16);
		boardFrame.getContentPane().add(player2Label);
		
		player2MapPercentage = new JLabel();
		player2MapPercentage.setBounds(23, 461, 61, 16);
		boardFrame.getContentPane().add(player2MapPercentage);
		
		player2ContinentControl = new JLabel();
		player2ContinentControl.setBounds(180, 461, 172, 16);
		boardFrame.getContentPane().add(player2ContinentControl);
		
		player2TotalArmies = new JLabel();
		player2TotalArmies.setBounds(381, 461, 61, 16);
		boardFrame.getContentPane().add(player2TotalArmies);
		
		player3Label = new JLabel();
		player3Label.setBounds(23, 507, 61, 16);
		boardFrame.getContentPane().add(player3Label);
		
		player3MapPercentage = new JLabel();
		player3MapPercentage.setBounds(23, 547, 61, 16);
		boardFrame.getContentPane().add(player3MapPercentage);
		
		player3ContinentControl = new JLabel();
		player3ContinentControl.setBounds(183, 547, 169, 16);
		boardFrame.getContentPane().add(player3ContinentControl);
		
		player3TotalArmies = new JLabel();
		player3TotalArmies.setBounds(381, 547, 61, 16);
		boardFrame.getContentPane().add(player3TotalArmies);
		
		player4Label = new JLabel();
		player4Label.setBounds(23, 596, 61, 16);
		boardFrame.getContentPane().add(player4Label);
		
		player4MapPercentage = new JLabel();
		player4MapPercentage.setBounds(23, 634, 61, 16);
		boardFrame.getContentPane().add(player4MapPercentage);
		
		player4ContinentControl = new JLabel();
		player4ContinentControl.setBounds(180, 634, 172, 16);
		boardFrame.getContentPane().add(player4ContinentControl);
		
		player4TotalArmies = new JLabel();
		player4TotalArmies.setBounds(381, 634, 61, 16);
		boardFrame.getContentPane().add(player4TotalArmies);
		
		player5Label = new JLabel();
		player5Label.setBounds(23, 675, 61, 16);
		boardFrame.getContentPane().add(player5Label);
		
		player5MapPercentage = new JLabel();
		player5MapPercentage.setBounds(23, 715, 61, 16);
		boardFrame.getContentPane().add(player5MapPercentage);
		
		player5ContinentControl = new JLabel();
		player5ContinentControl.setBounds(183, 715, 169, 16);
		boardFrame.getContentPane().add(player5ContinentControl);
		
		player5TotalArmies = new JLabel();
		player5TotalArmies.setBounds(381, 715, 61, 16);
		boardFrame.getContentPane().add(player5TotalArmies);
		
		player6Label = new JLabel();
		player6Label.setBounds(23, 755, 61, 16);
		boardFrame.getContentPane().add(player6Label);
		
		player6MapPercentage = new JLabel();
		player6MapPercentage.setBounds(23, 796, 61, 16);
		boardFrame.getContentPane().add(player6MapPercentage);
		
		player6ContinentControl = new JLabel();
		player6ContinentControl.setBounds(183, 796, 169, 16);
		boardFrame.getContentPane().add(player6ContinentControl);
		
		player6TotalArmies = new JLabel();
		player6TotalArmies.setBounds(381, 796, 61, 16);
		boardFrame.getContentPane().add(player6TotalArmies);
		
		JLabel cardsLabel = new JLabel("Number of Cards");
		cardsLabel.setBounds(23, 255, 119, 13);
		boardFrame.getContentPane().add(cardsLabel);
		
		cardsCountLabel = new JLabel("");
		cardsCountLabel.setBounds(148, 255, 45, 13);
		boardFrame.getContentPane().add(cardsCountLabel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1522, 22);
		boardFrame.getContentPane().add(menuBar);
		
		JMenuItem mntmSaveGame = new JMenuItem("Save Game");
		menuBar.add(mntmSaveGame);
		
		JLabel cardsObtainedLabel = new JLabel("Cards Obtained :");
		cardsObtainedLabel.setBounds(552, 744, 101, 27);
		boardFrame.getContentPane().add(cardsObtainedLabel);
		
		cardsObtainedTextArea = new JTextArea();
		cardsObtainedTextArea.setEnabled(false);
		cardsObtainedTextArea.setEditable(false);
		cardsObtainedTextArea.setBounds(701, 715, 658, 86);
		boardFrame.getContentPane().add(cardsObtainedTextArea);
	}
	
	/**
	 * Returns the CardsObtainedTextArea object set on this frame.
	 * @return the CardsObtainedTextArea object of this frame
	 */
	public JTextArea getCardsObtainedTextArea() {
		return cardsObtainedTextArea;
	}

	/**
	 * Returns the cardsCountLabel object set on this frame.
	 * @return the cardsCountLabel object of this frame
	 */
	public JLabel getCardsCountLabel() {
		return cardsCountLabel;
	}

	/**
	 * Returns the player1Label object set on this frame.
	 * @return the player1Label object of this frame
	 */
	public JLabel getPlayer1Label() {
		return player1Label;
	}
	
	/**
	 * Returns the player1MapPercentage object set on this frame, player1MapPercentage contains the percentage of the map owned by player 1.
	 * @return the player1MapPercentage object of this frame
	 */
	public JLabel getPlayer1MapPercentage() {
		return player1MapPercentage;
	}
	
	/**
	 * Returns the player1ContinentControl object set on this frame, player1ContinentControl contains the continents owned by player 1.
	 * @return the player1ContinentControl object of this frame
	 */
	public JLabel getPlayer1ContinentControl() {
		return player1ContinentControl;
	}
	
	/**
	 * Returns the player1TotalArmies object set on this frame, player1TotalArmies contains the total armies owned by player 1.
	 * @return the player1TotalArmies object of this frame
	 */
	public JLabel getPlayer1TotalArmies() {
		return player1TotalArmies;
	}
	
	/**
	 * Returns the player2Label object set on this frame.
	 * @return the player2Label object of this frame
	 */
	public JLabel getPlayer2Label() {
		return player2Label;
	}

	/**
	 * Returns the player2MapPercentage object set on this frame, player2MapPercentage contains the percentage of the map owned player 2.
	 * @return the player2MapPercentage object of this frame
	 */
	public JLabel getPlayer2MapPercentage() {
		return player2MapPercentage;
	}

	/**
	 * Returns the player2ContinentControl object set on this frame, player2ContinentControl contains the continents owned by player 2.
	 * @return the player2ContinentControl object of this frame
	 */
	public JLabel getPlayer2ContinentControl() {
		return player2ContinentControl;
	}

	/**
	 * Returns the player2TotalArmies object set on this frame, player2TotalArmies contains the total armies owned by player 2.
	 * @return the player2TotalArmies object of this frame
	 */
	public JLabel getPlayer2TotalArmies() {
		return player2TotalArmies;
	}

	/**
	 * Returns the player3Label object set on this frame.
	 * @return the player3Label object of this frame
	 */
	public JLabel getPlayer3Label() {
		return player3Label;
	}

	/**
	 * Returns the player3MapPercentage object set on this frame, player3MapPercentage contains the percentage of the map owned by player 3.
	 * @return the player3MapPercentage object of this frame
	 */
	public JLabel getPlayer3MapPercentage() {
		return player3MapPercentage;
	}

	/**
	 * Returns the player3ContinentControl object set on this frame, player3ContinentControl contains the continents owned by player 3.
	 * @return the player3ContinentControl object of this frame
	 */
	public JLabel getPlayer3ContinentControl() {
		return player3ContinentControl;
	}

	/**
	 * Returns the player3TotalArmies object set on this frame, player3TotalArmies contains the total armies owned by player 3.
	 * @return the player3TotalArmies object of this frame
	 */
	public JLabel getPlayer3TotalArmies() {
		return player3TotalArmies;
	}

	/**
	 * Returns the player4Label object set on this frame.
	 * @return the player4Label object of this frame
	 */
	public JLabel getPlayer4Label() {
		return player4Label;
	}

	/**
	 * Returns the player4MapPercentage object set on this frame, player4MapPercentage contains the percentage of the map owned by player 4.
	 * @return the player4MapPercentage object of this frame
	 */
	public JLabel getPlayer4MapPercentage() {
		return player4MapPercentage;
	}

	/**
	 * Returns the player4ContinentControl object set on this frame, player4ContinentControl contains the continents owned by player 4.
	 * @return the player4ContinentControl object of this frame
	 */
	public JLabel getPlayer4ContinentControl() {
		return player4ContinentControl;
	}

	/**
	 * Returns the player4TotalArmies object set on this frame, player4TotalArmies contains the total armies owned by player 4.
	 * @return the player4TotalArmies object of this frame
	 */
	public JLabel getPlayer4TotalArmies() {
		return player4TotalArmies;
	}

	/**
	 * Returns the player5Label object set on this frame.
	 * @return the player5Label object of this frame
	 */
	public JLabel getPlayer5Label() {
		return player5Label;
	}

	/**
	 * Returns the player5MapPercentage object set on this frame, player5MapPercentage contains the percentage of the map owned by player 5.
	 * @return the player5MapPercentage object of this frame
	 */
	public JLabel getPlayer5MapPercentage() {
		return player5MapPercentage;
	}
	
	/**
	 * Returns the player5ContinentControl object set on this frame, player5ContinentControl contains the continents owned by player 5.
	 * @return the player5ContinentControl object of this frame
	 */
	public JLabel getPlayer5ContinentControl() {
		return player5ContinentControl;
	}

	/**
	 * Returns the player5TotalArmies object set on this frame, player5TotalArmies contains the total armies owned by player 5.
	 * @return the player5TotalArmies object of this frame
	 */
	public JLabel getPlayer5TotalArmies() {
		return player5TotalArmies;
	}

	/**
	 * Returns the player6Label object set on this frame.
	 * @return the player6Label object of this frame
	 */
	public JLabel getPlayer6Label() {
		return player6Label;
	}

	/**
	 * Returns the player6MapPercentage object set on this frame, player6MapPercentage contains the percentage of the map owned by player 6.
	 * @return the player6MapPercentage object of this frame
	 */
	public JLabel getPlayer6MapPercentage() {
		return player6MapPercentage;
	}

	/**
	 * Returns the player6ContinentControl object set on this frame, player6ContinentControl contains the continents owned by player 6.
	 * @return the player6ContinentControl object of this frame
	 */
	public JLabel getPlayer6ContinentControl() {
		return player6ContinentControl;
	}

	/**
	 * Returns the player6TotalArmies object set on this frame, player6TotalArmies contains the total armies owned by player 6.
	 * @return the player6TotalArmies object of this frame
	 */
	public JLabel getPlayer6TotalArmies() {
		return player6TotalArmies;
	}

	/**
	 * Returns the phaseInfo object set on this frame, phaseInfo contains the phase's information data.
	 * @return the phaseInfo object of this frame
	 */
	public JLabel getPhaseInfo() {
		return phaseInfo;
	}

	/**
	 * Returns the currentPhase object set on this frame, currentPhase contains the name of the current phase.
	 * @return the currentPhase object of this frame
	 */
	public JLabel getCurrentPhase() {
		return currentPhase;
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

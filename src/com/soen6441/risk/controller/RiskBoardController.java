package com.soen6441.risk.controller;

import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.model.RiskBoardModel;
import com.soen6441.risk.view.RiskBoardView;

/**
 * <b>RiskBoardController</b>
 * RiskBoardController class is the controller between the {@link RiskBoardView} and {@link RiskBoardModel}.
 * It triggers respective methods in the {@link RiskBoardModel} when an event happened for the {@link RiskBoardView} components
 * 
 * @author Naga Satish Reddy
 *
 */
public class RiskBoardController {
	RiskBoardModel model;
	RiskBoardView view;

	/**
	 * Gets the instances of the {@link RiskBoardModel} and {@link RiskBoardView} classes
	 * @param riskBoardModel, instance of the {@link RiskBoardModel} object
	 * @param riskBoardView, instance of the {@link RiskBoardView} objects
	 */
	public RiskBoardController(RiskBoardModel riskBoardModel, RiskBoardView riskBoardView) {
		this.model = riskBoardModel;
		this.view = riskBoardView;
	}

	/**
	 * InitializeGame method calls the methods needed initially to beloaded to start the game and
	 * Initialize the event handlers for the {@link RiskBoardView} components
	 * It triggers the respective methods in the {@link RiskBoardModel} class
	 */
	public void intializeBoardGame(int playerCount, String fileName) {
		this.model.loadRequiredData(System.getProperty("user.dir")+"/resources/"+fileName+RiskGameConstants.MAP_FILE_EXTENSION);
		this.model.assignCountriesToPlayers(playerCount);
		addActionListenersToComponents();
		this.model.updateTheBoardScreenData(this.view);
	}

	/**
	 * addActionListenersToComponents methods adds the handlers for the components in the {@link RiskBoardView} frame
	 * to the respective methods in the {@link RiskBoardModel} frame.
	 */
	private void addActionListenersToComponents() {
		this.view.getCountryComboBox().addActionListener(e -> this.model.getAdjacentCountriesForComboCountry(this.view));
		this.view.getAdjacentCountryComboBox().addActionListener(e -> this.model.createOrUpdateImage(view));
		this.view.getReinforceBtn().addActionListener(action -> this.model.updateArmiesInCountries(this.view));
		this.view.getMoveArmiesBtn().addActionListener(m -> this.model.moveArmiesBetweenCountries(view));
		this.view.getEndFortificationBtn().addActionListener(end -> this.model.endFortificationPhase(view));
		this.view.getAttackBtn().addActionListener(action -> this.model.attackBetweenCountries(view));
		this.view.getEndAttackButton().addActionListener(action -> this.model.endAttackPhase(view));
	}

}

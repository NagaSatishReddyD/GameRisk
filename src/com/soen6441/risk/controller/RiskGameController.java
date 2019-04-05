package com.soen6441.risk.controller;

import com.soen6441.risk.model.RiskGameModel;
import com.soen6441.risk.view.RiskGameView;

/**
 * <b>RiskGameController</b>
 * RiskGameController class is the controller between the {@link RiskGameView} and {@link RiskGameModel}.
 * It triggers respective methods in the {@link RiskGameModel} when an event happened for the {@link RiskGameView} components
 * 
 * @author Naga Satish Reddy
 *
 */
public class RiskGameController {
	RiskGameModel model;
	RiskGameView view;

	/**
	 * Gets the instances of the {@link RiskGameModel} and {@link RiskGameView} classes
	 * @param riskModel, instance of the {@link RiskGameModel} object
	 * @param riskGameView, instance of the {@link RiskGameView} objects
	 */
	public RiskGameController(RiskGameModel riskModel, RiskGameView riskGameView) {
		this.model = riskModel;
		this.view = riskGameView;
	}

	/**
	 * InitializeGame method contains the event handlers for the components in the {@link RiskGameView} frame.
	 * It triggers the respective methods in the {@link RiskGameModel} class
	 */
	public void initializeController() {
		this.view.getStartGameButton().addActionListener(e -> model.showPlayerDetailsMenu(this.view));
		this.view.getLoadMapButton().addActionListener(e -> model.showAddMapFrame(this.view));
		this.view.getTournamentButton().addActionListener(e -> model.showTournamentFrame(this.view));
	}
}

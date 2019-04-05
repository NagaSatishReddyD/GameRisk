package com.soen6441.risk.controller;

import com.soen6441.risk.model.RiskStrategyModel;
import com.soen6441.risk.model.RiskTournamentModel;
import com.soen6441.risk.view.RiskStrategyView;
import com.soen6441.risk.view.RiskTournamentView;

/**
 * RiskTournamentController is the controller between {@link RiskTournamentModel} and {@link RiskTournamentView}
 * It will trigger the method in the {@link RiskTournamentModel} when a the Run Tournament button in {@link RiskTournamentView} is clicked
 * @author An Nguyen
 *
 */
public class RiskTournamentController {
	RiskTournamentModel model;
	RiskTournamentView view;
	
	/**
	 * The constructor gets the instance of {@link RiskTournamentModel} and {@link RiskTournamentView} classes
	 * @param model, instance of {@link RiskTournamentModel} class
	 * @param view, instance of {@link RiskTournamentView} class
	 */
	public RiskTournamentController(RiskTournamentModel model, RiskTournamentView view) {
		this.model = model;
		this.view = view;
	}
	
	/**
	 * This method is used to trigger an event when the Run Tournament button is clicked in {@link RiskStrategyView} frame
	 * and when a specific player count is selected, enable/disable appropriate player labels and combo boxes
	 */
	public void runTournament() {
		this.view.getRunTournamentButton().addActionListener(e -> this.model.runTournament(this.view));
		this.view.getPlayerCountCombo().addActionListener(e -> this.model.getComboBasedOnPlayerCount(this.view));
	}
}

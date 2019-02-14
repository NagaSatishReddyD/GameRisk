package com.soen6441.risk.controller;

import java.io.IOException;

import com.soen6441.risk.model.RiskBoardModel;
import com.soen6441.risk.view.RiskBoardView;

public class RiskBoardController {
	RiskBoardModel model;
	RiskBoardView view;

	public RiskBoardController(RiskBoardModel riskBoardModel, RiskBoardView riskBoardView) {
		this.model = riskBoardModel;
		this.view = riskBoardView;
	}

	public void intializeBoardGame(int playerCount) throws IOException {
		this.model.loadRequiredData();
		this.model.assignCountriesToPlayers(playerCount);
		addActionListenersToComponents();
		//initial army set up phase
		this.model.updateTheBoardScreenData(this.view);
	}

	private void addActionListenersToComponents() {
		this.view.getCountryComboBox().addActionListener(e -> this.model.getAdjacentCountriesForComboCountry(this.view));
		this.view.getReinforceBtn().addActionListener(action -> this.model.updateArmiesInCountries(this.view));
		this.view.getMoveArmiesBtn().addActionListener(m -> this.model.moveArmiesBetweenCountries(view));
		this.view.getEndFortificationBtn().addActionListener(end -> this.model.nextPlayerFortification(view));
	}

}

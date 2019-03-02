package com.soen6441.risk.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.model.RiskBoardModel;
import com.soen6441.risk.view.RiskBoardView;

public class RiskBoardController {
	RiskBoardModel model;
	RiskBoardView view;

	public RiskBoardController(RiskBoardModel riskBoardModel, RiskBoardView riskBoardView) {
		this.model = riskBoardModel;
		this.view = riskBoardView;
	}

	public void intializeBoardGame(int playerCount, String fileName) throws IOException, NoSuchAlgorithmException {
		this.model.loadRequiredData(System.getProperty("user.dir")+"/resources/"+fileName+RiskGameConstants.MAP_FILE_EXTENSION);
		this.model.assignCountriesToPlayers(playerCount, this.view);
		addActionListenersToComponents();
		//initial army set up phase
		this.model.updateTheBoardScreenData(this.view);
	}

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

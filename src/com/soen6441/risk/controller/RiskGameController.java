package com.soen6441.risk.controller;

import com.soen6441.risk.model.RiskGameModel;
import com.soen6441.risk.view.RiskGameView;

public class RiskGameController {
	RiskGameModel model;
	RiskGameView view;

	public RiskGameController(RiskGameModel riskModel, RiskGameView riskGameView) {
		this.model = riskModel;
		this.view = riskGameView;
	}

	public void initializeController() {
		this.view.getStartGameButton().addActionListener(e -> model.showPlayerDetailsMenu(this.view));
	}

}

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

	public void intializeBoardGame(String playerCount) throws IOException {
		this.model.loadRequiredData();
		this.model.assignCountriesToPlayers(Integer.parseInt(playerCount));
	}

}

package com.soen6441.risk.model;

import java.io.IOException;

import com.soen6441.risk.controller.RiskBoardController;
import com.soen6441.risk.view.RiskBoardView;
import com.soen6441.risk.view.RiskPlayerView;

public class RiskPlayerModel {

	public void startBoardFrame(RiskPlayerView view) throws IOException {
		view.getPlayerStartframe().setVisible(false);
		RiskBoardModel riskBoardModel = new RiskBoardModel();
		RiskBoardView riskBoardView = new RiskBoardView();
		RiskBoardController  riskBoardController = new RiskBoardController(riskBoardModel, riskBoardView);
		riskBoardController.intializeBoardGame(view.getPlayerCountCombo().getSelectedItem().toString());
	}

}

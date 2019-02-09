package com.soen6441.risk.model;

import com.soen6441.risk.controller.RiskPlayerController;
import com.soen6441.risk.view.RiskGameView;
import com.soen6441.risk.view.RiskPlayerView;

public class RiskGameModel {

	public void showPlayerDetailsMenu(RiskGameView view) {
		view.getGameStartframe().setVisible(false);
		RiskPlayerModel riskPlayerModel = new RiskPlayerModel();
		RiskPlayerView riskPlayerView = new RiskPlayerView();
		RiskPlayerController riskPlayerController = new RiskPlayerController(riskPlayerModel, riskPlayerView);
		riskPlayerController.initalizeGame();
	}

}

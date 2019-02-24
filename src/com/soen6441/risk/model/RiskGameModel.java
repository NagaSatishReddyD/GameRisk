package com.soen6441.risk.model;

import java.awt.*;

import javax.swing.*;

import com.soen6441.risk.controller.RiskAddMapController;
import com.soen6441.risk.controller.RiskPlayerController;
import com.soen6441.risk.view.RiskAddMapView;
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

	public void showAddMapFrame(RiskGameView view) {
		view.getGameStartframe().setVisible(false);
		RiskAddMapModel riskAddMapModel = new RiskAddMapModel();
		RiskAddMapView riskAddMapView = new RiskAddMapView();
		RiskAddMapController riskAddMapController = new RiskAddMapController(riskAddMapModel, riskAddMapView);
		riskAddMapController.addMap(view);
		
	}
}

package com.soen6441.risk.model;

import com.soen6441.risk.controller.RiskAddMapController;
import com.soen6441.risk.controller.RiskPlayerController;
import com.soen6441.risk.view.RiskAddMapView;
import com.soen6441.risk.view.RiskGameView;
import com.soen6441.risk.view.RiskPlayerView;

/**
 * RiskGameModel class contains the logics of the RiskGameView events
 * @author Naga Satish Reddy
 *
 */
public class RiskGameModel {

	/**
	 * showPlayerDetailsMenu methods creates the RiskPlayerModel objects and move to the RiskPlayerView frame
	 * @param view, RiskBoardView object used to update the components of the this screen.
	 */
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

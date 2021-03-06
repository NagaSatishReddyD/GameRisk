package com.soen6441.risk.model;

import com.soen6441.risk.controller.RiskAddMapController;
import com.soen6441.risk.controller.RiskPlayerController;
import com.soen6441.risk.controller.RiskTournamentController;
import com.soen6441.risk.view.RiskAddMapView;
import com.soen6441.risk.view.RiskGameView;
import com.soen6441.risk.view.RiskPlayerView;
import com.soen6441.risk.view.RiskTournamentView;

/**
 * <b>RiskGameModel</b>
 * RiskGameModel class contains the logics of the {@link RiskGameView} actions performed by the player
 * @author Naga Satish Reddy
 * @author An Nguyen
 *
 */
public class RiskGameModel {

	/**
	 * showPlayerDetailsMenu methods creates the RiskPlayerModel objects and move to the RiskPlayerView frame
	 * @param view, instance of the RiskGameView
	 */
	public void showPlayerDetailsMenu(RiskGameView view) {
		view.getGameStartframe().setVisible(false);
		RiskPlayerModel riskPlayerModel = new RiskPlayerModel();
		RiskPlayerView riskPlayerView = new RiskPlayerView();
		RiskPlayerController riskPlayerController = new RiskPlayerController(riskPlayerModel, riskPlayerView);
		riskPlayerController.initalizePlayerStrategy();
	}

	/**
	 * showAddMapFrame method creates the RiskAddMapModel objects and move to the RiskAddMapView frame
	 * @param view, instance of the RiskGameView
	 */
	public void showAddMapFrame(RiskGameView view) {
		view.getGameStartframe().setVisible(false);
		RiskAddMapModel riskAddMapModel = new RiskAddMapModel();
		RiskAddMapView riskAddMapView = new RiskAddMapView();
		RiskAddMapController riskAddMapController = new RiskAddMapController(riskAddMapModel, riskAddMapView);
		riskAddMapController.addMap(view);
		
	}
	
	/**
	 * showTournamentFrame method creates the RiskTournamentModel object and move to the RiskTournamentView frame
	 * @param view, instance of the RiskGameView
	 */
	public void showTournamentFrame(RiskGameView view) {
		view.getGameStartframe().setVisible(false);
		RiskTournamentModel riskTournamentModel = new RiskTournamentModel();
		RiskTournamentView riskTournamentView = new RiskTournamentView();
		RiskTournamentController riskTournamentController = new RiskTournamentController(riskTournamentModel, riskTournamentView);
		riskTournamentController.runTournament();
	}
}

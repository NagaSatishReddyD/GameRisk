package com.soen6441.risk.model;

import com.soen6441.risk.controller.RiskBoardController;
import com.soen6441.risk.view.RiskBoardView;
import com.soen6441.risk.view.RiskPlayerView;

/**
 * <b>RiskPlayerModel</b>
 * RiskPlayerModel class contains the logics when a actions are done in {@link RiskPlayerView} components.
 * @author Naga Satish Reddy
 */
public class RiskPlayerModel {

	/**
	 * startBoardFrame method is used to go the RiskBoardView from the RiskPlayerView
	 * @param riskPlayerView, instance of the {@link RiskPlayerView}
	 */
	public void startBoardFrame(RiskPlayerView riskPlayerView) {
		riskPlayerView.getPlayerStartframe().setVisible(false);
		RiskBoardModel riskBoardModel = new RiskBoardModel();
		RiskBoardView riskBoardView = new RiskBoardView();
		RiskBoardController  riskBoardController = new RiskBoardController(riskBoardModel, riskBoardView);
		riskBoardController.intializeBoardGame(Integer.parseInt(riskPlayerView.getPlayerCountCombo().getSelectedItem().toString()), 
				riskPlayerView.getMapComboBox().getSelectedItem().toString());
	}

}

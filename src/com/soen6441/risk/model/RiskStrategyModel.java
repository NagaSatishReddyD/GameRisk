package com.soen6441.risk.model;

import com.soen6441.risk.controller.RiskBoardController;
import com.soen6441.risk.view.RiskBoardView;
import com.soen6441.risk.view.RiskStrategyView;

/**
 * RiskStrategyModel class contains the logic when a user click the load game button in RiskStrategyView
 * @author An Nguyen
 *
 */
public class RiskStrategyModel {

	/**
	 * This method is used to go to the RiskBoardView from RiskStrategyView
	 * @param view, instance of the {@link RiskStrategyView}
	 * @param playerCount, number of players that are going to play the game
	 * @param fileName, the name of the map
	 */
	public void initializeBoardFrame(RiskStrategyView view, int playerCount, String fileName) {
		view.getStrategyFrame().setVisible(false);
		String[] behaviors = new String[6];
		behaviors[0] = view.getPlayer1StrategyCombo().getSelectedItem().toString();
		behaviors[1] = view.getPlayer2StrategyCombo().getSelectedItem().toString();
		behaviors[2] = view.getPlayer3StrategyCombo().getSelectedItem().toString();
		behaviors[3] = view.getPlayer4StrategyCombo().getSelectedItem().toString();
		behaviors[4] = view.getPlayer5StrategyCombo().getSelectedItem().toString();
		behaviors[5] = view.getPlayer6StrategyCombo().getSelectedItem().toString();
		RiskBoardModel riskBoardModel = new RiskBoardModel();
		RiskBoardView riskBoardView = new RiskBoardView();
		RiskBoardController riskBoardController = new RiskBoardController(riskBoardModel, riskBoardView);
		riskBoardController.intializeBoardGame(playerCount, fileName, behaviors);
		
	}

}

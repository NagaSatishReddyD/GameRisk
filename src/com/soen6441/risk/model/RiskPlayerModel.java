package com.soen6441.risk.model;

import com.soen6441.risk.controller.RiskBoardController;
import com.soen6441.risk.controller.RiskStrategyController;
import com.soen6441.risk.view.RiskBoardView;
import com.soen6441.risk.view.RiskPlayerView;
import com.soen6441.risk.view.RiskStrategyView;

/**
 * <b>RiskPlayerModel</b>
 * RiskPlayerModel class contains the logics when a actions are done in {@link RiskPlayerView} components.
 * @author Naga Satish Reddy
 */
public class RiskPlayerModel {

	/**
	 * startStrategyFrame method is used to go the RiskStrategyView from the RiskPlayerView
	 * @param riskPlayerView, instance of the {@link RiskPlayerView}
	 */
	public void startStrategyFrame(RiskPlayerView riskPlayerView) {
		riskPlayerView.getPlayerStartframe().setVisible(false);
		int playerCount = Integer.parseInt(riskPlayerView.getPlayerCountCombo().getSelectedItem().toString());
		RiskStrategyModel riskStrategyModel = new RiskStrategyModel();
		RiskStrategyView riskStrategyView = new RiskStrategyView();
		RiskStrategyController  riskStrategyController = new RiskStrategyController(riskStrategyModel, riskStrategyView);
		disableUnNeededComponents(riskStrategyView, playerCount);
		riskStrategyController.initializeBoardFrame(Integer.parseInt(riskPlayerView.getPlayerCountCombo().getSelectedItem().toString()), 
				riskPlayerView.getMapComboBox().getSelectedItem().toString());
	}

	/**
	 * This method is used to remove unnecessary player's components according to the player count
	 * chosen from the RiskPlayerView
	 * @param riskStrategyView, instance of the {@link RiskStrategyView}
	 * @param playerCount, the number of players going to play the game
	 */
	private void disableUnNeededComponents(RiskStrategyView riskStrategyView, int playerCount) {
		switch(playerCount) {
			case 2:
				riskStrategyView.getPlayer3Label().setVisible(false);
				riskStrategyView.getPlayer3StrategyCombo().setVisible(false);
				riskStrategyView.getPlayer4Label().setVisible(false);
				riskStrategyView.getPlayer4StrategyCombo().setVisible(false);
				riskStrategyView.getPlayer5Label().setVisible(false);
				riskStrategyView.getPlayer5StrategyCombo().setVisible(false);
				riskStrategyView.getPlayer6Label().setVisible(false);
				riskStrategyView.getPlayer6StrategyCombo().setVisible(false);
				break;
			case 3:
				riskStrategyView.getPlayer4Label().setVisible(false);
				riskStrategyView.getPlayer4StrategyCombo().setVisible(false);
				riskStrategyView.getPlayer5Label().setVisible(false);
				riskStrategyView.getPlayer5StrategyCombo().setVisible(false);
				riskStrategyView.getPlayer6Label().setVisible(false);
				riskStrategyView.getPlayer6StrategyCombo().setVisible(false);
				break;
			case 4:
				riskStrategyView.getPlayer5Label().setVisible(false);
				riskStrategyView.getPlayer5StrategyCombo().setVisible(false);
				riskStrategyView.getPlayer6Label().setVisible(false);
				riskStrategyView.getPlayer6StrategyCombo().setVisible(false);
				break;
			case 5:
				riskStrategyView.getPlayer6Label().setVisible(false);
				riskStrategyView.getPlayer6StrategyCombo().setVisible(false);
				break;
		}
	}

}

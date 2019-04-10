package com.soen6441.risk.model;

import javax.swing.JComboBox;

import com.soen6441.risk.controller.RiskBoardController;
import com.soen6441.risk.view.RiskBoardView;
import com.soen6441.risk.view.RiskStrategyView;

/**
 * RiskStrategyModel class contains the logic when a user click the load game button in RiskStrategyView
 * @author An Nguyen
 * @author Naga Satish Reddy
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
		behaviors[0] = getPlayerStrategy(view.getPlayer1StrategyCombo());
		behaviors[1] = getPlayerStrategy(view.getPlayer2StrategyCombo());
		behaviors[2] = getPlayerStrategy(view.getPlayer3StrategyCombo());
		behaviors[3] = getPlayerStrategy(view.getPlayer4StrategyCombo());
		behaviors[4] = getPlayerStrategy(view.getPlayer5StrategyCombo());
		behaviors[5] = getPlayerStrategy(view.getPlayer6StrategyCombo());
		RiskBoardModel riskBoardModel = new RiskBoardModel();
		RiskBoardView riskBoardView = new RiskBoardView();
		RiskBoardController riskBoardController = new RiskBoardController(riskBoardModel, riskBoardView);
		riskBoardController.intializeBoardGame(playerCount, fileName, behaviors);
		
	}

	/**
	 * This method is used to get the strategy value selected for respective number of players
	 * @param playerStrategyCombo
	 * @return the behavior value selected of each player
	 */
	private String getPlayerStrategy(JComboBox<String> playerStrategyCombo) {
		if(playerStrategyCombo.isVisible())
			return playerStrategyCombo.getSelectedItem().toString();
		return null;
	}

}

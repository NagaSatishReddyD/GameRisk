package com.soen6441.risk;

import com.soen6441.risk.controller.RiskGameController;
import com.soen6441.risk.model.RiskGameModel;
import com.soen6441.risk.view.RiskGameView;

/**
 * GameRisk class starts the game. It has the main class.The game starts from this file
 * @author Naga Satish Reddy
 *
 */
public class GameRisk {

	public static void main(String[] args) {
		System.out.println("Starting the game...");
		RiskGameModel riskModel = new RiskGameModel();
		RiskGameView riskGameView = new RiskGameView();
		RiskGameController riskGameController = new RiskGameController(riskModel, riskGameView);
		riskGameController.initializeController();
	}

}

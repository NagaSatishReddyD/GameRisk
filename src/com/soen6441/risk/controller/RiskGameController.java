package com.soen6441.risk.controller;

import com.soen6441.risk.model.RiskGameModel;
import com.soen6441.risk.view.RiskGameView;

/**
 * RiskGameController class contains the event handlers of the RiskGameView components and 
 * triggers the respective methods in the RiskGameModel.
 * @author Naga Satish Reddy
 * @since 1.0
 */
public class RiskGameController {
	RiskGameModel model;
	RiskGameView view;

	public RiskGameController(RiskGameModel riskModel, RiskGameView riskGameView) {
		this.model = riskModel;
		this.view = riskGameView;
	}

	/**
	 * initializeController method handles the event handlers of the RiskGameView components.
	 */
	public void initializeController() {
		this.view.getStartGameButton().addActionListener(e -> model.showPlayerDetailsMenu(this.view));
		this.view.getLoadMapButton().addActionListener(e -> model.showAddMapFrame(this.view));
	}

}

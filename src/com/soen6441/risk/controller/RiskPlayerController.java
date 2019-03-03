package com.soen6441.risk.controller;

import com.soen6441.risk.model.RiskPlayerModel;
import com.soen6441.risk.view.RiskPlayerView;

/**
 * <b>RiskPlayerController</b>
 * RiskPlayerController class is the controller between the {@link RiskPlayerView} and {@link RiskPlayerModel}.
 * It triggers respective methods in the {@link RiskPlayerModel} when an event happened for the {@link RiskPlayerView} components
 * 
 * @author Naga Satish Reddy
 *
 */
public class RiskPlayerController {
	RiskPlayerModel model;
	RiskPlayerView view;

	/**
	 * Gets the instances of the {@link RiskPlayerModel} and {@link RiskPlayerView} classes
	 * @param riskPlayerModel, instance of the {@link RiskPlayerModel} object
	 * @param riskPlayerView, instance of the {@link RiskPlayerView} objects
	 */
	public RiskPlayerController(RiskPlayerModel riskPlayerModel, RiskPlayerView riskPlayerView) {
		this.model = riskPlayerModel;
		this.view = riskPlayerView;
	}

	/**
	 * InitializeGame method contains the event handlers for the components in the {@link RiskPlayerView} frame.
	 * It triggers the respective methods in the {@link RiskPlayerModel} class
	 */
	public void initalizeGame() {
		this.view.getLoadGameButton().addActionListener(e -> model.startBoardFrame(this.view));
	}

}

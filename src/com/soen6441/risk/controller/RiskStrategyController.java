package com.soen6441.risk.controller;

import com.soen6441.risk.model.RiskStrategyModel;
import com.soen6441.risk.view.RiskStrategyView;

/**
 * RiskStrategyController is the controller between {@link RiskStrategyModel} and {@link RiskStrategyView}
 * It will trigger the method in the {@link RiskStrategyModel} when a the load game button in {@link RiskStrategyView} is clicked
 * @author An Nguyen
 *
 */
public class RiskStrategyController {
	RiskStrategyModel model;
	RiskStrategyView view;
	
	/**
	 * The constructor gets the instance of {@link RiskStrategyModel} and {@link RiskStrategyView} classes
	 * @param model, instance of {@link RiskStrategyModel} class
	 * @param view, instance of {@link RiskStrategyView} class
	 */
	public RiskStrategyController(RiskStrategyModel model, RiskStrategyView view) {
		this.model = model;
		this.view = view;
	}
	
	/**
	 * This method is used to trigger an event when the load game button is clicked in {@link RiskStrategyView} frame
	 * @param playerCount, number of players that are going to play the game
	 * @param fileName, the name of the chosen map
	 */
	public void initializeBoardFrame(int playerCount, String fileName) {
		this.view.getLoadGameButton().addActionListener(e -> model.initializeBoardFrame(this.view, playerCount, fileName));
	}
}

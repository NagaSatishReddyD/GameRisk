package com.soen6441.risk.controller;

import java.io.IOException;

import com.soen6441.risk.model.RiskPlayerModel;
import com.soen6441.risk.view.RiskPlayerView;

public class RiskPlayerController {
	RiskPlayerModel model;
	RiskPlayerView view;

	public RiskPlayerController(RiskPlayerModel riskPlayerModel, RiskPlayerView riskPlayerView) {
		this.model = riskPlayerModel;
		this.view = riskPlayerView;
	}

	public void initalizeGame() {
		this.view.getLoadGameButton().addActionListener(e -> {
			try {
				model.startBoardFrame(this.view);
			} catch (IOException exception) {
				System.out.println(exception.getMessage());
			}
		});
	}

}

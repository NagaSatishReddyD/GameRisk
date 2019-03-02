package com.soen6441.risk.controller;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.soen6441.risk.model.RiskAddMapModel;
import com.soen6441.risk.view.RiskAddMapView;
import com.soen6441.risk.view.RiskGameView;

/**
 * RiskAddMapController class handles the event buttons of RiskAddMapView
 * @author An Nguyen
 *
 */
public class RiskAddMapController {

	RiskAddMapModel model;
	RiskAddMapView view;
	
	public RiskAddMapController(RiskAddMapModel riskAddMapModel, RiskAddMapView riskAddMapView) {
		this.model = riskAddMapModel;
		this.view = riskAddMapView;
	}
	
	/**
	 * addMap method is used to get the action event of map add and back button
	 * @param gameView
	 */
	public void addMap(RiskGameView gameView) {
		view.getFrame().setVisible(true);
		this.view.getMapUploadBtn().addActionListener(e -> {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Map files", "map", "bmp");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.showOpenDialog(null);
				File[] files = chooser.getSelectedFiles();
				model.mapValidation(view, files);
			} catch (NumberFormatException exception) {
				System.out.println(exception.getMessage());
			}
		});
		
		
		this.view.getBackBtn().addActionListener(e -> {
			view.getFrame().setVisible(false);
			gameView.getGameStartframe().setVisible(true);
		});
	}

}

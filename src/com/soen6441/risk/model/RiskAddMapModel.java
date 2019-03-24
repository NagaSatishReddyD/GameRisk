package com.soen6441.risk.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.controller.RiskPlayerController;
import com.soen6441.risk.view.RiskAddMapView;
import com.soen6441.risk.view.RiskPlayerView;


/**
 * RiskAddMapModel class handles the method for validating the uploaded map files
 * @author An Nguyen
 *
 */
public class RiskAddMapModel {
	
	/**
	 * This method is used to validate the correctness of the uploaded map.
	 * @param view, the frame that allows users to upload the map files of the game
	 * @param files, this contains the files that the user uploaded
	 */
	public void mapValidation(RiskAddMapView view, File[] files) {
		//in progress
		RiskBoardModel model = new RiskBoardModel();
		if(files.length != 2 || (files[0].getName().endsWith("bmp") && files[1].getName().endsWith("bmp")) || (files[0].getName().endsWith("map") && files[1].getName().endsWith("map"))) {
			return;
		}
		view.getFrame().setVisible(false);
		
		try {
			
			Path sourceImage;
			Path destinationImage;
			if(files[0].getName().endsWith("map")) {
				model.loadRequiredData(files[0].getAbsolutePath());
				Path sourceMap = Paths.get(files[0].getAbsolutePath());
				Path destinationMap = Paths.get(System.getProperty(RiskGameConstants.USER_DIR)+RiskGameConstants.RESOURCES_FOLDER+files[0].getName());
				Files.copy(sourceMap, destinationMap);
				sourceImage = Paths.get(files[1].getAbsolutePath());
				destinationImage = Paths.get(System.getProperty(RiskGameConstants.USER_DIR)+RiskGameConstants.RESOURCES_FOLDER+files[1].getName());
			}else {
				model.loadRequiredData(files[1].getAbsolutePath());
				Path sourceMap = Paths.get(files[1].getAbsolutePath());
				Path destinationMap = Paths.get(System.getProperty(RiskGameConstants.USER_DIR)+RiskGameConstants.RESOURCES_FOLDER+files[1].getName());
				Files.copy(sourceMap, destinationMap);
				sourceImage = Paths.get(files[0].getAbsolutePath());
				destinationImage = Paths.get(System.getProperty(RiskGameConstants.USER_DIR)+RiskGameConstants.RESOURCES_FOLDER+files[0].getName());
			}
			Files.copy(sourceImage, destinationImage);
			RiskPlayerModel riskPlayerModel = new RiskPlayerModel();
			RiskPlayerView riskPlayerView = new RiskPlayerView();
			RiskPlayerController riskPlayerController = new RiskPlayerController(riskPlayerModel, riskPlayerView);
			riskPlayerController.initalizeGame();
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}

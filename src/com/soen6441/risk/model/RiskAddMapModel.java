package com.soen6441.risk.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.soen6441.risk.view.RiskAddMapView;


/**
 * RiskAddMapModel class handles the method for validating the uploaded map files
 * @author An Nguyen
 *
 */
public class RiskAddMapModel {
	
	/**
	 * This method is used to validate the correctness of the uploaded map.
	 * @param view
	 * @param files
	 */
	public void mapValidation(RiskAddMapView view, File[] files) {
		//in progress
		view.getFrame().setVisible(false);
		for(int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}
	}
}

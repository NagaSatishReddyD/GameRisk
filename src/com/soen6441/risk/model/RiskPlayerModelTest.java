package com.soen6441.risk.model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.view.RiskPlayerView;

/**
 * RiskPlayerModelTest class contains unit test cases to check the functionalities for in the RiskPlayerModel file
 * @author Tosin 
 * @author Yinka
 */
class RiskPlayerModelTest {
	
	static RiskBoardModel riskBoardModel; 
	static RiskPlayerView riskPlayerView; 
	static RiskPlayerModel riskPlayerModel;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		riskBoardModel = new RiskBoardModel(); 
		riskPlayerView = new RiskPlayerView(); 
		riskPlayerModel = new RiskPlayerModel();
	}
	
	/**
	 * This test case is used to test the  start board frame
	 * @throws NumberFormatException NoSuchAlgorithmException
	 */

	@Test
	
	void testStartBoard() throws NumberFormatException, NoSuchAlgorithmException, IOException {
		riskPlayerModel.startBoardFrame(riskPlayerView);
	}

}

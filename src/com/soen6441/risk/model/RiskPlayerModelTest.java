package com.soen6441.risk.model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.view.RiskPlayerView;

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

	@Test
	void testStartBoard() throws NumberFormatException, NoSuchAlgorithmException, IOException {
		riskPlayerModel.startBoardFrame(riskPlayerView);
	}

}

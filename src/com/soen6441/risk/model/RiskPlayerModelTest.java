package com.soen6441.risk.model;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.Continent;
import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;
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

package com.soen6441.risk.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.view.RiskGameView;
import com.soen6441.risk.view.RiskPlayerView;

class RiskGameModelTest {

	static RiskGameModel riskGameModel; 
	static RiskGameView riskGameView; 
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		riskGameModel = new RiskGameModel(); 
		riskGameView = new RiskGameView(); 
	}

	@Test
	void testShowPlayerDetailsMenu() {
		riskGameModel.showPlayerDetailsMenu(riskGameView);
		assertEquals(riskGameView.getGameStartframe().isVisible(), false);
	}
	
	@Test
	void testShowAddMapFrame() {
		riskGameModel.showPlayerDetailsMenu(riskGameView);
		assertEquals(riskGameView.getGameStartframe().isVisible(), false);
	}

}

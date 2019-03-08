package com.soen6441.risk.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.view.RiskGameView;

/**
 * RiskGameModelTest class contains unit test cases to check the functionalities for in the RiskGameModel file
 * @author Tosin 
 * @author Yinka
 */
class RiskGameModelTest {

	static RiskGameModel riskGameModel; 
	static RiskGameView riskGameView; 
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		riskGameModel = new RiskGameModel(); 
		riskGameView = new RiskGameView(); 
	}

	/**
	 * This test case is used to test and show the details of each player menu 
	 * Expected result to be success for this test case
	 */
	@Test
	void testShowPlayerDetailsMenu() {
		riskGameModel.showPlayerDetailsMenu(riskGameView);
		assertEquals(riskGameView.getGameStartframe().isVisible(), false);
	}
	
	/**
	 * This test case is used to test and show the adding of map frame
	 */
	@Test
	void testShowAddMapFrame() {
		riskGameModel.showPlayerDetailsMenu(riskGameView);
		assertEquals(riskGameView.getGameStartframe().isVisible(), false);
	}

}

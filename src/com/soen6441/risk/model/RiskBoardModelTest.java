package com.soen6441.risk.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.view.RiskBoardView;

class RiskBoardModelTest {

	static RiskBoardModel riskBoardModel;
	static Map<String, Continent> continentsMap;
	private static HashMap<String, Country> countriesMap;
	static RiskBoardView riskBoardView;
	static List<Player> playersData;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		riskBoardModel = new RiskBoardModel();
		continentsMap = new HashMap<String, Continent>();
		countriesMap = new HashMap<String, Country> ();
		riskBoardView = new RiskBoardView();
		playersData = new ArrayList<>();
		playersData.add(new Player("Test 1", 2));
		playersData.add(new Player("Test 2", 2));
	}
	
	@Test
	void testCreateCountinents() {
		String [] linesArray = {"North America=5","South America=2","Africa=3","Europe=5","Asia=7","Australia=2"};
		continentsMap.put("North America", new Continent("North America", 5));
		continentsMap.put("South America", new Continent("South America", 2));
		continentsMap.put("Africa", new Continent("Africa", 3));
		continentsMap.put("Europe", new Continent("Europe", 5));
		continentsMap.put("Asia", new Continent("Asia", 7));
		continentsMap.put("Australia", new Continent("Australia", 2));
		for(int i = 0; i< linesArray.length;i++) {
			riskBoardModel.createCountinents(linesArray[i]);
		}
		assertEquals(continentsMap.size(), riskBoardModel.getContinentsMap().size());
		assertEquals(5, riskBoardModel.getContinentsMap().get("North America").getArmiesGainedAfterConquer());
		assertEquals(3, riskBoardModel.getContinentsMap().get("Africa").getArmiesGainedAfterConquer());
	
	}
	
	@Test
	void testUpdateTheBoardScreenData() throws IOException, NoSuchAlgorithmException {
		int playersCount = 2;
		riskBoardModel.loadRequiredData("/GameRisk/resources/World.map");
		riskBoardModel.assignCountriesToPlayers(playersCount);
		riskBoardModel.updateTheBoardScreenData(riskBoardView, RiskGameConstants.REINFORCEMENT_PHASE);
		assertEquals(riskBoardView.getAttackBtn().isVisible(), false);
		assertEquals(riskBoardView.getEndAttackButton().isVisible(), false);
		assertEquals(riskBoardView.getReinforceBtn().isVisible(), true);
	}

	@Test
	void testMoveArmiesBetweenCountries() {
		riskBoardModel.moveArmiesBetweenCountries(riskBoardView);
	}

	@Test
	void testEndFortificationPhase() throws NoSuchAlgorithmException {
		riskBoardModel.assignCountriesToPlayers(2);
		riskBoardModel.endFortificationPhase(riskBoardView);
		assertEquals(riskBoardModel.isInitialPhase(), true);
	}

	@Test
	void testLoadRequiredData() throws IOException {
		riskBoardModel.loadRequiredData("/GameRisk/resources/World.map");
	}
	
	@Test
	void testAssignCountriesToPlayers() throws NoSuchAlgorithmException {
		int playersCount = 2;
		riskBoardModel.assignCountriesToPlayers(playersCount);
        assertEquals(riskBoardModel.getPlayersData().size(), playersCount);
	}
//
//	@Test
//	@Ignore
//	void testUpdateTheBoardScreenData() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Ignore
//	void testGetAdjacentCountriesForComboCountry() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Ignore
//	void testUpdateArmiesInCountries() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Ignore
//	void testUpdateFortificationData() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Ignore
//	void testMoveArmiesBetweenCountries() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Ignore
//	void testEndFortificationPhase() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Ignore
//	void testAttackBetweenCountries() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Ignore
	void testEndAttackPhase() {
		riskBoardModel.endAttackPhase(riskBoardView);
		assertEquals(riskBoardView.getReinforceBtn().isVisible(), false);
		assertEquals(riskBoardView.getAttackBtn().isVisible(), false);
		assertEquals(riskBoardView.getEndAttackButton().isVisible(), false);
		assertEquals(riskBoardView.getMoveArmiesBtn().isVisible(), true);
		assertEquals(riskBoardView.getEndFortificationBtn().isVisible(), true);
}

}

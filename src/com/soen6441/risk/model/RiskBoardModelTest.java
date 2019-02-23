package com.soen6441.risk.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.Continent;

class RiskBoardModelTest {

	static RiskBoardModel riskBoardModel;
	static Map<String, Continent> continentsMap;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		riskBoardModel = new RiskBoardModel();
		continentsMap = new HashMap<String, Continent>();
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



//	@Test
//	void testcreateContries() {
//		String [] linesArray = {"WesternAustralia,729,373,Australia,EasternAustralia,Indonesia,New Guinea", "EasternAustralia,779,381,Australia,Western Australia,New Guinea",
//				"NewGuinea,768,325,Australia,Indonesia,Western Australia,Eastern Australia","Indonesia,698,314,Australia,Siam,New Guinea,Western Australia"};
//		
//		}
//	}
//	@Ignore
//	void testLoadRequiredData() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Ignore
//	void testAssignCountriesToPlayers() {
//		fail("Not yet implemented");
//	}
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
//	void testEndAttackPhase() {
//		fail("Not yet implemented");
//	}

}

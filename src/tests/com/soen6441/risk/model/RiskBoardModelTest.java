package tests.com.soen6441.risk.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.Continent;
import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.model.RiskBoardModel;
import com.soen6441.risk.view.RiskBoardView;
/**
 * RiskBoardModelTest class contains unit test cases to check the functionalities for in the RiskBoardModel file
 * @author Tosin 
 * @author Yinka
 * 
 */
class RiskBoardModelTest {

	static RiskBoardModel riskBoardModel;
	static Map<String, Continent> continentsMap;
	private static HashMap<String, Country> countriesMap;
	static RiskBoardView riskBoardView;
	static List<Player> playersData;
	
	@BeforeAll
	
	/**
	 * test for setUpBefore: ALL variables instantiated for the tests are defined here
	 * 
	 */
	static void setUpBeforeClass() throws Exception {
		riskBoardModel = new RiskBoardModel();
		continentsMap = new HashMap<String, Continent>();
		countriesMap = new HashMap<String, Country> ();
		riskBoardView = new RiskBoardView();
		playersData = new ArrayList<>();
		playersData.add(new Player("Test 1", 2));
		playersData.add(new Player("Test 2", 2));
	}
	
	
	/**
	 * testCreateCountinents to check the CreateCountinents method works as expected, a sample Array
	 * of continent list created and compared with output from the object
	 */
	@Test
	public void testCreateCountinents() {
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
	
	
	/**
	 * testUpdateTheBoardScreenData to check theUpdateTheBoardScreenData method works as expected, a sample for 2 players was initialized
	 * and tested
	 * @throws IOException NoSuchAlgorithmException
	 */
	@Test
	public void testUpdateTheBoardScreenData() throws IOException, NoSuchAlgorithmException {
		int playersCount = 2;
		riskBoardModel.loadRequiredData(System.getProperty("user.dir")+"/resources/World.map");
		riskBoardModel.assignCountriesToPlayers(playersCount);
		riskBoardModel.updateTheBoardScreenData(riskBoardView, RiskGameConstants.REINFORCEMENT_PHASE);
		assertEquals(riskBoardView.getAttackBtn().isVisible(), false);
		assertEquals(riskBoardView.getEndAttackButton().isVisible(), false);
		assertEquals(riskBoardView.getReinforceBtn().isVisible(), true);
	}
	
	
	/**
	 * TestCalculateReinforcement is used to test the calculation of reinforce armies at the beginning of the player's turn
	 * Expected result to be success for this test case
	 */
	@Test
	public void testCalculateReinforcement() {
		Country country1 = new Country("Indonesia");
		country1.setPlayerName("Test 1");
		Country country2 = new Country("Guinea");
		country2.setPlayerName("Test 1");
		Country country3 = new Country("Western Australia");
		country3.setPlayerName("Test 1");
		Country country4 = new Country("Eastern Australia");
		country4.setPlayerName("Test 1");
		playersData.get(0).addTerritory(playersData.get(0), country1);
		playersData.get(0).addTerritory(playersData.get(0), country2);
		playersData.get(0).addTerritory(playersData.get(0), country3);
		playersData.get(0).addTerritory(playersData.get(0), country4);
		riskBoardModel.getContinentsMap().put("Australia", new Continent("Australia", 2));
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country1);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country2);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country3);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country4);
		int result = riskBoardModel.countArmiesBasedOnTerritories(playersData.get(0));
		assertEquals(5, result);
		
	}
	
	
	/**
	 * testReadInvalidMap is used to test when reading an invalid map file
	 * Expected to be failed when loading the map
	 * @throws IOException
	 */
	@Ignore
	public void testReadInvalidMap() throws IOException {
		riskBoardModel.loadRequiredData(System.getProperty("user.dir")+"/resources/001_I72_GhTroc 720.map");
	}

	
	/**
	 * This test case is used to test the EndFortification Phase
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	public void testEndFortificationPhase() throws NoSuchAlgorithmException {
		riskBoardModel.assignCountriesToPlayers(2);
		riskBoardModel.endFortificationPhase(riskBoardView);
		assertEquals(riskBoardModel.isInitialPhase(), true);
	}

	
	/**
	 * This test case is used to test the object loads the world map correctly
	  */
	@Test
	public void testLoadRequiredData() throws IOException {
		riskBoardModel.loadRequiredData(System.getProperty("user.dir")+"/resources/World.map");
	}
	/**
	 * testEndAttackPhase is used to test the End Attack phase correctly
	  */
	@Test
	void testEndAttackPhase() {
		riskBoardModel.endAttackPhase(riskBoardView);
		assertEquals(riskBoardView.getReinforceBtn().isVisible(), false);
		assertEquals(riskBoardView.getAttackBtn().isVisible(), false);
		assertEquals(riskBoardView.getEndAttackButton().isVisible(), false);
		assertEquals(riskBoardView.getMoveArmiesBtn().isVisible(), true);
		assertEquals(riskBoardView.getEndFortificationBtn().isVisible(), true);
}

}

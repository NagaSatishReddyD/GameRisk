package tests.com.soen6441.risk.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.soen6441.risk.Card;
import com.soen6441.risk.Continent;
import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.model.RiskBoardModel;
import com.soen6441.risk.view.RiskBoardView;

/**
 * RiskBoardModelTest class contains unit test cases to check the
 * functionalities for in the RiskBoardModel file
 * 
 * @author An Nguyen
 */
public class RiskBoardModelTest {

	static RiskBoardModel riskBoardModel;
	static Map<String, Continent> continentsMap;
	private static HashMap<String, Country> countriesMap;
	static RiskBoardView riskBoardView;
	static List<Player> playersData;

	/**
	 * test for setUpBefore: ALL variables instantiated for the tests are defined
	 * here
	 * 
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		riskBoardModel = new RiskBoardModel();
		continentsMap = new HashMap<String, Continent>();
		countriesMap = new HashMap<String, Country>();
		riskBoardView = new RiskBoardView();
		playersData = new ArrayList<>();
		playersData.add(new Player("Player 1", 2));
		playersData.add(new Player("Player 2", 2));
		playersData.add(new Player("Player 3", 2));
		playersData.add(new Player("Player 4", 2));
		int playersCount = 4;
		riskBoardModel.loadRequiredData(System.getProperty("user.dir") + "/resources/World.map", true);
		riskBoardModel.assignCountriesToPlayers(playersCount);
		playersData.get(0).getPlayerCards().add(new Card("Country A", "Infantry"));
		playersData.get(0).getPlayerCards().add(new Card("Country B", "Infantry"));
		playersData.get(0).getPlayerCards().add(new Card("Country C", "Cavalry"));
		playersData.get(0).getPlayerCards().add(new Card("Country D", "Infantry"));
	}

	/**
	 * testCreateCountinents to check the CreateCountinents method works as
	 * expected, a sample Array of continent list created and compared with output
	 * from the object
	 */
	@Test
	public void testCreateCountinents() {
		String[] linesArray = { "North America=5", "South America=2", "Africa=3", "Europe=5", "Asia=7", "Australia=2" };
		continentsMap.put("North America", new Continent("North America", 5));
		continentsMap.put("South America", new Continent("South America", 2));
		continentsMap.put("Africa", new Continent("Africa", 3));
		continentsMap.put("Europe", new Continent("Europe", 5));
		continentsMap.put("Asia", new Continent("Asia", 7));
		continentsMap.put("Australia", new Continent("Australia", 2));
		for (int i = 0; i < linesArray.length; i++) {
			riskBoardModel.createCountinents(linesArray[i]);
		}
		assertEquals(continentsMap.size(), riskBoardModel.getContinentsMap().size());
		assertEquals(5, riskBoardModel.getContinentsMap().get("North America").getArmiesGainedAfterConquer());
		assertEquals(3, riskBoardModel.getContinentsMap().get("Africa").getArmiesGainedAfterConquer());

	}

	/**
	 * testUpdateTheBoardScreenData to check theUpdateTheBoardScreenData method
	 * works as expected, a sample for 2 players was initialized and tested
	 */
	@Test
	public void testUpdateTheBoardScreenData(){
		riskBoardModel.updateTheBoardScreenData(riskBoardView, RiskGameConstants.REINFORCEMENT_PHASE);
		assertEquals(riskBoardView.getAttackBtn().isVisible(), false);
		assertEquals(riskBoardView.getEndAttackButton().isVisible(), false);
		assertEquals(riskBoardView.getReinforceBtn().isVisible(), true);
	}

	/**
	 * TestCalculateReinforcement is used to test the calculation of reinforce
	 * armies at the beginning of the player's turn Expected result to be success
	 * for this test case
	 */
	@Test
	public void testCalculateReinforcement() {
		Country country1 = new Country("Indonesia");
		country1.setPlayerName("Player 1");
		Country country2 = new Country("Guinea");
		country2.setPlayerName("Player 1");
		Country country3 = new Country("Western Australia");
		country3.setPlayerName("Player 1");
		Country country4 = new Country("Eastern Australia");
		country4.setPlayerName("Player 1");
		playersData.get(0).addTerritory(country1);
		playersData.get(0).addTerritory(country2);
		playersData.get(0).addTerritory(country3);
		playersData.get(0).addTerritory(country4);
		riskBoardModel.getContinentsMap().put("Australia", new Continent("Australia", 2));
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country1);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country2);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country3);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country4);
		int result = riskBoardModel.countArmiesBasedOnTerritories(playersData.get(0), riskBoardView);
		assertEquals(5, result);

	}

	@Test
	public void testGetBonusArmiesOnContinent() {
		Country country1 = new Country("Indonesia");
		country1.setPlayerName("Player 1");
		Country country2 = new Country("Guinea");
		country2.setPlayerName("Player 1");
		Country country3 = new Country("Western Australia");
		country3.setPlayerName("Player 1");
		Country country4 = new Country("Eastern Australia");
		country4.setPlayerName("Player 1");
		playersData.get(0).addTerritory(country1);
		playersData.get(0).addTerritory(country2);
		playersData.get(0).addTerritory(country3);
		playersData.get(0).addTerritory(country4);
		riskBoardModel.getContinentsMap().put("Australia", new Continent("Australia", 3));
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country1);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country2);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country3);
		riskBoardModel.getContinentsMap().get("Australia").addCountryInContinent(country4);
		int result = riskBoardModel.getBonusArmiesOnContinent(playersData.get(0));
		assertEquals(3, result);

	}

	/**
	 * testReadInvalidMap is used to test when reading an invalid map file Expected
	 * to be failed when loading the map
	 * 
	 */
	@Ignore
	public void testReadInvalidMap() {
		riskBoardModel.loadRequiredData(System.getProperty("user.dir") + "/resources/001_I72_GhTroc 720.map", true);
	}

	/**
	 * This test case is used to test the EndFortification Phase
	 */
	@Test
	public void testEndFortificationPhase(){
		riskBoardModel.endFortificationPhase(riskBoardView);
		assertEquals(riskBoardModel.isInitialPhase(), true);
	}

	/**
	 * This test case is used to test the object loads the world map correctly
	 */
	@Test
	public void testLoadRequiredData(){
		riskBoardModel.loadRequiredData(System.getProperty("user.dir") + "/resources/World.map", true);
	}

	/**
	 * testEndAttackPhase is used to test the End Attack phase correctly
	 */
	@Test
	public void testEndAttackPhase() {
		riskBoardModel.endAttackPhase(riskBoardView);
		assertEquals(riskBoardView.getReinforceBtn().isVisible(), false);
		assertEquals(riskBoardView.getAttackBtn().isVisible(), false);
		assertEquals(riskBoardView.getEndAttackButton().isVisible(), false);
		assertEquals(riskBoardView.getMoveArmiesBtn().isVisible(), true);
		assertEquals(riskBoardView.getEndFortificationBtn().isVisible(), true);
	}

	/**
	 * testGetTotalArmies is used to test the getTotalArmies method to calculate the
	 * total armies owned by a player
	 */
	@Test
	public void testGetTotalArmies() {
		Country country1 = new Country("Indonesia");
		country1.setPlayerName("Player 1");
		country1.setArmiesOnCountry(3);
		Country country2 = new Country("Guinea");
		country2.setPlayerName("Player 1");
		country2.setArmiesOnCountry(5);
		Country country3 = new Country("Western Australia");
		country3.setPlayerName("Player 1");
		country3.setArmiesOnCountry(10);
		Country country4 = new Country("Eastern Australia");
		country4.setPlayerName("Player 1");
		country4.setArmiesOnCountry(15);
		playersData.get(0).addTerritory(country1);
		playersData.get(0).addTerritory(country2);
		playersData.get(0).addTerritory(country3);
		playersData.get(0).addTerritory(country4);
		int armies = riskBoardModel.getTotalArmies(playersData.get(0));
		assertEquals(33, armies);
	}

	/**
	 * testGetOwnedContinent is used to test the getOwnedContinent method to check
	 * if a player owns any continent
	 */
	@Test
	public void testGetOwnedContinent() {
		Country country1 = new Country("Indonesia");
		country1.setPlayerName("Player 1");
		Country country2 = new Country("Guinea");
		country2.setPlayerName("Player 1");
		Country country3 = new Country("Western Australia");
		country3.setPlayerName("Player 1");
		Country country4 = new Country("Eastern Australia");
		country4.setPlayerName("Player 1");
		playersData.get(0).addTerritory(country1);
		playersData.get(0).addTerritory(country2);
		playersData.get(0).addTerritory(country3);
		playersData.get(0).addTerritory(country4);
		String result = riskBoardModel.getOwnedContinent(playersData.get(0)).trim();
		assertEquals("Australia", result);
	}

	/**
	 * This method is used to test the findImageName method to check after reading
	 * the map file whether the method get the correct image name file
	 */
	@Test
	public void testFindImageName() {
		assertEquals("world.bmp", riskBoardModel.getImageName());
	}

	/**
	 * This method is used to test if the correct buttons are visible for
	 * Reinforcement phase
	 */
	@Test
	public void testEnableDisableButtonsForReinforcementPhase() {
		riskBoardModel.enableDisableButtons(RiskGameConstants.REINFORCEMENT_PHASE, riskBoardView);
		assertEquals(true, riskBoardView.getReinforceBtn().isVisible());
	}

	/**
	 * This method is used to test if the correct buttons are visible for attack
	 * phase
	 */
	@Test
	public void testEnableDisableButtonsForAttackPhase() {
		riskBoardModel.enableDisableButtons(RiskGameConstants.ATTACK_PHASE, riskBoardView);
		assertEquals(true, riskBoardView.getAttackBtn().isVisible());
		assertEquals(true, riskBoardView.getEndAttackButton().isVisible());
	}

	/**
	 * This method is used to test if the correct buttons are visible for
	 * fortification phase
	 */
	@Test
	public void testEnableDisableButtonsForFortificationPhase() {
		riskBoardModel.enableDisableButtons(RiskGameConstants.FORTIFICATION_PHASE, riskBoardView);
		assertEquals(true, riskBoardView.getMoveArmiesBtn().isVisible());
		assertEquals(true, riskBoardView.getEndFortificationBtn().isVisible());
	}

	/**
	 * This method is used to test if the calculation of armies based on the number
	 * of countries owned by a player is correct
	 */
	@Test
	public void testGetBonusArmiesOnTerritories() {
		Country country1 = new Country("Country A");
		country1.setPlayerName("Test 1");
		Country country2 = new Country("Country B");
		country2.setPlayerName("Test 1");
		Country country3 = new Country("Country C");
		country3.setPlayerName("Test 1");
		Country country4 = new Country("Country D");
		country4.setPlayerName("Test 1");
		Country country5 = new Country("Country E");
		country5.setPlayerName("Test 1");
		Country country6 = new Country("Country F");
		country6.setPlayerName("Test 1");
		Country country7 = new Country("Country G");
		country7.setPlayerName("Test 1");
		Country country8 = new Country("Country H");
		country8.setPlayerName("Test 1");
		Country country9 = new Country("Country I");
		country9.setPlayerName("Test 1");
		Country country10 = new Country("Country J");
		country10.setPlayerName("Test 2");
		playersData.get(2).addTerritory(country1);
		playersData.get(2).addTerritory(country2);
		playersData.get(2).addTerritory(country3);
		playersData.get(2).addTerritory(country4);
		playersData.get(2).addTerritory(country5);
		playersData.get(2).addTerritory(country6);
		playersData.get(2).addTerritory(country7);
		playersData.get(2).addTerritory(country8);
		playersData.get(2).addTerritory(country9);
		playersData.get(3).addTerritory(country10);
		int result1 = riskBoardModel.getBonusArmiesOnTerritories(playersData.get(2));
		int result2 = riskBoardModel.getBonusArmiesOnTerritories(playersData.get(3));
		assertEquals(3, result1);
		assertEquals(3, result2);
	}

	/**
	 * This method is used to test if the Reinforcement phase information is shown
	 * correctly
	 */
	@Test
	public void testReinforcementPhaseView() {
		riskBoardModel.updateTheBoardScreenData(riskBoardView, RiskGameConstants.REINFORCEMENT_PHASE);
		String phase = riskBoardView.getCurrentPhase().getText().trim();
		assertEquals("Reinforcement phase", phase);
	}

	/**
	 * This method is used to test if the Attack phase information is shown
	 * correctly
	 */
	@Test
	public void testAttackPhaseView() {
		riskBoardModel.updateTheBoardScreenData(riskBoardView, RiskGameConstants.ATTACK_PHASE);
		String phase = riskBoardView.getCurrentPhase().getText().trim();
		assertEquals("Attack phase", phase);
	}

	/**
	 * This method is used to test if the Fortification phase information is shown
	 * correctly
	 */
	@Test
	public void testFortificationPhaseView() {
		riskBoardModel.updateTheBoardScreenData(riskBoardView, RiskGameConstants.FORTIFICATION_PHASE);
		String phase = riskBoardView.getCurrentPhase().getText().trim();
		assertEquals("Fortification phase", phase);
	}

	/**
	 * This method is used to test if after reading the map file, a correct list of
	 * countries is created
	 */
	@Test
	public void testCreateCountries() {
		assertEquals(42, riskBoardModel.getCountriesList().size());
	}

	/**
	 * This method is used to test if the checkExchangeSet method correctly checked
	 * if the player has a set of cards to exchange or not
	 */
	@Test
	public void testCheckExchangeSet() {
		playersData.get(0).getPlayerCards().add(new Card("Country A", "Infantry"));
		playersData.get(0).getPlayerCards().add(new Card("Country B", "Infantry"));
		playersData.get(0).getPlayerCards().add(new Card("Country C", "Cavalry"));
		playersData.get(0).getPlayerCards().add(new Card("Country D", "Infantry"));
		boolean result = riskBoardModel.checkExchangeSet(playersData.get(0));
		assertTrue(result);
	}

	/**
	 * This method is used to test if after loading the map, the correct number of
	 * cards is generated that equals to the total number of countries
	 */
	@Test
	public void testCreateCardsData() {
		assertEquals(42, riskBoardModel.getCardsList().size());
	}
	
	/**
	 * This method is used to test after exchanging the cards, the correct number of
	 * reinforcement armies is calculated
	 */
	@Test
	public void testGetExchangeCardsArmies() {
		int result = riskBoardModel.getExchangeCardsArmies(playersData.get(0));
		assertEquals(4, result);
	}

}

/**
 * 
 */
package tests.com.soen6441.risk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;

/**
 * Player class is used to test the {@link Player} methods.
 * @author Naga Satish Reddy
 *
 */
class PlayerTest {
	static Player player;
	static RiskBoardView riskBoardView;

	@BeforeClass
	static void setUpBeforeClass() {
		player = new Player("Player 1", 2);
		riskBoardView = new RiskBoardView();
	}

	/**
	 * This method is used to test the getNumberOfDicesPlayerWantsToThrow method
	 * to check if the method get the correct number of dices the player wants to use
	 */
	@Test
	public void testGetNumberOfDicesPlayerWantsToThrow() {
		int result = player.getNumberOfDicesPlayerWantsToThrow(10, riskBoardView, 3, true);
		int result2 = player.getNumberOfDicesPlayerWantsToThrow(2, riskBoardView, 3, true);
		assertEquals(3, result);
		assertEquals(2, result2);
	}
	
	/**
	 * This method is used to test if 2 adjacent countries is owned by one player or not
	 */
	@Test
	public void testIsCountriesOwnedByPlayers() {
		Country country1 = new Country("Country A");
		country1.setPlayerName("Player 1");
		Country country2 = new Country("Country B");
		country2.setPlayerName("Player 2");
		Country country3 = new Country("Country C");
		country3.setPlayerName("Player 1");
		boolean result1 = player.isCountriesOwnedByPlayers(country1, country2);
		boolean result2 = player.isCountriesOwnedByPlayers(country1, country3);
		assertEquals(false, result1);
		assertEquals(true, result2);
	}

}

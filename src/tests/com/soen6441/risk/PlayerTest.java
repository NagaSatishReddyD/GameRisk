/**
 * 
 */
package tests.com.soen6441.risk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;

/**
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

}

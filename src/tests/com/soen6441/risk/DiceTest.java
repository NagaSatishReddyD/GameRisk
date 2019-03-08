/**
 * 
 */
package tests.com.soen6441.risk;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soen6441.risk.Dice;

/**
 * DiceTest class is to test the order of dice rolled by player in attack phase
 * @author Naga Satish Reddy
 * @since 1.0
 *
 */
class DiceTest {

	static Dice dice;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dice = new Dice();
	}

	@Test
	void testDiceRoll() throws NoSuchAlgorithmException {
		Integer[] diceArray = dice.diceRoll(3);
		assertTrue(diceArray[0] >= diceArray[1] && diceArray[1] >= diceArray[2]);
	}

}

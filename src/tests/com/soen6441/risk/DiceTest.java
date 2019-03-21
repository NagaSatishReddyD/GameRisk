/**
 * 
 */
package tests.com.soen6441.risk;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import com.soen6441.risk.Dice;

/**
 * DiceTest class is to test the order of dice rolled by player in attack phase
 * @author Naga Satish Reddy
 * @since 1.0
 *
 */
public class DiceTest {

	static Dice dice;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dice = new Dice();
	}

	@Test
	public void testDiceRoll() throws NoSuchAlgorithmException {
		Integer[] diceArray = dice.diceRoll(3);
		assertTrue(diceArray[0] >= diceArray[1] && diceArray[1] >= diceArray[2]);
	}

}

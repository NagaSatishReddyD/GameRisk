package TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.soen6441.risk.Dice;
import com.soen6441.risk.Player;

import tests.com.soen6441.risk.DiceAndPlayerTestSuite;
import tests.com.soen6441.risk.model.ModelTestSuite;


@RunWith(Suite.class)
@SuiteClasses({DiceAndPlayerTestSuite.class,
			   ModelTestSuite.class})
public class AllTestsSuite {

}

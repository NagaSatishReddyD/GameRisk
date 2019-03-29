package TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.com.soen6441.risk.DiceAndPlayerTestSuite;
import tests.com.soen6441.risk.model.ModelTestSuite;


@RunWith(Suite.class)
@SuiteClasses({DiceAndPlayerTestSuite.class,
			   ModelTestSuite.class})

/**
 * This test suite will run all of the test cases in the project
 * @author An Nguyen
 */
public class AllTestsSuite {

}

package tests.com.soen6441.risk.model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;

import com.soen6441.risk.controller.RiskBoardController;
import com.soen6441.risk.model.RiskBoardModel;
import com.soen6441.risk.model.RiskPlayerModel;
import com.soen6441.risk.view.RiskBoardView;
import com.soen6441.risk.view.RiskPlayerView;

/**
 * RiskPlayerModelTest class contains unit test cases to check the functionalities for in the RiskPlayerModel file
 * @author An Nguyen
 */
class RiskPlayerModelTest {
	
	static RiskBoardModel riskBoardModel; 
	static RiskPlayerView riskPlayerView; 
	static RiskPlayerModel riskPlayerModel;
	static RiskBoardView riskBoardView;
	static RiskBoardController riskBoardController;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		riskBoardModel = new RiskBoardModel(); 
		riskBoardView = new RiskBoardView();
		riskPlayerView = new RiskPlayerView(); 
		riskPlayerModel = new RiskPlayerModel();
		riskBoardController = new RiskBoardController(riskBoardModel, riskBoardView);
	}
	
	/**
	 * This test case is used to test the  start board frame
	 * @throws NumberFormatException NoSuchAlgorithmException
	 */

	@Ignore
	void testStartBoard() throws NumberFormatException, NoSuchAlgorithmException, IOException {
		riskPlayerModel.startBoardFrame(riskPlayerView);
		//assertEquals(false, riskPlayerView.getPlayerStartframe().isVisible());
	}

}

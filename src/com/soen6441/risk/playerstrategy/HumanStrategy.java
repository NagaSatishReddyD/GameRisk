/**
 * 
 */
package com.soen6441.risk.playerstrategy;

import javax.swing.JOptionPane;

import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;

/**
 * @author Naga Satish Reddy
 *
 */
public class HumanStrategy implements PlayerBehaviourStrategyInterface{

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#reinforceArmyToCountry(com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, boolean)
	 */
	@Override
	public Integer reinforceArmyToCountry(Country country, RiskBoardView riskBoardView, boolean isInitialPhase, Player player) {
		Object [] possibilities = new Object [player.getArmyCountAvailable()];
		for(int index = 0; index < player.getArmyCountAvailable(); index++) {
			possibilities[index] = index+1;
		}
		Integer selectedValue = (Integer)JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
				JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
		return selectedValue;
	}

}

/**
 * 
 */
package com.soen6441.risk.playerstrategy;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;

/**
 * @author Naga Satish Reddy
 *
 */
public class BenevolentStrategy implements PlayerBehaviourStrategyInterface{

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#reinforceArmyToCountry(com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, boolean, com.soen6441.risk.Player)
	 */
	@Override
	public Integer reinforceArmyToCountry(Country country, RiskBoardView riskBoardView, boolean isInitialPhase,
			Player player) {
		Integer selectedValue = null;
		if(isInitialPhase) {
			Random random;
			try {
				random = SecureRandom.getInstanceStrong();
				selectedValue = random.nextInt(player.getArmyCountAvailable()+1);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}else {
			selectedValue = player.getArmyCountAvailable();
		}
		return selectedValue;
	}

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#attackBetweenCountries(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player, com.soen6441.risk.Player)
	 */
	@Override
	public boolean attackBetweenCountries(Country currentPlayerCountry, Country opponentPlayerCountry,
			RiskBoardView riskBoardView, Player opponentPlayer, Player currentPlayer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#foriticateArmies(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player)
	 */
	@Override
	public void foriticateArmies(Country country, Country adjacentCountry, RiskBoardView riskBoardview, Player player) {
		for(Country adjacent : country.getAdjacentCountries()) {
			if(adjacent.getPlayerName().equals(country.getPlayerName()) && adjacent.getArmiesOnCountry() > country.getArmiesOnCountry()) {
				int extraArmies = adjacent.getArmiesOnCountry() - country.getArmiesOnCountry();
				if(extraArmies % 2 ==0 ) {
					adjacent.decreaseArmiesOnCountry(extraArmies / 2);
					country.incrementArmiesOnCountry(extraArmies / 2);
				}else {
					adjacent.decreaseArmiesOnCountry((extraArmies-1) / 2);
					country.incrementArmiesOnCountry((extraArmies-1) / 2);
				}
			}
		}
	}

}

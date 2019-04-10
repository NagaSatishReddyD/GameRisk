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

	/** This method contains the reinforcement logic for benevolent player
	 * The player will reinforce its weakest country it owns if the current phase is the reinforcement phase and it is not the first turn
	 * If it is the first turn of the game, it will place a random number of armies on a random country it owns
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#reinforceArmyToCountry(com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, boolean, com.soen6441.risk.Player)
	 * @return 
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

	/** This methods will not have any logic as the benevolent player behavior never attack in the game
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#attackBetweenCountries(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player, com.soen6441.risk.Player)
	 */
	@Override
	public boolean attackBetweenCountries(Country currentPlayerCountry, Country opponentPlayerCountry,
			RiskBoardView riskBoardView, Player opponentPlayer, Player currentPlayer) {
		// TODO Auto-generated method stub
		return false;
	}

	/** This method contains the fortification logic for benevolent player
	 * The player will always reinforce its weakest country it owns
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#foriticateArmies(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player)
	 */
	@Override
	public void foriticateArmies(Country currentPlayerCountry, Country adjacentCountry, RiskBoardView riskBoardview, Player player) {
		for(Country adjacent : currentPlayerCountry.getAdjacentCountries()) {
			if(adjacent.getPlayerName().equals(currentPlayerCountry.getPlayerName()) && adjacent.getArmiesOnCountry() > currentPlayerCountry.getArmiesOnCountry()) {
				int extraArmies = adjacent.getArmiesOnCountry() - currentPlayerCountry.getArmiesOnCountry();
				if(extraArmies % 2 ==0 ) {
					System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" moved to "+(extraArmies / 2)+" armies to "+ adjacent.getPlayerName()+" "+adjacent.getCountryName());
					adjacent.decreaseArmiesOnCountry(extraArmies / 2);
					currentPlayerCountry.incrementArmiesOnCountry(extraArmies / 2);
				}else {
					System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" moved to "+((extraArmies-1) / 2)+" armies to "+ adjacent.getPlayerName()+" "+adjacent.getCountryName());
					adjacent.decreaseArmiesOnCountry((extraArmies-1) / 2);
					currentPlayerCountry.incrementArmiesOnCountry((extraArmies-1) / 2);
				}
			}
		}
	}

}

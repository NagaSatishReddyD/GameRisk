/**
 * 
 */
package com.soen6441.risk.playerstrategy;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;

/**
 * @author Naga Satish Reddy
 *
 */
public class CheaterStrategy implements PlayerBehaviourStrategyInterface{

	/** 
	 * This method contains the reinforcement logic for cheater player behavior. It will place a random number of armies to a random country
	 * This method will only be triggered if it is the first turn of the game
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#reinforceArmyToCountry(com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, boolean, com.soen6441.risk.Player)
	 * @return the number of armies that is generated randomly
	 */
	@Override
	public Integer reinforceArmyToCountry(Country country, RiskBoardView riskBoardView, boolean isInitialPhase,
			Player player) {
		Integer selectedValue = null;
		Random random;
		try {
			random = SecureRandom.getInstanceStrong();
			selectedValue = random.nextInt(player.getArmyCountAvailable()+1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return selectedValue;
	}

	/** 
	 * this method contains the attack logic for cheater player behavior.
	 * It will automatically conquer all of the opponent countries that are adjacent to its countries
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#attackBetweenCountries(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player, com.soen6441.risk.Player)
	 * @return true to indicate the method ends
	 */
	@Override
	public boolean attackBetweenCountries(Country currentPlayerCountry, Country opponentPlayerCountry,
			RiskBoardView riskBoardView, Player opponentPlayer, Player currentPlayer) {
		System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" attacking "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
		opponentPlayer.getTerritoryOccupied().remove(opponentPlayerCountry);
		currentPlayer.getTerritoryOccupied().add(opponentPlayerCountry);
		opponentPlayerCountry.setPlayerName(currentPlayer.getPlayerName());
		opponentPlayerCountry.setArmiesOnCountry(1);
		currentPlayerCountry.incrementArmiesOnCountry(currentPlayerCountry.getArmiesOnCountry() - 1);
		System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" conquered "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
		currentPlayer.isOpponentPlayerOutOfGame(currentPlayer, opponentPlayer);
		return true;
	}

	/**
	 * This method contains the fortification logic for cheater player behavior
	 * It will double the number of armies on its countries if the country has neighbors that are belong to others players
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#foriticateArmies(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player)
	 */
	@Override
	public void foriticateArmies(Country currentCountry, Country adjacentCountry, RiskBoardView riskBoardview, Player player) {
		if(checkForFortification(currentCountry.getAdjacentCountries(), currentCountry)) {
			System.out.println(currentCountry.getPlayerName()+" "+currentCountry.getCountryName()+" dobuled to "+(currentCountry.getArmiesOnCountry()*2));
			currentCountry.setArmiesOnCountry(currentCountry.getArmiesOnCountry()*2);
		}
	}

	/**
	 * This method is used to check if the countries owned by the cheater player have neighbors that are belong to others players
	 * @param adjacentCountries, the list of adjacent countries of the current country being checked
	 * @param currentCountry, the current country that is being checked
	 * @return true if there is neighbor belongs to other players
	 * @return false if there is no neighbor belongs to other players
	 */
	private boolean checkForFortification(List<Country> adjacentCountries, Country currentCountry) {
		for(Country country : adjacentCountries) {
			if(!country.getPlayerName().equals(currentCountry.getPlayerName())) {
				return true;
			}
		}
		return false;
	}

}

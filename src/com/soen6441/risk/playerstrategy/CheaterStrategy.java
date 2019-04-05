/**
 * 
 */
package com.soen6441.risk.playerstrategy;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;

/**
 * @author Naga Satish Reddy
 *
 */
public class CheaterStrategy implements PlayerBehaviourStrategyInterface{

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#reinforceArmyToCountry(com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, boolean, com.soen6441.risk.Player)
	 */
	@Override
	public Integer reinforceArmyToCountry(Country country, RiskBoardView riskBoardView, boolean isInitialPhase,
			Player player) {
		Integer selectedValue = null;
		if(!isInitialPhase) {
			player.getTerritoryOccupied().forEach(eachCountry -> {
				eachCountry.setArmiesOnCountry(country.getArmiesOnCountry()*2);
			});
		}
		if(isInitialPhase) {
			Random random;
			try {
				random = SecureRandom.getInstanceStrong();
				selectedValue = random.nextInt(player.getArmyCountAvailable()+1);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return selectedValue;
	}

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#attackBetweenCountries(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player, com.soen6441.risk.Player)
	 */
	@Override
	public boolean attackBetweenCountries(Country currentPlayerCountry, Country opponentPlayerCountry,
			RiskBoardView riskBoardView, Player opponentPlayer, Player currentPlayer) {
		opponentPlayer.getTerritoryOccupied().remove(opponentPlayerCountry);
		currentPlayer.getTerritoryOccupied().add(opponentPlayerCountry);
		opponentPlayerCountry.setPlayerName(currentPlayer.getPlayerName());
		opponentPlayerCountry.setArmiesOnCountry(1);
		currentPlayerCountry.incrementArmiesOnCountry(currentPlayerCountry.getArmiesOnCountry() - 1);
		currentPlayer.isOpponentPlayerOutOfGame(currentPlayer, opponentPlayer);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#foriticateArmies(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player)
	 */
	@Override
	public void foriticateArmies(Country currentCountry, Country adjacentCountry, RiskBoardView riskBoardview, Player player) {
		Map<String, Country> adjacentCountriesData = currentCountry.getAdjacentCountries().stream().map(data -> data).collect(Collectors.toMap(Country::getPlayerName, Function.identity()));
		if(checkForFortification(adjacentCountriesData, currentCountry)) {
			currentCountry.setArmiesOnCountry(currentCountry.getArmiesOnCountry()*2);
		}
	}

	/**
	 * @param data
	 * @param currentCountry 
	 * @return
	 */
	private boolean checkForFortification(Map<String, Country> data, Country currentCountry) {
		for(Entry<String, Country> country : data.entrySet()) {
			if(!country.getKey().equals(currentCountry.getPlayerName())) {
				return true;
			}
		}
		return false;
	}

}

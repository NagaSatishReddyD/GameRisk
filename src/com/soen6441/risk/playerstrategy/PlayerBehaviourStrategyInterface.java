package com.soen6441.risk.playerstrategy;

import com.soen6441.risk.Country;
import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;

/**
 * @author Naga Satish Reddy
 *
 */
public interface PlayerBehaviourStrategyInterface {

	Integer reinforceArmyToCountry(Country country, RiskBoardView riskBoardView, boolean isInitialPhase, Player player);
	boolean attackBetweenCountries(Country currentPlayerCountry, Country opponentPlayerCountry,
			RiskBoardView riskBoardView, Player opponentPlayer, Player currentPlayer);
	void foriticateArmies(Country country, Country adjacentCountry, RiskBoardView riskBoardview, Player player); 
}

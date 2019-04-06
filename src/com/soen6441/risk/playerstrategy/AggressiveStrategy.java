/**
 * 
 */
package com.soen6441.risk.playerstrategy;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.Random;

import com.soen6441.risk.Country;
import com.soen6441.risk.Dice;
import com.soen6441.risk.Player;
import com.soen6441.risk.view.RiskBoardView;

/**
 * @author Naga Satish Reddy
 *
 */
public class AggressiveStrategy implements PlayerBehaviourStrategyInterface{

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#reinforceArmyToCountry(com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, boolean)
	 */
	@Override
	public Integer reinforceArmyToCountry(Country country, RiskBoardView riskBoardView, boolean isInitialPhase, Player player) {
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
		boolean isOcuppiedTerritory = false;
		int currentPlayerAttackingArmies= currentPlayerCountry.getArmiesOnCountry() - 1;
		int opponentDefendingArmies = opponentPlayerCountry.getArmiesOnCountry();
		currentPlayerCountry.decreaseArmiesOnCountry(currentPlayerAttackingArmies);
		opponentPlayerCountry.decreaseArmiesOnCountry(opponentDefendingArmies);
		do {
			System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+"("+currentPlayerAttackingArmies+")"+
					" attacking "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName()+"("+opponentDefendingArmies+")");
			int currentPlayerDicesToRoll = currentPlayerAttackingArmies > 3 ? 3: currentPlayerAttackingArmies;
			int opponentPlayerDicesToRoll = opponentDefendingArmies > 2 ? 2:opponentDefendingArmies;
			Integer[] currentPlayerDice = new Dice().diceRoll(currentPlayerDicesToRoll);
			System.out.print("Current Player Dices: ");
			opponentPlayer.printDicesValues(currentPlayerDice);
			Integer[] opponentPlayerDice = new Dice().diceRoll(opponentPlayerDicesToRoll);
			System.out.print("Opponent Player Dices: ");
			opponentPlayer.printDicesValues(opponentPlayerDice);
			if(currentPlayerDice[0] > opponentPlayerDice[0])
				opponentDefendingArmies--;
			else
				currentPlayerAttackingArmies--;
			if(opponentPlayerDice.length > 1 && currentPlayerDice.length > 1) {
				if(currentPlayerDice[1] > opponentPlayerDice[1])
					opponentDefendingArmies--;
				else
					currentPlayerAttackingArmies--;
			}
		}while(currentPlayerAttackingArmies != 0 && opponentDefendingArmies!=0);

		if(currentPlayerAttackingArmies > 0) {
			System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" conquered "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
			opponentPlayer.getTerritoryOccupied().remove(opponentPlayerCountry);
			currentPlayer.getTerritoryOccupied().add(opponentPlayerCountry);
			opponentPlayerCountry.setPlayerName(currentPlayer.getPlayerName());
			opponentPlayerCountry.setArmiesOnCountry(1);
			currentPlayerCountry.incrementArmiesOnCountry(currentPlayerAttackingArmies - 1);
			isOcuppiedTerritory = true;
			currentPlayer.isOpponentPlayerOutOfGame(currentPlayer, opponentPlayer);
		}else if(currentPlayerAttackingArmies == 0 ){
			opponentPlayerCountry.setArmiesOnCountry(opponentDefendingArmies);
			System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" lost to "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
		}
		return isOcuppiedTerritory;
	}

	/* (non-Javadoc)
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#foriticateArmies(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player)
	 */
	@Override
	public void foriticateArmies(Country currentPlayerCountry, Country adjacentCountry, RiskBoardView riskBoardview, Player player) {
		boolean isMoved = false;
		while(!isMoved) {
			player.getTerritoryOccupied().sort(Comparator.comparing(Country::getArmiesOnCountry).reversed());
			Country highestArmiesCountry = player.getTerritoryOccupied().get(0);
			if(otherPlayerAdjacent(highestArmiesCountry)) {
				highestArmiesCountry.getAdjacentCountries().stream().forEach(adjacent -> {
					if(adjacent.getPlayerName().equals(highestArmiesCountry.getPlayerName())) {
						int armies = adjacent.getArmiesOnCountry() - 1;
						adjacent.decreaseArmiesOnCountry(armies);
						highestArmiesCountry.incrementArmiesOnCountry(armies);
					}
				});
				isMoved = true;
			}else {
				for(Country adjacent: highestArmiesCountry.getAdjacentCountries()) {
					if(otherPlayerAdjacent(adjacent)) {
						int armies = highestArmiesCountry.getArmiesOnCountry() - 1;
						highestArmiesCountry.decreaseArmiesOnCountry(armies);
						adjacent.incrementArmiesOnCountry(armies);
						isMoved = true;
						break;
					}
				}
			}
		}
	}

	/**
	 * @param highestArmiesCountry
	 * @return
	 */
	private boolean otherPlayerAdjacent(Country highestArmiesCountry) {
		for(Country adjacentCountry:highestArmiesCountry.getAdjacentCountries()) {
			if(!adjacentCountry.getPlayerName().equals(highestArmiesCountry.getPlayerName()))
				return true;
		}
		return false;
	}
}

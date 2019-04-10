/**
 * 
 */
package com.soen6441.risk.playerstrategy;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

import com.soen6441.risk.Country;
import com.soen6441.risk.Dice;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.view.RiskBoardView;

/**
 * @author Naga Satish Reddy
 *
 */
public class RandomStrategy implements PlayerBehaviourStrategyInterface{

	/**
	 * This method contains the reinforcement logic for random player in the game
	 * The random player will randomly select a country it owns and then place a random number of armies on that country
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#reinforceArmyToCountry(com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, boolean, com.soen6441.risk.Player)
	 * @return the random number of armies the random player is going to place
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
	 * This method contains the attack logic for random player in the game
	 * The random player will select a random number of armies, attack a random country
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#attackBetweenCountries(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player, com.soen6441.risk.Player)
	 * @return true if the random player conquered the opponent territory
	 * @return false if the random player failed to conquer the opponent territory
	 */
	@Override
	public boolean attackBetweenCountries(Country currentPlayerCountry, Country opponentPlayerCountry,
			RiskBoardView riskBoardView, Player opponentPlayer, Player currentPlayer) {
		boolean isOcuppiedTerritory = false;
		System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" attacking "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
		Random random;
		try {
			random = SecureRandom.getInstanceStrong();
			Object [] possibilities = new Object [currentPlayerCountry.getArmiesOnCountry()];
			possibilities[0] = RiskGameConstants.ALL_OUT;
			for(int index = 1; index < currentPlayerCountry.getArmiesOnCountry(); index++) {
				possibilities[index] = index;
			}

			Object currentPlayerOption = 0;
			int currentPlayerDicesToRoll = 0;
			int opponentPlayerDicesToRoll = 0;
			if(possibilities.length >= 1) {
				currentPlayerOption = possibilities[random.nextInt(possibilities.length)];
			}

			int opponentDefendingArmies = opponentPlayerCountry.getArmiesOnCountry();
			int currentPlayerAttackingArmies = 0;
			if(currentPlayerOption.equals(RiskGameConstants.ALL_OUT)) {
				currentPlayerAttackingArmies  = currentPlayerCountry.getArmiesOnCountry() - 1;

			}else if((Integer)currentPlayerOption > 0) {
				currentPlayerAttackingArmies = (Integer)currentPlayerOption;
			}
			currentPlayerDicesToRoll = currentPlayerAttackingArmies > 3 ?random.nextInt(3)+1: random.nextInt(currentPlayerAttackingArmies)+1;
			opponentPlayerDicesToRoll = opponentDefendingArmies> 2 ?random.nextInt(2)+1: random.nextInt(opponentDefendingArmies)+1;

			if(currentPlayerDicesToRoll != 0 && opponentPlayerDicesToRoll != 0) {
				currentPlayerCountry.decreaseArmiesOnCountry(currentPlayerAttackingArmies);
				opponentPlayerCountry.decreaseArmiesOnCountry(opponentDefendingArmies);
				do {
					currentPlayerDicesToRoll = currentPlayerAttackingArmies > 3 ?random.nextInt(3)+1: random.nextInt(currentPlayerAttackingArmies)+1;
					opponentPlayerDicesToRoll = opponentDefendingArmies> 2 ?random.nextInt(2)+1: random.nextInt(opponentDefendingArmies)+1;
					System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+"("+currentPlayerAttackingArmies+")"+
							" attacking "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName()+"("+opponentDefendingArmies+")");
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
				} while (currentPlayerAttackingArmies != 0 && opponentDefendingArmies != 0);
				if(currentPlayerAttackingArmies > 0) {
					Integer armiesOnCountry;
					do {
						Integer[] possibilitiesArmies = new Integer [currentPlayerAttackingArmies];
						for(int index = 0; index < currentPlayerAttackingArmies; index++) {
							possibilitiesArmies[index] = index+1;
						}
						armiesOnCountry = possibilitiesArmies[random.nextInt(possibilitiesArmies.length)];
						System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" conquered "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
						if(Objects.nonNull(armiesOnCountry)) {
							opponentPlayer.getTerritoryOccupied().remove(opponentPlayerCountry);
							currentPlayer.getTerritoryOccupied().add(opponentPlayerCountry);
							opponentPlayerCountry.setPlayerName(currentPlayer.getPlayerName());
							opponentPlayerCountry.setArmiesOnCountry(armiesOnCountry);
							currentPlayerCountry.incrementArmiesOnCountry(currentPlayerAttackingArmies - armiesOnCountry);
							isOcuppiedTerritory = true;
							currentPlayer.isOpponentPlayerOutOfGame(currentPlayer, opponentPlayer);
						}
					}while(Objects.isNull(armiesOnCountry));
				}else if(currentPlayerAttackingArmies == 0 ){
					opponentPlayerCountry.setArmiesOnCountry(opponentDefendingArmies);
					System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" lost to "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
				}
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isOcuppiedTerritory;
	}

	/**
	 * This method contains the fortification logic for random player
	 * The random player will select a random number of armies and move the armies to a random country it owns
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#foriticateArmies(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player)
	 */
	@Override
	public void foriticateArmies(Country currentPlayerCountry, Country adjacentCountry, RiskBoardView riskBoardview, Player player) {
		if(currentPlayerCountry.getArmiesOnCountry()> 1 && adjacentCountry.getPlayerName().equals(currentPlayerCountry.getPlayerName())) {
				try {
					Random random = SecureRandom.getInstanceStrong();
					int armiesToMove = random.nextInt(currentPlayerCountry.getArmiesOnCountry());
					System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" moved to "+(armiesToMove)+" armies to "+ adjacentCountry.getPlayerName()+" "+adjacentCountry.getCountryName());
					currentPlayerCountry.decreaseArmiesOnCountry(armiesToMove);
					adjacentCountry.incrementArmiesOnCountry(armiesToMove);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}

package com.soen6441.risk.playerstrategy;

import java.util.Objects;

import javax.swing.JOptionPane;

import com.soen6441.risk.Country;
import com.soen6441.risk.Dice;
import com.soen6441.risk.Player;
import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.view.RiskBoardView;

/**
 * @author Naga Satish Reddy
 *
 */
public class HumanStrategy implements PlayerBehaviourStrategyInterface{

	/**
	 * This method contains the logic for reinforcement of human player
	 * The human player can choose any country any number of armies to reinforce it
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#reinforceArmyToCountry(com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, boolean)
	 * @return the number of armies selected by the player
	 */
	@Override
	public Integer reinforceArmyToCountry(Country country, RiskBoardView riskBoardView, boolean isInitialPhase, Player player) {
		Object [] possibilities = new Object [player.getArmyCountAvailable()];
		for(int index = 0; index < player.getArmyCountAvailable(); index++) {
			possibilities[index] = index+1;
		}
		return (Integer)JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
				JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
	}

	/**
	 * This method contains the attack logic for human player
	 * The human player can choose any country to attack if they are adjacent to any country the human player owns
	 * The human player can select a specific number of armies and dice to attack or select "All Out" to get the maximum number of armies and dice
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#attackBetweenCountries(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView, com.soen6441.risk.Player)
	 * @return true if the human player conquered the opponent country
	 * @return false if the human player failed to conquer the opponent country
	 */
	@Override
	public boolean attackBetweenCountries(Country currentPlayerCountry, Country opponentPlayerCountry,
			RiskBoardView riskBoardView, Player opponentPlayer, Player currentPlayer) {
		boolean isOcuppiedTerritory = false;
		Object [] possibilities = new Object [currentPlayerCountry.getArmiesOnCountry()];
		possibilities[0] = RiskGameConstants.ALL_OUT;
		for(int index = 1; index < currentPlayerCountry.getArmiesOnCountry(); index++) {
			possibilities[index] = index;
		}

		Object currentPlayerOption = 0;
		int currentPlayerDicesToRoll = 0;
		int opponentPlayerDicesToRoll = 0;
		if(possibilities.length >= 1) {
			currentPlayerOption = JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"How many armies to be used to attack", "Armies To Attack",
					JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
		}
		int opponentDefendingArmies = opponentPlayerCountry.getArmiesOnCountry();
		int currentPlayerAttackingArmies = 0;
		if(currentPlayerOption.equals(RiskGameConstants.ALL_OUT)) {
			currentPlayerAttackingArmies  = currentPlayerCountry.getArmiesOnCountry() - 1;
			currentPlayerDicesToRoll = opponentPlayer.getNumberOfDicesPlayerWantsToThrow(currentPlayerAttackingArmies, riskBoardView, 
					currentPlayerAttackingArmies > 3? 3 : currentPlayerAttackingArmies, true);
			opponentPlayerDicesToRoll = opponentPlayer.getNumberOfDicesPlayerWantsToThrow(opponentDefendingArmies, riskBoardView, 
					opponentDefendingArmies > 2 ? 2 : opponentDefendingArmies, false);

		}else if((Integer)currentPlayerOption > 0) {
			currentPlayerAttackingArmies = (Integer)currentPlayerOption;
			currentPlayerDicesToRoll = opponentPlayer.getNumberOfDicesPlayerWantsToThrow(currentPlayerAttackingArmies, riskBoardView, -1, true);
			opponentPlayerDicesToRoll = opponentPlayer.getNumberOfDicesPlayerWantsToThrow(opponentDefendingArmies, riskBoardView, -1, false);
		}
		if(currentPlayerDicesToRoll != 0 && opponentPlayerDicesToRoll != 0) {
			currentPlayerCountry.decreaseArmiesOnCountry(currentPlayerAttackingArmies);
			opponentPlayerCountry.decreaseArmiesOnCountry(opponentDefendingArmies);
			do {
				System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+"("+currentPlayerAttackingArmies+")"+
						" attacking "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName()+"("+opponentDefendingArmies+")");
				currentPlayerDicesToRoll = opponentPlayer.getNumberOfDicesPlayerWantsToThrow(currentPlayerAttackingArmies, riskBoardView, currentPlayerDicesToRoll, true);
				opponentPlayerDicesToRoll = opponentPlayer.getNumberOfDicesPlayerWantsToThrow(opponentDefendingArmies, riskBoardView, opponentPlayerDicesToRoll, false);
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
				System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" conquered "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
				JOptionPane.showMessageDialog(riskBoardView.getBoardFrame(), currentPlayer.getPlayerName()+" WON ");
				do {
					possibilities = new Object [currentPlayerAttackingArmies];
					for(int index = 0; index < currentPlayerAttackingArmies; index++) {
						possibilities[index] = index+1;
					}
					armiesOnCountry = (Integer) JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"How many armies you want to place on conquered terriroty", "Armies To Attack",
							JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
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
				JOptionPane.showMessageDialog(riskBoardView.getBoardFrame(), opponentPlayerCountry.getPlayerName()+" WON ");
				System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" lost to "+ opponentPlayerCountry.getPlayerName()+" "+opponentPlayerCountry.getCountryName());
			}
		}else {
			JOptionPane.showMessageDialog(riskBoardView.getBoardFrame(), "You don't have enough armies to attack");
		}
		return isOcuppiedTerritory;
	}

	/**
	 * This method contains the fortification logic for human player
	 * The human player can select any country it owns and select a specific number of armies to move armies between two adjacent countries
	 * @see com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface#foriticateArmies(com.soen6441.risk.Country, com.soen6441.risk.Country, com.soen6441.risk.view.RiskBoardView)
	 */
	@Override
	public void foriticateArmies(Country currentPlayerCountry, Country adjacentCountry, RiskBoardView riskBoardview,Player player) {
		if(currentPlayerCountry.getArmiesOnCountry() == 0){
			JOptionPane.showMessageDialog(riskBoardview.getBoardFrame(), "No armies on selected country to move");
		}else if(!player.isCountriesOwnedByPlayers(currentPlayerCountry, adjacentCountry)) {
			JOptionPane.showMessageDialog(riskBoardview.getBoardFrame(), "Selected Adjacent Country is owned by another player");
		}else {
			Object [] possibilities = new Object [currentPlayerCountry.getArmiesOnCountry() - 1];
			for(int index = 0; index < possibilities.length; index++) {
				possibilities[index] = index+1;
			}
			Integer selectedValue = (Integer)JOptionPane.showInputDialog(riskBoardview.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
					JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);

			if(Objects.nonNull(selectedValue)) {
				System.out.println(currentPlayerCountry.getPlayerName()+" "+currentPlayerCountry.getCountryName()+" moved to "+(selectedValue)+" armies to "+ adjacentCountry.getPlayerName()+" "+adjacentCountry.getCountryName());
				currentPlayerCountry.decreaseArmiesOnCountry(selectedValue);
				adjacentCountry.incrementArmiesOnCountry(selectedValue);
				riskBoardview.getArmiesCountAvailableLabel().setText(String.valueOf(player.getArmyCountAvailable()));
			}
		}
	
	}

}

package com.soen6441.risk;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import com.soen6441.risk.view.RiskBoardView;

/**
 * Player class do the operation of players.
 * @author Naga Satish Reddy
 *
 */
public class Player {
	
	String playerName;
	List<Country> territoryOccupied;
	int armyCountAvailable;
	List<Card> playerCards;

	public Player(String playerName, int initalArmiesAssigned) {
		this.playerName = playerName;
		this.armyCountAvailable = initalArmiesAssigned;
		this.territoryOccupied = new ArrayList<>();
		playerCards = new ArrayList<>();
	}
	
	public void incrementArmy(int number) {
		armyCountAvailable += number;
	}
	
	public void decrementArmy(int number) {
		armyCountAvailable -= number;
	}
	
	public int getArmyCountAvailable() {
		return armyCountAvailable;
	}

	public void setArmyCountAvailable(int armyCountAvailable) {
		this.armyCountAvailable = armyCountAvailable;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public List<Country> getTerritoryOccupied() {
		return territoryOccupied;
	}

	public void setTerritoryOccupied(List<Country> territoryOccupied) {
		this.territoryOccupied = territoryOccupied;
	}
	
	/**
	 * addTerritory adds the country to the player which no other player had yet
	 * @param player, player object {@link Player}
	 * @param country, country occupied by player {@link Country}
	 */
	public void addTerritory(Player player, Country country) {
		player.getTerritoryOccupied().add(country);
	}
		
	public void addCardToPlayer(Card card) {
		this.playerCards.add(card);
	}

	public List<Card> getPlayerCards() {
		return playerCards;
	}

	public void resetCards() {
		this.playerCards = new ArrayList<>();
	}

	/**
	 * reinforceArmyToCountry method places the armies which are not placed on the country.
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 * @param country, {@link Country} to which the armies to be placed.
	 */
	public void reinforceArmyToCountry(Country country, RiskBoardView riskBoardView) {
		Object [] possibilities = new Object [this.getArmyCountAvailable()];
		for(int index = 0; index < this.getArmyCountAvailable(); index++) {
			possibilities[index] = index+1;
		}
		Integer selectedValue = (Integer)JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
				JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
		if(Objects.nonNull(selectedValue)) {
			country.incrementArmiesOnCountry(selectedValue);
			this.decrementArmy(selectedValue);
			riskBoardView.getArmiesCountAvailableLabel().setText(String.valueOf(this.getArmyCountAvailable()));
		}
	}

	/**
	 * attackBetweenCountries method is used to do the attack between the countries.
	 * @param opponentPlayer, player on whom the user is attacking
	 * @param currentPlayerCountry, country which belongs to the current player
	 * @param opponentPlayerCountry, country belongs to the opponent
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 * @return true, if current player conquered the territory of opponent player
	 *         false, if current player defeted by the opponent player defence.
	 */
	public boolean attackBetweenCountries(Country currentPlayerCountry, Country opponentPlayerCountry,
			RiskBoardView riskBoardView, Player opponentPlayer) {
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
			currentPlayerDicesToRoll = getNumberOfDicesPlayerWantsToThrow(currentPlayerAttackingArmies, riskBoardView, 
					currentPlayerAttackingArmies > 3? 3 : currentPlayerAttackingArmies, true);
			opponentPlayerDicesToRoll = getNumberOfDicesPlayerWantsToThrow(opponentDefendingArmies, riskBoardView, 
					opponentDefendingArmies > 2 ? 2 : opponentDefendingArmies, false);

		}else if((Integer)currentPlayerOption > 0) {
			currentPlayerAttackingArmies = (Integer)currentPlayerOption;
			currentPlayerDicesToRoll = getNumberOfDicesPlayerWantsToThrow(currentPlayerAttackingArmies, riskBoardView, -1, true);
			opponentPlayerDicesToRoll = getNumberOfDicesPlayerWantsToThrow(opponentDefendingArmies, riskBoardView, -1, false);
		}
		if(currentPlayerDicesToRoll != 0 && opponentPlayerDicesToRoll != 0) {
			currentPlayerCountry.decreaseArmiesOnCountry(currentPlayerAttackingArmies);
			opponentPlayerCountry.decreaseArmiesOnCountry(opponentDefendingArmies);
			do {
				try {
					currentPlayerDicesToRoll = getNumberOfDicesPlayerWantsToThrow(currentPlayerAttackingArmies, riskBoardView, currentPlayerDicesToRoll, true);
					opponentPlayerDicesToRoll = getNumberOfDicesPlayerWantsToThrow(opponentDefendingArmies, riskBoardView, opponentPlayerDicesToRoll, false);
					Integer[] currentPlayerDice = new Dice().diceRoll(currentPlayerDicesToRoll);
					System.out.print("Current Player Dices: ");
					printDicesValues(currentPlayerDice);
					Integer[] opponentPlayerDice = new Dice().diceRoll(opponentPlayerDicesToRoll);
					System.out.print("Opponent Player Dices: ");
					printDicesValues(opponentPlayerDice);
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
				} catch (NoSuchAlgorithmException e) {
					System.out.println(e.getMessage());
				}
			} while (currentPlayerAttackingArmies != 0 && opponentDefendingArmies != 0);
			if(currentPlayerAttackingArmies > 0) {
				Integer armiesOnCountry;
				JOptionPane.showMessageDialog(riskBoardView.getBoardFrame(), this.getPlayerName()+" WON ");
				do {
					possibilities = new Object [currentPlayerAttackingArmies];
					for(int index = 0; index < currentPlayerAttackingArmies; index++) {
						possibilities[index] = index+1;
					}
					armiesOnCountry = (Integer) JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"How many armies you want to place on conquered terriroty", "Armies To Attack",
							JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
					if(Objects.nonNull(armiesOnCountry)) {
						opponentPlayer.getTerritoryOccupied().remove(opponentPlayerCountry);
						this.getTerritoryOccupied().add(opponentPlayerCountry);
						opponentPlayerCountry.setPlayerName(this.getPlayerName());
						opponentPlayerCountry.setArmiesOnCountry(armiesOnCountry);
						currentPlayerCountry.incrementArmiesOnCountry(currentPlayerAttackingArmies - armiesOnCountry);
						isOcuppiedTerritory = true;
						isOpponentPlayerOutOfGame(opponentPlayer);
					}
				}while(Objects.isNull(armiesOnCountry));
			}else if(currentPlayerAttackingArmies == 0 ){
				opponentPlayerCountry.setArmiesOnCountry(opponentDefendingArmies);
				JOptionPane.showMessageDialog(riskBoardView.getBoardFrame(), opponentPlayerCountry.getPlayerName()+" WON ");
			}
		}else {
			JOptionPane.showMessageDialog(riskBoardView.getBoardFrame(), "You don't have enough armies to attack");
		}
		return isOcuppiedTerritory;
	}
	
	/**
	 * isOpponentPlayerOutOfGame method checks whether the opponent player is done with the game if so
	 * all opponent player cards will be given to attacked player.
	 * @param opponentPlayer, {@link Player} object of the opponent player.
	 */
	private void isOpponentPlayerOutOfGame(Player opponentPlayer) {
		if(!opponentPlayer.getTerritoryOccupied().isEmpty()) {
			opponentPlayer.getPlayerCards().addAll(opponentPlayer.getPlayerCards());
			opponentPlayer.resetCards();
		}
	}
	
	/**
	 * printDicesValues method prints the dice rolled result on the console.
	 * @param currentPlayerDice, array of the dices values
	 */
	private void printDicesValues(Integer[] currentPlayerDice) {
		int  i = 0;
		while(i < currentPlayerDice.length) {
			System.out.print(currentPlayerDice[i++]+" ");
		}
		System.out.println();
	}
	
	/**
	 * getNumberOfDicesPlayerWantsToThrow method is used to get the number of armies an player wants to use in the attack phase
	 * @param isAttacker, whether dices is throwning by attacker or defender, if true attacker else defender
	 * @param currentPlayerDicesToRoll, -1 if the player hasnot selected yet, else some value what the player selected
	 * @param riskBoardView,instance of {@link RiskBoardView} object
	 * @param playerArmies, number armies available to attack or defend on the country.
	 * @return numberOfDices, number of dices to be used to throw.
	 */
	public int getNumberOfDicesPlayerWantsToThrow(Integer playerArmies, RiskBoardView riskBoardView, int currentPlayerDicesToRoll, boolean isAttacker) {
		Integer dicesToThrow = 0;
		int maxDicesToThrow = isAttacker ? 3:2;
		if(currentPlayerDicesToRoll == -1) {
			Object [] possibilities = new Object [playerArmies > maxDicesToThrow ? maxDicesToThrow: playerArmies];
			for(int index = 0; index < possibilities.length; index++) {
				possibilities[index] = index+1;
			}
			if(possibilities.length >= 1) {
				dicesToThrow = (Integer)JOptionPane.showInputDialog(riskBoardView.getBoardFrame(),"How many dices you want to thow by "+ (isAttacker ? "Attacker":"Defender"),
						"Dices To Throw",JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);
			}
		}else {
			dicesToThrow = playerArmies > currentPlayerDicesToRoll ? currentPlayerDicesToRoll : playerArmies; 
		}
		return Objects.nonNull(dicesToThrow) ? dicesToThrow : 0;
	}

	/**
	 * moveArmiesBetweenInCountries method is used to move armies between neighboring countries
	 * @param country, from which country armies need to move.
	 * @param adjacentCountry, to which country armies need to move.
	 * @param riskBoardview, RiskBoardView object used to update the components of the screen
	 */
	public void moveArmiesBetweenCountries(Country country, Country adjacentCountry, RiskBoardView riskBoardview) {
		if(country.getArmiesOnCountry() == 0){
			JOptionPane.showMessageDialog(riskBoardview.getBoardFrame(), "No armies on selected country to move");
		}else if(!isCountriesOwnedByPlayers(country, adjacentCountry)) {
			JOptionPane.showMessageDialog(riskBoardview.getBoardFrame(), "Selected Adjacent Country is owned by another player");
		}else {
			Object [] possibilities = new Object [country.getArmiesOnCountry() - 1];
			for(int index = 0; index < possibilities.length; index++) {
				possibilities[index] = index+1;
			}
			Integer selectedValue = (Integer)JOptionPane.showInputDialog(riskBoardview.getBoardFrame(),"Please enter armies to be added", "Armies To Add",
					JOptionPane.INFORMATION_MESSAGE, null,possibilities, possibilities[0]);

			if(Objects.nonNull(selectedValue)) {
				country.decreaseArmiesOnCountry(selectedValue);
				adjacentCountry.incrementArmiesOnCountry(selectedValue);
				riskBoardview.getArmiesCountAvailableLabel().setText(String.valueOf(this.getArmyCountAvailable()));
			}
		}
	}
	
	/**
	 * isCountriesOwnedByPlayers method is used to check whether the two countries are owned by the same player or not.
	 * @param country, one of the country from the {@link RiskBoardView#getCountryComboBox()}
	 * @param adjacentCountry, one of the country from the {@link RiskBoardView#getAdjacentCountryComboBox()}
	 * @return true, if the both country and adjacent countries belong to current player
	 * 		   false, if the both country and adjacent countries doesn't belong to current player  
	 */
	public boolean isCountriesOwnedByPlayers(Country country, Country adjacentCountry) {
		return country.getPlayerName().trim().equalsIgnoreCase(adjacentCountry.getPlayerName().trim());
	}
}

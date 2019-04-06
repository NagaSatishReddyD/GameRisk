package com.soen6441.risk;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import com.soen6441.risk.playerstrategy.PlayerBehaviourStrategyInterface;
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
	PlayerBehaviourStrategyInterface playerStrategy;
	String playerStrategyName;

	public Player(String playerName, int initalArmiesAssigned) {
		this.playerName = playerName;
		this.armyCountAvailable = initalArmiesAssigned;
		this.territoryOccupied = new ArrayList<>();
		playerCards = new ArrayList<>();
	}

	public String getPlayerStrategyName() {
		return playerStrategyName;
	}

	public void setPlayerStrategyName(String playerStrategyName) {
		this.playerStrategyName = playerStrategyName;
	}


	public void setPlayerStrategy(PlayerBehaviourStrategyInterface playerStrategy) {
		this.playerStrategy = playerStrategy;
	}

	public PlayerBehaviourStrategyInterface getPlayerStrategy() {
		return this.playerStrategy;
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
	public void addTerritory(Country country) {
		this.getTerritoryOccupied().add(country);
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
	 * @param isInitialPhase 
	 * @param riskBoardView, RiskBoardView object used to update the components of the screen
	 * @param country, {@link Country} to which the armies to be placed.
	 */
	public void reinforceArmyToCountry(Country country, RiskBoardView riskBoardView, boolean isInitialPhase) {
		Integer selectedValue = this.playerStrategy.reinforceArmyToCountry(country, riskBoardView, isInitialPhase, this);
		if(Objects.nonNull(selectedValue)) {
			System.out.println(this.playerName+" "+this.playerStrategyName+" places "+ selectedValue+" on "+country.getCountryName());
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
		return this.playerStrategy.attackBetweenCountries(currentPlayerCountry, opponentPlayerCountry, riskBoardView, opponentPlayer, this);
	}

	/**
	 * isOpponentPlayerOutOfGame method checks whether the opponent player is done with the game if so
	 * all opponent player cards will be given to attacked player.
	 * @param opponentPlayer2 
	 * @param opponentPlayer, {@link Player} object of the opponent player.
	 */
	public void isOpponentPlayerOutOfGame(Player currentPlayer, Player opponentPlayer) {
		if(opponentPlayer.getTerritoryOccupied().isEmpty()) {
			currentPlayer.getPlayerCards().addAll(opponentPlayer.getPlayerCards());
			opponentPlayer.resetCards();
		}
	}

	/**
	 * printDicesValues method prints the dice rolled result on the console.
	 * @param currentPlayerDice, array of the dices values
	 */
	public void printDicesValues(Integer[] currentPlayerDice) {
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
		this.playerStrategy.foriticateArmies(country, adjacentCountry, riskBoardview, this);
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

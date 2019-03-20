package com.soen6441.risk;

import java.util.ArrayList;
import java.util.List;

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
}

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
	List<Country> territoryOccupied = new ArrayList<>();
	
	public Player(String playerName) {
		this.playerName = playerName;
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
	 * @param player
	 * @param string
	 */
	public void addTerritory(Player player, Country country) {
		player.getTerritoryOccupied().add(country);
	}
		
}

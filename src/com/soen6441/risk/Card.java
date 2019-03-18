package com.soen6441.risk;

/**
 * Card class used to create Card objects
 * @author Naga Satish Reddy
 */
public class Card {
	private String territoryName;
	private String armyType;
	
	public Card(String territoryName, String armyType) {
		super();
		this.territoryName = territoryName;
		this.armyType = armyType;
	}
	public String getTerritoryName() {
		return territoryName;
	}

	public String getArmyType() {
		return armyType;
	}
}

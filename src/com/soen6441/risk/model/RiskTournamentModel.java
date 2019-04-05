package com.soen6441.risk.model;

import com.soen6441.risk.view.RiskTournamentView;


/**
 * RiskTournamentModel class contains the logic when a user click the Run Tournament button in RiskTournamentView
 * @author An Nguyen
 *
 */
public class RiskTournamentModel {
	
	/**
	 * This method is used to run the tournament mode after the user choose all of the inputs
	 * @param view, instance of {@link RiskTournamentView} class
	 */
	public void runTournament(RiskTournamentView view) {
		
	}
	
	/**
	 * This method is used to enable/disable necessary label and Combobox of players when 
	 * a specific number of player is selected
	 * @param view, instance of {@link RiskTournamentView} class
	 */
	public void getComboBasedOnPlayerCount(RiskTournamentView view) {
		Integer count = Integer.parseInt(view.getPlayerCountCombo().getSelectedItem().toString());
		switch(count){
			case 2:
				view.getPlayer3Label().setVisible(false);
				view.getPlayer3StrategyCombo().setVisible(false);
				view.getPlayer4Label().setVisible(false);
				view.getPlayer4StrategyCombo().setVisible(false);
				break;
			case 3:
				view.getPlayer3Label().setVisible(true);
				view.getPlayer3StrategyCombo().setVisible(true);
				view.getPlayer4Label().setVisible(false);
				view.getPlayer4StrategyCombo().setVisible(false);
				break;
			case 4:
				view.getPlayer3Label().setVisible(true);
				view.getPlayer3StrategyCombo().setVisible(true);
				view.getPlayer4Label().setVisible(true);
				view.getPlayer4StrategyCombo().setVisible(true);
		}
	}
}

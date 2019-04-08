package com.soen6441.risk.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComboBox;

import com.soen6441.risk.RiskGameConstants;
import com.soen6441.risk.controller.RiskBoardController;
import com.soen6441.risk.view.RiskBoardView;
import com.soen6441.risk.view.RiskTournamentView;


/**
 * RiskTournamentModel class contains the logic when a user click the Run Tournament button in RiskTournamentView
 * @author An Nguyen
 * @author Naga Satish Reddy
 */
public class RiskTournamentModel {

	/**
	 * This method is used to run the tournament mode after the user choose all of the inputs
	 * @param riskTournamentView, instance of {@link RiskTournamentView} class
	 */
	public void runTournament(RiskTournamentView riskTournamentView) {
		int mapsCount = Integer.parseInt(riskTournamentView.getMapCountCombo().getSelectedItem().toString());
		int numberOfGames = Integer.parseInt(riskTournamentView.getGameCountCombo().getSelectedItem().toString());
		int numberOfTurns = Integer.parseInt(riskTournamentView.getTurnCountCombo().getSelectedItem().toString());
		int playersCount = Integer.parseInt(riskTournamentView.getPlayerCountCombo().getSelectedItem().toString());
		String[] behaviours = getPlayerBehaviour(riskTournamentView);
		String[] mapFiles = getMapsNames(mapsCount);
		Map<String, List<String>> tournamentResult= new HashMap<>();
		for(int mapCountIndex = 0; mapCountIndex < mapsCount; mapCountIndex++) {
			for(int gamesIndex = 0; gamesIndex < numberOfGames; gamesIndex++) {
				RiskBoardView riskBoardView = new RiskBoardView();
				RiskBoardModel riskBoardModel = new RiskBoardModel();
				riskBoardModel.setNumberOfTurn(numberOfTurns);
				riskBoardModel.setGameMode(RiskGameConstants.TOURNAMENT_MODE);
				RiskBoardController riskBoardController = new RiskBoardController(riskBoardModel, riskBoardView);
				riskBoardController.intializeBoardGame(playersCount, mapFiles[mapCountIndex], behaviours);
				getResult(riskBoardModel.getPlayersWinningResult(), mapFiles[mapCountIndex], tournamentResult);
			}
		}
		
		System.out.println("Tournament Result");
		for (Entry<String, List<String>> resultMap : tournamentResult.entrySet()) {
			String resultString = "";
			List<String> resultList = resultMap.getValue();
			for(String result : resultList) {
				resultString+= result+"  ";
			}
			System.out.println(resultMap.getKey()+" "+resultString);
		}
	}

	/**
	 * @param playersWinningResult
	 * @param mapFiles 
	 * @param tournamentResult 
	 * @return
	 */
	private void getResult(List<String> playersWinningResult, String mapFiles, Map<String, List<String>> tournamentResult) {
		String result;
		if(playersWinningResult.size() == 1) {
			result = playersWinningResult.get(0);
		}else {
			result = "Draw";
		}

		if(tournamentResult.containsKey(mapFiles)) {
			tournamentResult.get(mapFiles).add(result);
		}else {
			List<String> resultList = new ArrayList<>();
			resultList.add(result);
			tournamentResult.put(mapFiles, resultList);
		}
	}

	/**
	 * @param mapsCount
	 * @return 
	 */
	private String[] getMapsNames(int mapsCount) {
		String[] filesNamesList = new String[mapsCount];
		File resourceFolder = new File(System.getProperty("user.dir")+RiskGameConstants.RESOURCES_FOLDER);
		File[] filesList = resourceFolder.listFiles();
		int fileCount = 0;
		int fileIndex = 0;
		while(fileCount != mapsCount) {
			if(filesList[fileIndex].getAbsoluteFile().getName().contains(RiskGameConstants.MAP_FILE_EXTENSION)) {
				String fileName = filesList[fileIndex].getAbsoluteFile().getName();
				filesNamesList[fileCount++] = fileName.substring(0, fileName.lastIndexOf("."));
			}
			fileIndex++;
		}
		return filesNamesList;
	}

	/**
	 * @param riskTournamentView
	 * @return 
	 * @return
	 */
	private String[] getPlayerBehaviour(RiskTournamentView riskTournamentView) {
		String[] behaviors = new String[4];
		behaviors[0] = getPlayerStrategy(riskTournamentView.getPlayer1StrategyCombo());
		behaviors[1] = getPlayerStrategy(riskTournamentView.getPlayer2StrategyCombo());
		behaviors[2] = getPlayerStrategy(riskTournamentView.getPlayer3StrategyCombo());
		behaviors[3] = getPlayerStrategy(riskTournamentView.getPlayer4StrategyCombo());
		return behaviors;
	}

	/**
	 * @param player1StrategyCombo
	 * @return
	 */
	private String getPlayerStrategy(JComboBox<String> playerStrategyCombo) {
		if(playerStrategyCombo.isVisible())
			return playerStrategyCombo.getSelectedItem().toString();
		return null;
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

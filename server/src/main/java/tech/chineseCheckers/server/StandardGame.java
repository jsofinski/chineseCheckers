package tech.chineseCheckers.server;

//import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Iterator;

// import org.graalvm.compiler.core.common.Fields;
/***
 * Implementation of standard Chineese Checkers game. Interprets
 * player moves and check if any player won or the game ended.
 * @author Jakub
 *
 */
public class StandardGame implements Game {

	int[] rowSizes;
	GameRules rules;
	
	int[] fields;
	int[] playersFieldsIDs;
	String[] players;
	int noPlayers;
	int[] winners;
	int noWinners;
	
	private void initGame() {
		fields = new int[121];
		players = new String[6];
		winners = new int[6];
		playersFieldsIDs = new int[] {0,1,2,3,4,5,6,7,8,9, // Player 0
									111,112,113,114,115,116,117,118,119,120, // 1
									19,20,21,22,32,33,34,44,45,55, // 2
									65,75,76,86,87,88,98,99,100,101, // 3
									74,84,85,95,96,97,107,108,109,110, // 4
									10,11,12,13,23,24,25,35,36,46}; // 5
		Arrays.fill(fields, -1);
		Arrays.fill(players,"");
		Arrays.fill(winners,0);
		noPlayers = 0;
		noWinners = 0;
	}

	
	public StandardGame(GameRules rules) {
		initGame();
		this.rules = rules;
		
	}
	@Override
	public boolean addPlayer(String name) {
		if(noPlayers>6)
			return false;
		players[noPlayers] = name;
		int idx = noPlayers*10;
		
		for(int i = 0; i < 10; i++) {
			fields[playersFieldsIDs[idx+i]] = noPlayers;
		}
		noPlayers++;
		return true;
		
	}
	
	@Override
	public boolean interpretMove(String name, String str) {
		try {
			String split[] = str.split("\\s");
			int sourceID = Integer.parseInt(split[0]);
			int targetID = Integer.parseInt(split[1]);
			
			// Get player ID 
			int pID = -1;
			for(int i = 0; i < 6; i++) {
				if(players[i] == (name)) {
					pID = i;
					break;
				}
			}
			
			// Check if players move his pawn
			if(fields[sourceID] != pID)
				return false;
						
			// Check if pawns are in goal zone
			int eID = pID + (pID % 2 == 0 ? 1 : -1);
			int eIDX = eID * 10;
			boolean sourceInGoalZone = false;
			boolean targetInGoalZone = false;
			for(int i = 0 ; i < 10; i++) {
				if(playersFieldsIDs[eIDX + i] == sourceID)
					sourceInGoalZone = true;
				if(playersFieldsIDs[eIDX + i] == targetID)
					targetInGoalZone = true;		
			}
			
			// Pawns can swap in goal zone if rules allow that
			if(rules.pawnsSwappable && targetInGoalZone) {
				int t = fields[targetID];
				fields[targetID] = fields[sourceID];
				fields[sourceID] = t;
				return true;
			}
			// Check if players is trying to move pawn into another pawn not in goal zone
			else if(fields[targetID] != -1) {
				return false;
			}
			// Check if player is trying to leave the goal zone
			else if((!rules.canLeaveGoalZone)&&(sourceInGoalZone)&&(!targetInGoalZone)) {
				return false;
			}
			// Move pawn into free field
			else {
				fields[targetID] = fields[sourceID];
				fields[sourceID] = -1;
			}
			
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	@Override
	public boolean ended() {
		if(rules.winsBeforeGameEnds == noWinners)
			return true;
		else if(noWinners == noPlayers)
			return true;
		
		return false;
	}



	@Override
	public String winner() {
		int oIDX;
		int oID;
		int inTarget;
		int free;
		for(int i = 0; i < 6; i++) {
			if(winners[i] != 0)
				continue;
			
			oID = i + (i%2 == 0 ? 1 : -1);
			oIDX = 10*oID;
			free = 10;
			inTarget = 0;
			
			for(int j = 0; j < 10; j++) {
				if(fields[playersFieldsIDs[oIDX + j]] == i) {
					inTarget++;
					free--;
				}
				else if(fields[playersFieldsIDs[oIDX + j]] != -1) {
					free--;
				}
				
			}
			
			if((inTarget == 10) || (rules.opponentsPawnsCounts && free == 0 && inTarget > 0)) {
				winners[i] = 1;
				noWinners++;
				return players[i];
			}
			
		}
		
		return  "";
	}
}

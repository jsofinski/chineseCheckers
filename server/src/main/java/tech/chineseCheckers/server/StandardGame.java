package tech.chineseCheckers.server;

import java.util.ArrayList;
import java.util.Iterator;

public class StandardGame implements Game {

	int[] rowSizes;
	GameRules rules;
	
	ArrayList<Integer[]> fields;
	
	private void initGame() {
		rowSizes = new int[17];
		rowSizes[0] = 1;
		rowSizes[1] = 2;
		rowSizes[2] = 3;
		rowSizes[3] = 4;
		rowSizes[4] = 13;
		rowSizes[5] = 12;
		rowSizes[6] = 11;
		rowSizes[7] = 10;
		rowSizes[8] = 9;
		rowSizes[9] = 10;
		rowSizes[10] = 11;
		rowSizes[11] = 12;
		rowSizes[12] = 13;
		rowSizes[13] = 4;
		rowSizes[14] = 3;
		rowSizes[15] = 2;
		rowSizes[16] = 1;
		
		for(int  i = 0 ; i < 17; i++) {
			fields.add(new Integer[rowSizes[i]]);
		}
		
	}
	
	public StandardGame(GameRules rules) {
		initGame();
		
		
	}

	@Override
	public boolean interpretMove(String str) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean ended() {
		// TODO Auto-generated method stub
		return false;
	}
}

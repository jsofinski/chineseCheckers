package tech.chineseCheckers.client;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Represents Chinese checkers player.
 * Holds data required to set board and importantly, has player pawns.
 */
public class Player {
	
	ArrayList<Pawn> myPawns;
	Color color;
	String nick;
	int[][] playerPawnsPositions;
	
	/**
	 * Every player must have pawns so value is assigned in constructor
	 */
	public Player() {
		this.myPawns = new ArrayList<Pawn>();
	}

	public Color getColor() {return color;}
	public void setColor(Color color) {this.color = color;}
	public String getNick() {return nick;}
	public void setNick(String nick) {this.nick = nick;}

	public ArrayList<Pawn> getMyPawns() {return myPawns;}
	public void addPawn(Pawn pawn) {this.myPawns.add(pawn);}

	public int[][] getPlayerPawnsPositions() {return this.playerPawnsPositions;}
	public void setPlayerPawnsPositions(int[][] playerPawnsPositions) {this.playerPawnsPositions = playerPawnsPositions;}
}

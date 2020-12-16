package tech.chineseCheckers.server;

public class Game {
	
	private GameRules rules;
	
	public Game(GameRules rules) {
		this.rules = rules;
	}
	
	public synchronized boolean interpretMove(String str) {
		// TODO
		return true;
	}
	public synchronized boolean ended() {
		// TODO
		return false;
	}
}

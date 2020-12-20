package tech.chineseCheckers.client;

public class Game {
	public Game() {
		GameConfig gameConfig = new GameConfig();
		
		Board board = new Board(40, gameConfig);
		
		GameFrame gameFrame = new GameFrame(board);
	}
}

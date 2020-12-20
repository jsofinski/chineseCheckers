package tech.chineseCheckers.client;

public class Game {
	
	CommunicationService server;
	
	public Game(CommunicationService server, String name) {
		GameConfig gameConfig = new GameConfig();
		
		Board board = new Board(40, gameConfig);
		
		GameFrame gameFrame = new GameFrame(board);
		
		this.server = server;
	}
	
	public void start() {
		
	}
}

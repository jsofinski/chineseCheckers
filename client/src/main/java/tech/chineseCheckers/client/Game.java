package tech.chineseCheckers.client;

public class Game {
	
	CommunicationService server;
	GameConfig gameConfig;
	Board board;
	GameFrame gameFrame;
	
	public Game(CommunicationService server, String name) {
		this.server = server;

		gameConfig = new GameConfig();
		
		board = new Board(40, gameConfig, server);
		
		gameFrame = new GameFrame(board);
		
	}
	
	public void start() {
		while (true) {
			String input = this.server.getMessage();
			String[] res = input.split("\\s");
			switch (res[0]) {
				case "MOVE": {
					this.board.moveByIds(res[1], res[2]);
				} break;
			}
		}
	}
}

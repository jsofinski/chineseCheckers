package tech.chineseCheckers.client;
/**
 * 
 */
public class Game {
	
	CommunicationService server;
	GameConfig gameConfig;
	Board board;
	GameFrame gameFrame;
	String name;
	public Game(CommunicationService server, String name) {
		this.server = server;
		gameConfig = new GameConfig();
		this.name = name;
	}
	/**
	 * 
	 */
	public void start() {
		while (true) {
			String input = this.server.getMessage();
			String[] res = input.split("\\s");
			switch (res[0]) {
				case "GAME_START": {
					this.board = new Board(40, this.gameConfig, this.server, this.name);
					gameFrame = new GameFrame(board, name);
					break;
				}
				case "NUMBER_OF_PLAYERS": {
					this.gameConfig.setPlayers(Integer.parseInt(res[1]));
					break;
				} 
				case "MOVE": {
					this.board.moveByIds(res[1], res[2]);
					break;
				}
				case "MOVE_NOW": {
					if(res[1].equals(this.name))
						board.setMyTurn(true);
					else
						board.setMyTurn(false);
					break;
				}
				case "PLAYER": {
					switch (res[1]) {
						case ("1"):
							this.gameConfig.players.get(0).setNick(res[2]);
							break;
						case ("2"):
							this.gameConfig.players.get(1).setNick(res[2]);
							break;
						case ("3"):
							this.gameConfig.players.get(2).setNick(res[2]);
							break;
						case ("4"):
							this.gameConfig.players.get(3).setNick(res[2]);
							break;
						case ("5"):
							this.gameConfig.players.get(4).setNick(res[2]);
							break;
						case ("6"):
							this.gameConfig.players.get(5).setNick(res[2]);
							break;
					}
				} break;
				case "ERROR": {
					return;
				}
			}
		}
	}
}

package tech.chineseCheckers.client;

/**
 * Most of the server's responds are handled here in game cycle.
 * Holds important game components like @board, @gameFrame and @gameConfig.
 */
public class Game {
	// server allows player to received and send informations
	CommunicationService server;
	// gameConfig has players pawns starting position and shape of the board
	GameConfig gameConfig;
	// representation of game board
	Board board;
	GameFrame gameFrame;
	String name;
	public Game(CommunicationService server, String name) {
		this.server = server;
		gameConfig = new GameConfig();
		this.name = name;
	}
	/**
	 * Game life cycle
	 */
	public void start() {
		boolean gameEnded = false;
		while (!gameEnded) {
			String input = this.server.getMessage();
			String[] res = input.split("\\s");
			switch (res[0]) {
				// Starts the game, sets new Board and puts it to the new frame.
				case "GAME_START": {
					this.board = new Board(40, this.gameConfig, this.server, this.name);
					gameFrame = new GameFrame(board, name);
					break;
				}
				// ends game, players are not allowed to move anymore
				case "GAME_END": {
					gameEnded = true;
					this.board.endGame();
					break;
				}
				// sets Winner
				case "WINNER": {
					this.board.setWinner(res[1]);
					System.out.println("Winner: " + res[1]);
					break;
				}
				// Sets number of players to the configuration, it will change all of the players pawns positions etc.
				case "NUMBER_OF_PLAYERS": {
					this.gameConfig.setPlayers(Integer.parseInt(res[1]));
					break;
				} 
				// moves pawns for res[1] field to res[2] field
				case "MOVE": {
					this.board.moveByIds(res[1], res[2]);
					break;
				}
				// sets the player's turn
				case "MOVE_NOW": {
					if(res[1].equals(this.name))
						this.board.setMyTurn(true);
					else
						this.board.setMyTurn(false);
					break;
				}
				// sets each player nick, res[1] is player position and res[2] his nick
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
				// if error occurs, turn off client
				case "ERROR": {
					return;
				}
			}
		}
	}
}

package tech.chineseCheckers.server;

/***
 * Holds server configuration. Currently it holds only port and number of players.
 * @author Jakub
 *
 */
public class Config {

	public int NOPlayers;
	public int port;
	
	public Config() {
		NOPlayers = 2;
		port = 55000;
	}
}

package tech.chineseCheckers.server;

public interface Game {

	public boolean interpretMove(String name, String str);
	public boolean ended();
	public boolean addPlayer(String name);
	public String winner();
}

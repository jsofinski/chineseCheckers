package tech.chineseCheckers.server;

public interface Game {

	public boolean interpretMove(String str);
	public boolean ended();
}

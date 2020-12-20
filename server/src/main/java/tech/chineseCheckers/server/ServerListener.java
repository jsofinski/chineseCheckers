package tech.chineseCheckers.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerListener {
	
	ServerSocket listener;
	SharedData data;
	boolean good;
	
	public ServerListener(int port, SharedData data) {
		try {
			listener = new ServerSocket(port);
			this.data = data;
			good = true;
			
		} catch (IOException e) {
			good = false;
		}
		
	}
	
	public boolean isGood() {
		return good;
	}
	
	public PlayerHandler getPlayer() {
		try {
			PlayerSocket playerSocket = new PlayerSocket(listener.accept());
			PlayerHandler playerHandler = new PlayerHandler(playerSocket, data);
			
			return playerHandler;
			
		} catch (IOException e) {
			return null;
		}
		
	}
}

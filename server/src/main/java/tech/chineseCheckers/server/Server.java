package tech.chineseCheckers.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {

	private int playerNumber;
	private GameRules gameRules;
	
	private static SharedData data;
	
	public Server() {
		data = SharedData.getInstance();
	}
	
	public static void broadcast(String message) {
		Set<PlayerSocket> players = data.getPlayerSockets();
		synchronized (players) {
			for(PlayerSocket player : players) {
				player.send(message);
			}
		}
	}
	
	
	private void getNewConfig() {
		playerNumber = UserInterface.getInt("Podaj liczbe graczy.");
		gameRules = new GameRules();
	}
	
	private void sendColorInfo() {
		Set<String> colors = new HashSet<String>();
		colors.add("WHITE");
		colors.add("RED");
		colors.add("BLUE");
		colors.add("BLACK");
		colors.add("GREEN");
		colors.add("YELLOW");
		Iterator<String> iter = data.getNames().iterator();
		Iterator<String> col = colors.iterator();
		while(iter.hasNext()) {
			broadcast("COLOR_SET " + iter.next() + " " + col.next());
		}
		
	}
	
	public void start() {
		UserInterface.print("Starting server");
		while(true) {
			UserInterface.print("Starting game");
			
			getNewConfig();
			
			// Wait for players
			UserInterface.print("Waiting for players");
			
			int currentPlayerNumber = 0;
			
			Executor pool = Executors.newFixedThreadPool(6);
			
			try (ServerSocket listener = new ServerSocket(59001)) {
				while(currentPlayerNumber < playerNumber) {
					pool.execute(new PlayerHandler(new PlayerSocket(listener.accept()), data));
				}
			}
			catch(IOException e) {
				
			}
			
			// Give all players informations about colors
			sendColorInfo();
			
			// Game
			data.game = new Game(gameRules);
			broadcast("GAME_START");
			
			Iterator<String> playerNames = data.getNames().iterator();
			
			while(data.game.ended()) {
				if(!playerNames.hasNext())
					playerNames = data.getNames().iterator();
				
				broadcast("MOVE_NOW " + playerNames.next());
				try {
					pool.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			
		}
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}
}

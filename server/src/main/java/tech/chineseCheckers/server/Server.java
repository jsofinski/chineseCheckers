package tech.chineseCheckers.server;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {

	private GameRules gameRules;
	private Config serverConfig;
	private SharedData data;
	private ServerListener listener;
	
	public Server(ServerListener listener, Config config, SharedData data) {
		this.data = data;
		this.serverConfig = config;
		this.listener = listener;
		this.gameRules = new GameRules();
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
			data.broadcast("COLOR_SET " + iter.next() + " " + col.next());
		}
		
	}
	
	public void start() {
		UserInterface.print("Starting server");
		
		if(!listener.isGood()) {
			UserInterface.print("Unable to create listener");
			return;
		}
		
		while(true) {
			UserInterface.print("Starting game");

			// Wait for players
			UserInterface.print("Waiting for players");
			
			int currentPlayerNumber = 0;
			
			Executor pool = Executors.newFixedThreadPool(6);
			
			while(currentPlayerNumber < serverConfig.NOPlayers) {
				PlayerHandler ph = listener.getPlayer();
				if(ph != null) {
					currentPlayerNumber++;
					pool.execute(listener.getPlayer());
				}
			}
	
			
			// Give all players informations about colors
			sendColorInfo();
			
			// Game
			data.game = new StandardGame(gameRules);
			data.broadcast("GAME_START");
			
			Iterator<String> playerNames = data.getNames().iterator();
			
			while(data.game.ended()) {
				if(!playerNames.hasNext())
					playerNames = data.getNames().iterator();
				
				data.broadcast("MOVE_NOW " + playerNames.next());
				try {
					pool.wait();
				} catch (InterruptedException e) {
					
				}
				
			}
			
			
		}
	}
	
	public static void main(String[] args) {
		Config config = new Config();
		config.port = 55000;
		config.NOPlayers = 2;
		ServerListener listener = new ServerListener(config.port, SharedData.getInstance());
		Server s = new Server(listener, config, SharedData.getInstance());
		s.start();
	}
}

package tech.chineseCheckers.server;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server {

	private GameRules gameRules;
	private Config serverConfig;
	private SharedData data;
	private ServerListener listener;
	private Object lock;
	
	public Server(ServerListener listener, Config config, SharedData data) {
		this.data = data;
		this.serverConfig = config;
		this.listener = listener;
		this.gameRules = new GameRules();
		this.lock = new Object();
	}
	
	private void sendNickInfo() {
		Iterator<String> iter = data.getNames().iterator();
		int i = 1;
		while(iter.hasNext()) {
			data.broadcast("PLAYER " + Integer.toString(i) + " " + iter.next());
			i++;
		}
	}
	private void sendPlayersAmountInfo() {
		data.broadcast("NUMBER_OF_PLAYERS " + serverConfig.NOPlayers);
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
			UserInterface.print("Waiting for players to join");
			
			int currentPlayerNumber = 0;
			
			Executor pool = Executors.newFixedThreadPool(6);
			
			while(currentPlayerNumber < serverConfig.NOPlayers) {
				PlayerHandler ph = listener.getPlayer();
				if(ph != null) {
					currentPlayerNumber++;
					UserInterface.print("Player connected");
					ph.setLock(lock);
					pool.execute(ph);
				}
			}
	
			// Wait for all players to be ready
			
			UserInterface.print("Waiting for player to be ready.");
			
			while(data.ready() != serverConfig.NOPlayers)
				;
			
			UserInterface.print("Sending info");
			// Give all players informations about colors
			sendPlayersAmountInfo();
			sendColorInfo();
			sendNickInfo();
			
			// Game
			data.game = new StandardGame(gameRules);
			data.broadcast("GAME_START");
			
			Iterator<String> playerNames = data.getNames().iterator();
			
			while(!data.game.ended()) {
				if(!playerNames.hasNext())
					playerNames = data.getNames().iterator();
				
				data.broadcast("MOVE_NOW " + playerNames.next());
				synchronized(lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Config config = new Config();
		config.port = 55000;
		config.NOPlayers = Integer.parseInt(JOptionPane.showInputDialog("Podaj liczbe graczy","2"));
		ServerListener listener = new ServerListener(config.port, SharedData.getInstance());
		Server s = new Server(listener, config, SharedData.getInstance());
		s.start();
	}
}

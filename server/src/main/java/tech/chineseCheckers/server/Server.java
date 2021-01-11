package tech.chineseCheckers.server;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server {

	private GameRules gameRules;
	private Game game;
	private Config serverConfig;
	private SharedData data;
	private ServerListener listener;
	private Object lock;
	private Set<String> colors;
	
	public Server(ServerListener listener, Config config, SharedData data, GameRules rules, Game game) {
		this.data = data;
		this.serverConfig = config;
		this.listener = listener;
		this.gameRules = rules;
		this.game = game;
		this.lock = new Object();
		colors = new HashSet<String>();
		colors.add("WHITE");
		colors.add("RED");
		colors.add("BLUE");
		colors.add("BLACK");
		colors.add("GREEN");
		colors.add("YELLOW");
		
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
		Iterator<String> iter = data.getNames().iterator();
		Iterator<String> col = colors.iterator();
		int cID = 0;
		while(iter.hasNext()) {
			String name = iter.next();
			data.broadcast("COLOR_SET " + name + " " + col.next());	
			data.game.addPlayer(name);
		}
	}
	
	public void start() {
		UserInterface.print("Starting server");
		
		if(!listener.isGood()) {
			UserInterface.print("Unable to create listener");
			return;
		}
		
		data.game = game;
		
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
			data.broadcast("GAME_START");
			
			Iterator<String> playerNames = data.getNames().iterator();
			List<String> winners = new ArrayList<String>();
			
			while(!data.game.ended()) {
				if(!playerNames.hasNext())
					playerNames = data.getNames().iterator();
				String player = playerNames.next();
				if(winners.contains(player))
					continue;
				UserInterface.print("Move of " + player);
				data.broadcast("MOVE_NOW " + player);
				synchronized(lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
					}
				}
				String winner = data.game.winner();
				winners.add(winner);
				if(winner != "") {
					data.broadcast("WINNER " + winner);
					UserInterface.print("Winner: " + winner);
				}
			}
			
			UserInterface.print("Game ended.");
			data.broadcast("GAME_END");
		}
	}
	
	public static void main(String[] args) {
		Config config = new Config();
		config.port = 55000;
		config.NOPlayers = Integer.parseInt(JOptionPane.showInputDialog("Podaj liczbe graczy","2"));
		ServerListener listener = new ServerListener(config.port, SharedData.getInstance());
		Server s = new Server(listener, config, SharedData.getInstance(), new GameRules(), new StandardGame(new GameRules()));
		s.start();
	}
}

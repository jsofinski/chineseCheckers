package tech.chineseCheckers.server;

import java.util.HashSet;
import java.util.Set;

/*** 
 * Provides safe access to necessary data to all threads.
 * @author Jakub
 *
 */
public class SharedData {

	private static SharedData instance;
	private static Object mutex = new Object();
	
	private Set<String> names;
	private Set<PlayerSocket> players;
	
	private int ready;
	
	public Game game;
	
	private SharedData() {
		names = new HashSet<String>();
		players = new HashSet<PlayerSocket>();
	}
	public static SharedData getInstance() {
		SharedData result = instance;
		
		if(result == null) {
			synchronized(mutex) {
				result = instance;
				if(result == null)
					instance = result = new SharedData();
			}
		}
		
		return result;
	}
	
	
	public void broadcast(String message) {
		synchronized(mutex) {
			Set<PlayerSocket> players = getPlayerSockets();
			for(PlayerSocket player : players) {
				player.send(message);
			}
		}
	}
	
	public Game getGame() { synchronized(mutex) {return game;}}
	
	public void setReady() { synchronized(mutex) {ready++;} }
	public void setNotReady() { synchronized(mutex) {ready--;}}
	public int ready() {synchronized(mutex) {return ready;} }
	
	public Set<String> getNames() { 
		synchronized(mutex) { return names; } }
	public Set<PlayerSocket> getPlayerSockets() { 
		synchronized(mutex) { return players; } }
	public void addName(String name) { 
		synchronized(mutex) { names.add(name); }}
	public void addPlayerSocket(PlayerSocket player) { 
		synchronized(mutex) { players.add(player); }}
	public void deleteName(String name) { 
		synchronized(mutex) { names.remove(name); }}
	public void deletePlayerSocket(PlayerSocket player) { 
		synchronized(mutex) { players.remove(player); }}
	public void clearAll() { 
		synchronized(mutex) {names.clear(); players.clear();}}

}

package tech.chineseCheckers.server;

import java.util.Set;
/***
 * Given socket, will handle all communication with player.
 * Currently it's accepting player name and forward moves to 
 * Board class for further interpretation.
 * @author Jakub
 *
 */
public class PlayerHandler implements Runnable {
	private String name;
	PlayerSocket player;
	
	SharedData data;
	Object lock;
	
	public PlayerHandler(PlayerSocket player, SharedData data) {
		this.player = player;
		this.data = data;
	}

	private String reciveName() {
		String temp;
		while(true) {
			Set<String> names = data.getNames();
			player.send("NAME_GET");
			temp = player.recive();
			// Name recived: NAME_SET userName
			temp = temp.substring(9);
			if(temp == null) {
				continue;
			}
			if(!temp.isBlank() && !names.contains(temp)) {
				data.addName(temp);
				break;
			}
		}
		
		player.send("NAME_ACCEPTED");
		
		return temp;
	}
	
	public void setLock(Object lock){
		this.lock = lock;
	}
	
	public void run() {
		try {
			name = reciveName();

			data.addPlayerSocket(player);
			data.setReady();
			UserInterface.print(name + " joined.");
			boolean connected = true;
			while(connected) {
				String input = player.recive();
				String[] res = input.split("\\s");
				switch (res[0]) {
					case "DISCONNECT": {connected = false;} break;
					case "MOVE": {
						if(!data.game.interpretMove(name, input.substring(5)))
							player.send("MOVE_BAD");
						else {
							data.broadcast(input);
							synchronized(lock) {
								lock.notifyAll();
							}
						}
					} break;
				}
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			if (player != null) {
				player.send("DISCONNECTED");
				data.deletePlayerSocket(player);
			}
			if (name != null) {
				UserInterface.print(name + " left" );
				data.deleteName(name);
			}
			player.close();

		}
		
	}
}
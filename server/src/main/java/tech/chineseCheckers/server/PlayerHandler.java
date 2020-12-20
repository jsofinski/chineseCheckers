package tech.chineseCheckers.server;

import java.util.Set;

public class PlayerHandler implements Runnable {
	private String name;
	PlayerSocket player;
	
	SharedData data;
	
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
	
	public void run() {
		try {
			
			name = reciveName();

			data.addPlayerSocket(player);
			
			UserInterface.print(name + " joined.");
			boolean connected = true;
			while(connected) {
				String input = player.recive();
				String[] res = input.split("\\s");
				switch (res[0]) {
					case "DISCONNECT": {connected = false;} break;
					case "MOVE": {
						if(!data.game.interpretMove(input.substring(5)))
							player.send("MOVE_BAD");
						else {
							
							this.notify();
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
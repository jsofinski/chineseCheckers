package tech.chineseCheckers.client;

/**
 * Main class, represents client entity of Chinese Checkers player.
 */
public class Client {

	/**
	 * Main method connects to server, gets user name and starts the Game life cycle
	 */
	public static void main(String[] args) {
		StartMenu startMenu = new StartMenu();
		
		CommunicationService server = new CommunicationService();
		
		if(!server.connect("127.0.0.1", 55000)) {
			System.out.println("Unable to connect");
			return;
		}
		else {
			System.out.println("Connected");
		}
		
		String returnMessage;
		String name = "";
		do {
			returnMessage = server.getMessage();
			if(returnMessage.equals("NAME_GET")) {
				name = startMenu.getName();
				server.sentMessage("NAME_SET " + name);
			}
			
		} while(!returnMessage.equals("NAME_ACCEPTED"));
				
		Game game = new Game(server, name);
		game.start();

	}

}

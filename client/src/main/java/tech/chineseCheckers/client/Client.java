package tech.chineseCheckers.client;

public class Client {

	public static void main(String[] args) {
		System.out.println("Hi from client.");
		
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
			System.out.println("Recived: " + returnMessage);
			if(returnMessage.equals("NAME_GET")) {
				name = startMenu.getName();
				server.sentMessage("NAME_SET " + name);
			}
			
		} while(!returnMessage.equals("NAME_ACCEPTED"));
		
		Game game = new Game(server, name);

		while(!returnMessage.equals("GAME_START")) {
			game.start();
		}
	}

}

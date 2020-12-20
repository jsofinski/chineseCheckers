package tech.chineseCheckers.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CommunicationService {
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	public boolean connect(String IP, int port) {
		try {
			socket = new Socket(IP, port);
			
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());
			
			
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	public String getMessage() {
		return in.nextLine();
	}
	public void sentMessage(String message) {
		out.println(message);
	}
}

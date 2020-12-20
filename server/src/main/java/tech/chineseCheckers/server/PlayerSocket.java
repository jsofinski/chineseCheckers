package tech.chineseCheckers.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerSocket {

	private Socket socket;
	private PrintWriter out;
	private Scanner in;
	
	public PlayerSocket(Socket socket) throws IOException {
		this.socket = socket;
		out = new PrintWriter(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
	
	}
	
	public void close() {
		out.close();
		in.close();

		try {
			socket.close();
		} catch (IOException e) {
		}
	}
	
	public void send(String str) {
		out.println(str);
	}
	
	public String recive() {
		String temp = in.nextLine();
		return temp;
	}
	
	
}

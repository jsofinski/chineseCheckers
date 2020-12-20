package tech.chineseCheckers.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerSocket {

	private Socket socket;
	private DataOutputStream  out;
	private DataInputStream  in;
	
	public PlayerSocket(Socket socket) throws IOException {
		this.socket = socket;
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
	
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}
	
	public void send(String str) {
		System.out.println("Send: " + str);
		try {
			out.writeUTF(str);
		} catch (IOException e) {
			
		}
	}
	
	public String recive() {
		String temp;
		try {
			temp = in.readUTF();
			return temp;
		} catch (IOException e) {
			return "";
		}
		
	}
	
	
}

package tech.chineseCheckers.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommunicationService {
	
	private Socket socket;
	private DataOutputStream  out;
	private DataInputStream  in;
	
	public boolean connect(String IP, int port) {
		try {
			socket = new Socket(IP, port);
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	public String getMessage() {
		
		String mess;
		
		try {
			mess = in.readUTF();
			System.out.println("Recieved: " + mess);
			
			return mess;
		} catch (IOException e) {
			System.out.println("GET MESSAGE FAILED!");
			return "ERROR";
		}
		
	}
	public void sentMessage(String message) {
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			
		}
	}
}

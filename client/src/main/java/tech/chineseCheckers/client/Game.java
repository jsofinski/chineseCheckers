package tech.chineseCheckers.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;  
import java.awt.event.*;  

public class Game implements MouseListener{
	public Game() {
		GameConfig gameConfig = new GameConfig();
		Board board = new Board(40, gameConfig);
		GameFrame gameFrame = new GameFrame(board);
	}

	public void mouseClicked(MouseEvent arg0) {
		System.out.println("game: click");
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

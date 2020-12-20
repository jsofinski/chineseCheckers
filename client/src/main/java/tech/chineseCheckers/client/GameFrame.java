package tech.chineseCheckers.client;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	
	int width;
	int height;
	public GameFrame(Board board, String name) {
		super("Chinese checkers " + name);
		add(board);
		this.width = 1000;
		this.height = 800;
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

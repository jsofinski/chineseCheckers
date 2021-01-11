package tech.chineseCheckers.client;

import javax.swing.JFrame;

/**
 * Shows board in a frame.
 */
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

package tech.chineseCheckers.client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Menu that is created at the very begging of the program.
 * At current state it allows to get player nick.
 */
public class StartMenu {
	
    JFrame frame = new JFrame("ChineseCheckers - game config");
    
    public String getName() {
        return JOptionPane.showInputDialog(frame, "Podaj swój nick:", "Game name selection",
                JOptionPane.PLAIN_MESSAGE);
    }
}

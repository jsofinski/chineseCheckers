package tech.chineseCheckers.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StartMenu {
	
	String serverAddress;
    Scanner in;
    PrintWriter out;
    JFrame frame = new JFrame("ChineseCheckers - game config");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);
    
    
    //JFrame frame;
    JPanel buttonPane, fieldsPanel;
    JLabel cash, checks;
    JTextField cashField, checksField;
    JButton ok, cancel;
    
    public StartMenu() {
    	

    	//this.getName();
    }
    
    public String getName() {
        return JOptionPane.showInputDialog(frame, "Choose a screen name:", "Game name selection",
                JOptionPane.PLAIN_MESSAGE);
    }
    
}

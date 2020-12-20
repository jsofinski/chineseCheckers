package tech.chineseCheckers.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
	
	int[] fieldArray;
	int size;
	ArrayList<Pawn> pawns;
	ArrayList<Field> fields;
	ArrayList<Field> possibleFields;
	ArrayList<Player> players;
	Pawn activePawn;
	Player me;
	CommunicationService server;

	
	public Board(int size, GameConfig gameConfig, CommunicationService server, String myName) {
		this.server = server;
		this.size = size;
		this.fieldArray = gameConfig.fieldArray;
		this.players = new ArrayList<Player>();
		System.out.println("myname: " + myName);
		for(Player player: gameConfig.players) {
			this.players.add(player);
			if (player.getNick().equals(myName)) {
				this.me = player;
				System.out.println("Found myself");
			}
		}
		this.setFields();
		this.setPawns();
		addMouseListener(this);
	}
	

	public void paintComponent(Graphics g) {
		this.paintFields(g);
		this.paintPawns(g);
		this.printPlayers(g);
		if (this.activePawn != null) {
			this.setPossibleFields(g);
		}
	}
	
	private void paintPawns(Graphics g) {
		for (Player player: this.players) {
			for (Pawn pawn: player.getMyPawns()) {
				g.setColor(pawn.getColor());
				g.fillOval(pawn.getX(), pawn.getY(), pawn.getSize(), pawn.getSize());
			}
		}
	}
	
	private void printPlayers(Graphics g) {
		int x = 750;
		int y = 40;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x - this.size/2, this.size/2, 100, 200);

		for (Player player: this.players) {
			g.setColor(Color.black);
			if (player.equals(this.me)) {
				g.setColor(player.getColor());
				g.setFont(new Font("default", Font.BOLD, 12));
			}
			g.drawString(player.getNick(), x, y);
			g.setFont(new Font("default", Font.PLAIN, 12));
			y += (this.size/2);

		}
	}
	
	private void paintFields(Graphics g) {
		for (Field field: this.fields) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(field.getX(), field.getY(), field.getSize(), field.getSize());
		}
	}

	private void setFields() {
		this.fields = new ArrayList<Field>();
		int xPosition = 400;
		int yPosition = 20;
		int pawnPosition = 0;
		int id = 0;
		for (int i: this.fieldArray) {
			xPosition = xPosition - (i*this.size / 2);
			for (int j = 0; j < i; j++) {
				this.fields.add(new Field(xPosition, yPosition, this.size, Integer.toString(id)));
				xPosition += this.size; 
				pawnPosition++;
				id++;
			}
			yPosition += this.size; 
			xPosition = 400;
		}
	}
	
	private void setPawns() {
		this.pawns = new ArrayList<Pawn>();
		for (Player player: this.players) {
			int xPosition = 400;
			int yPosition = 20;
			int row = 0;
			for (int i: this.fieldArray) {
				xPosition = xPosition - (i*this.size / 2);
				for(int j = 0; j < i; j++) {
					//System.out.println("row: " + row + "; j: " + j);
					int[] playerPawnsPositions = player.getPlayerPawnsPositions()[row];
					if(playerPawnsPositions.length != 0) {	// wchodzimy jeœli player ma w tym wierszu jakieœ pionki
						for (int k: playerPawnsPositions) {	
							if (k == j) {
								Pawn newPawn = new Pawn(this.size);
								newPawn.setColor(player.getColor());
								newPawn.setPosition(xPosition, yPosition);
								newPawn.setPlayerNick(player.getNick());
								player.addPawn(newPawn);
								this.pawns.add(newPawn);
							}
						}
					}
					xPosition += this.size;
				}
				xPosition = 400;
				yPosition += this.size; 
				row++;
			}
		}
	}	
	
	private boolean canPawnMoveThere(Pawn pawn, Field field) {
		int fieldX = field.getX();
		int fieldY = field.getY();
		int pawnX = pawn.getX();
		int pawnY = pawn.getY();
		if ((Math.abs(fieldX - pawnX) == this.size &&
				Math.abs(fieldY - pawnY) == 0) ||
			 	(Math.abs(fieldX - pawnX) == (this.size/2) &&
				Math.abs(fieldY - pawnY) == this.size)) {
			return true;
		}
		return false;
	}
	
	private boolean isFieldOccupied(Field field) {
		for (Pawn anyPawn: this.pawns) {
			if (anyPawn.getX() == field.getX() && anyPawn.getY() == field.getY()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean canPawnHopThere(Pawn pawn, Field jumpOverField, Field destinationField) {
		int innerDistanceX = (jumpOverField.getX() - this.activePawn.getX());
		int innerDistanceY = (jumpOverField.getY() - this.activePawn.getY());
		int fieldsDistanceX = (destinationField.getX() - jumpOverField.getX());
		int fieldsDistanceY = (destinationField.getY() - jumpOverField.getY());
		if (innerDistanceX == fieldsDistanceX && innerDistanceY == fieldsDistanceY) {
			if(!this.isFieldOccupied(destinationField)) {
				return true;
			}
		}
		return false;
	}
	
	private void setPossibleFields(Graphics g) {
		this.possibleFields = new ArrayList<Field>();
		for (Field field: this.fields) {
			if(this.canPawnMoveThere(this.activePawn, field)) {
				if (!this.isFieldOccupied(field)) {
					this.possibleFields.add(field);
					g.setColor(Color.GRAY);
					g.fillOval(field.getX(), field.getY(), field.getSize(), field.getSize());
				}
				else {
					for (Field innerField: this.fields) {
						if(this.canPawnHopThere(this.activePawn, field, innerField)) {
							if (!this.isFieldOccupied(innerField)) {
								this.possibleFields.add(innerField);
								g.setColor(Color.GRAY);
								g.fillOval(innerField.getX(), innerField.getY(), innerField.getSize(), innerField.getSize());
							}
						}
					}
				}
			}
		}
	}
	public void moveByIds(String idFrom, String idTo) {
		Field fieldFrom = null;
		Field fieldTo = null;
		for (Field field: this.fields) {
			if (field.getId().equals(idFrom)) {
				fieldFrom = field;
			}
			if (field.getId().equals(idTo)) {
				fieldTo = field;
			}
		}
		if (!this.isFieldOccupied(fieldTo)) {
			if (this.isFieldOccupied(fieldFrom)) {
				for (Pawn pawn: this.pawns) {
					if (pawn.getX() == fieldFrom.getX() && pawn.getY() == fieldFrom.getY()) {
						pawn.setPosition(fieldTo.getX(), fieldTo.getY());
					}
				}
			}
		}
		this.repaint();
	}
	
	private void movePawn(Pawn pawn, Field field) {
		
		if(this.possibleFields.contains(field)) {
			Field fromField = field;
			for(Field pawnField: this.fields) {
				if (pawn.getX() == pawnField.getX() && pawn.getY() == pawnField.getY()) {
					fromField = pawnField;
				}
			}
			if (fromField != null) {
				System.out.println("MOVE " + fromField.getId() + " " + field.getId());
				this.server.sentMessage(("MOVE " + fromField.getId() + " " + field.getId()));
			}
			
			this.activePawn.setPosition(field.getX(), field.getY());
			this.activePawn = null;
		}
		else {
			System.out.println("error, wrong move");
		}
 
	}
	
	public void mouseClicked(MouseEvent arg0) {
		boolean pawnFound = false;
		for (Pawn pawn: this.pawns) {
			if (pawn != null) {
	            if ((new Ellipse2D.Float(pawn.getX(), pawn.getY(), pawn.getSize(), pawn.getSize())).contains(arg0.getPoint())) {
	            	if (pawn.getPlayerNick().equals(this.me.getNick())) {
		            	pawnFound = true;
		            	this.activePawn = pawn;
	            	}
	            	else {
	            		System.out.println("To nie twój pionek!");
	            	}
	            }
	            this.repaint();
			}
        }
		if (!pawnFound) {
			for (Field field: this.fields) {
	            if ((new Ellipse2D.Float(field.getX(), field.getY(), field.getSize(), field.getSize())).contains(arg0.getPoint())) {
	            	if (this.activePawn != null) {
		            	//System.out.println((field.getBounds().x - this.activePawn.getShape().getBounds().x) + "  " + (field.getBounds().y - this.activePawn.getShape().getBounds().y));
	            		this.movePawn(this.activePawn, field);
	            		//System.out.println("ActivePawn change");
	            	}
	            	//System.out.println("field: x: " + arg0.getX() + "; y: " + arg0.getY());
	            }
	            this.repaint();
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {	}

	public void mouseExited(MouseEvent arg0) { }

	public void mousePressed(MouseEvent arg0) {	}

	public void mouseReleased(MouseEvent arg0) { }

	public void mouseDragged(MouseEvent arg0) {	}

	public void mouseMoved(MouseEvent arg0) { }

	public void actionPerformed(ActionEvent arg0) {	}
}

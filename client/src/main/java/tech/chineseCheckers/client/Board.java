package tech.chineseCheckers.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
	
	int[] fieldArray = {1,2,3,4,13,12,11,10,9,10,11,12,13,4,3,2,1};
	int size;
	ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	ArrayList<Shape> fields = new ArrayList<Shape>();
	ArrayList<Shape> possibleFields = new ArrayList<Shape>();
	ArrayList<Player> players = new ArrayList<Player>();
	Pawn activePawn;
	public Board(int size) {
		this.size = size;
		
		Player player1 = new Player();
		Player player2 = new Player();
		int [][] player1positons = {{0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}};
		int [][] player2positons = {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}};
		player1.setPlayerPawnsPositions(player1positons);
		player2.setPlayerPawnsPositions(player2positons);
		player1.setColor(Color.blue);
		player2.setColor(Color.red);
		player1.setNick("player1");
		player2.setNick("player2");

		this.players.add(player1);
		this.players.add(player2);
		
		this.setFields();
		this.setPawns();

		addMouseListener(this);
	}

	
	public void paintComponent(Graphics g) {
		// System.out.println("paintComponent");
		this.paintFields(g);
		this.paintPawns(g);
		if (this.activePawn != null) {
			this.setPossibleFields(g);
		}
	}
	
	private void paintPawns(Graphics g) {
		// System.out.println("paintPawns");

		for (Player player: this.players) {
			for (Pawn pawn: player.getMyPawns()) {
				g.setColor(pawn.getColor());
				g.fillOval(pawn.getShape().getBounds().x, pawn.getShape().getBounds().y, pawn.getShape().getBounds().height, pawn.getShape().getBounds().width);
			}
		}
	}
	
	
	private void paintFields(Graphics g) {
		// System.out.println("paintFields");
		for (Shape field: this.fields) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(field.getBounds().x, field.getBounds().y, field.getBounds().height, field.getBounds().width);
		}
	}

	public void setFields() {
		// System.out.println("setFields");
		this.fields = new ArrayList<Shape>();
		int xPosition = 400;
		int yPosition = 20;
		int pawnPosition = 0;
		for (int i: this.fieldArray) {
			xPosition = xPosition - (i*this.size / 2);
			for (int j = 0; j < i; j++) {
				this.fields.add(new Ellipse2D.Float(xPosition, yPosition, this.size, this.size));
				xPosition += this.size; 
				pawnPosition++;
			}
			yPosition += this.size; 
			xPosition = 400;
		}
	}
	
	public void setPawns() {
		for (Player player: this.players) {
			int xPosition = 400;
			int yPosition = 20;
			int pawnPosition = 0;
			int row = 0;
			for (int i: this.fieldArray) {
				
				xPosition = xPosition - (i*this.size / 2);
				
				for (int j = 0; j < i; j++) {
					int[] positions = (player.getPlayerPawnsPositions())[row];
					if(positions.length != 0) {
						if(positions[j] == j) {
							Pawn newPawn = new Pawn(this.size);
							newPawn.setColor(player.getColor());
							newPawn.setShape(xPosition, yPosition);
							newPawn.setPlayerNick(player.getNick());
							player.addPawn(newPawn);
							this.pawns.add(newPawn);
						}
						xPosition += this.size;
					}
				}
				xPosition = 400;
				yPosition += this.size; 
				row++;
			}
		}
	}
	
	
	
	private boolean canPawnMoveThere(Pawn pawn, Shape field) {
		int fieldX = field.getBounds().x;
		int fieldY = field.getBounds().y;
		int pawnX = pawn.getShape().getBounds().x;
		int pawnY = pawn.getShape().getBounds().y;
		if ((Math.abs(fieldX - pawnX) == this.size &&
				Math.abs(fieldY - pawnY) == 0) ||
			 	(Math.abs(fieldX - pawnX) == (this.size/2) &&
				Math.abs(fieldY - pawnY) == this.size)) {
			return true;
		}
		return false;
	}
	
	private boolean isFieldOccupied(Shape field) {
		for (Pawn anyPawn: this.pawns) {
			if (anyPawn.getShape().getBounds().equals(field.getBounds())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean canPawnHopThere(Pawn pawn, Shape jumpOverField, Shape destinationField) {
		int innerDistanceX = (jumpOverField.getBounds().x - this.activePawn.getShape().getBounds().x);
		int innerDistanceY = (jumpOverField.getBounds().y - this.activePawn.getShape().getBounds().y);
		int fieldsDistanceX = (destinationField.getBounds().x - jumpOverField.getBounds().x);
		int fieldsDistanceY = (destinationField.getBounds().y - jumpOverField.getBounds().y);
		if (innerDistanceX == fieldsDistanceX && innerDistanceY == fieldsDistanceY) {
			if(!this.isFieldOccupied(destinationField)) {
				return true;
			}
		}
		return false;
	}
	
	private void setPossibleFields(Graphics g) {
		this.possibleFields = new ArrayList<Shape>();
		for (Shape field: this.fields) {
			if(this.canPawnMoveThere(this.activePawn, field)) {
				if (!this.isFieldOccupied(field)) {
					this.possibleFields.add(field);
					g.setColor(Color.GRAY);
					g.fillOval(field.getBounds().x, field.getBounds().y, field.getBounds().height, field.getBounds().width);
				}
				else {
					for (Shape innerField: this.fields) {
						if(this.canPawnHopThere(this.activePawn, field, innerField)) {
							this.possibleFields.add(innerField);
							g.setColor(Color.GRAY);
							g.fillOval(innerField.getBounds().x, innerField.getBounds().y, innerField.getBounds().height, innerField.getBounds().width);
						}
					}
				}
			}		
		}	
	}
	
	private void movePawn(Pawn pawn, Shape field) {
		if(this.possibleFields.contains(field)) {
			this.activePawn.setShape(field.getBounds().x, field.getBounds().y);
			this.activePawn = null;
		}
		else {
			System.out.println("error, wrong move");
		}
 
	}
	
	public void mouseClicked(MouseEvent arg0) {
		boolean pawnFound = false;
		for (Pawn pawn: this.pawns) {
				if (pawn.getShape() != null) {
		            if (pawn.getShape().contains(arg0.getPoint())) {
		            	pawnFound = true;
		            	this.activePawn = pawn;
		            	System.out.println("pawn: x: " + arg0.getX() + "; y: " + arg0.getY() + "; player: " + pawn.getPlayerNick());
		            }
		            this.repaint();
			}
        }
		if (!pawnFound) {
			for (Shape field: this.fields) {
	            if (field.contains(arg0.getPoint())) {
	            	if (this.activePawn != null) {
		            	System.out.println((field.getBounds().x - this.activePawn.getShape().getBounds().x) + "  " + (field.getBounds().y - this.activePawn.getShape().getBounds().y));
	            		this.movePawn(this.activePawn, field);
	            		System.out.println("ActivePawn change");
	            	}
	            	System.out.println("field: x: " + arg0.getX() + "; y: " + arg0.getY());
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

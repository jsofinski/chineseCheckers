package tech.chineseCheckers.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Pawn {
	
	Color color;
	Shape shape;
	String playerNick;
	int size;
	
	public String getPlayerNick() {
		return playerNick;
	}
	public void setPlayerNick(String playerNick) {
		this.playerNick = playerNick;
	}

	public Pawn(int size) {
		this.size = size;
	}
	
	public void setShape(int xPosition, int yPosition) {
		this.shape = new Ellipse2D.Float(xPosition, yPosition, this.size, this.size);
	}
	
	public Shape getShape() {
		return this.shape;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
}

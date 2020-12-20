package tech.chineseCheckers.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Pawn {
	
	Color color;
	int xPosition;
	int yPosition;
	String playerNick;
	String id;
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
	
	public void setPosition(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	public int getX() {
		return this.xPosition;
	}
	public int getY() {
		return this.yPosition;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}

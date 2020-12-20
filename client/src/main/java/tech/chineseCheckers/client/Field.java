package tech.chineseCheckers.client;

public class Field {
	String id;
	int size;
	int xPosition;
	int yPosition;
	
	public Field(int xPosition, int yPosition, int size, String id) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.size = size;
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	public int getX() {
		return xPosition;
	}

	public void setX(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getY() {
		return yPosition;
	}

	public void setY(int yPosition) {
		this.yPosition = yPosition;
	}
}

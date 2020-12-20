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
	
	public void setSize(Int size) {
		this.size = size;
	}
	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
}

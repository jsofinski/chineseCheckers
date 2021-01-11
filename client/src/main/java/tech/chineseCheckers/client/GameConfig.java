package tech.chineseCheckers.client;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

/**
 * Game configuration holds all data to draw at @Board any shape board and
 * pawns positions of all of the players in any number of player case.
 */
public class GameConfig {
	
	int[] fieldArray;
	ArrayList<Field> fields;
	ArrayList<Player> players;

	public GameConfig() {
		this.setFieldArray();
	}
	
	/**
	 * Sets players colours and pawns positions depending on number of players
	 */
	protected void setPlayers(int playerAmount) {
		this.players = new ArrayList<Player>();
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		Player player5 = new Player();
		Player player6 = new Player();
		player1.setColor(Color.blue);
		player2.setColor(Color.red);
		player3.setColor(Color.green);
		player4.setColor(Color.magenta);
		player5.setColor(Color.orange);
		player6.setColor(Color.pink);
		
		switch (playerAmount) {
			case 2:
				System.out.println("Setting board for 2 players");
				player1.setPlayerPawnsPositions(new int[][] {{0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
				player2.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}});
				this.players.add(player1);
				this.players.add(player2);
				break;
			case 3:
				System.out.println("Setting board for 3 players");
				player1.setPlayerPawnsPositions(new int[][] {{0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
				player2.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}});
				player3.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {9}, {9,10}, {9,10,11}, {9,10,11,12}, {}, {}, {}, {}});
				this.players.add(player1);
				this.players.add(player2);
				this.players.add(player3);
				break;
			case 4:
				System.out.println("Setting board for 4 players");
				player1.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
				player2.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {9,10,11,12}, {9,10,11}, {9,10}, {9}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
				player3.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}});
				player4.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {9}, {9,10}, {9,10,11}, {9,10,11,12}, {}, {}, {}, {}});
				this.players.add(player1);
				this.players.add(player2);
				this.players.add(player3);
				this.players.add(player4);
				break;
			case 6:
				System.out.println("Setting board for 6 players");
				player1.setPlayerPawnsPositions(new int[][] {{0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
				player2.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
				player3.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {9,10,11,12}, {9,10,11}, {9,10}, {9}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
				player4.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}});
				player5.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {9}, {9,10}, {9,10,11}, {9,10,11,12}, {}, {}, {}, {}});
				player6.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}});

				this.players.add(player1);
				this.players.add(player2);
				this.players.add(player3);
				this.players.add(player4);
				this.players.add(player5);
				this.players.add(player6);
				break;
		}
	}

	/**
	 * Sets shape of the board putting length of every row to each row.
	 */
	private void setFieldArray() {
		this.fields = new ArrayList<Field>();
		int[] tempArray;
		tempArray = new int[17];
		tempArray[0] = 1;
		tempArray[1] = 2;
		tempArray[2] = 3;
		tempArray[3] = 4;
		tempArray[4] = 13;
		tempArray[5] = 12;
		tempArray[6] = 11;
		tempArray[7] = 10;
		tempArray[8] = 9;
		tempArray[9] = 10;
		tempArray[10] = 11;
		tempArray[11] = 12;
		tempArray[12] = 13;
		tempArray[13] = 4;
		tempArray[14] = 3;
		tempArray[15] = 2;
		tempArray[16] = 1;
		this.fieldArray = tempArray;
	}
}

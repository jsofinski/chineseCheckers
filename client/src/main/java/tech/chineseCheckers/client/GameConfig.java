package tech.chineseCheckers.client;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

public class GameConfig {
	int[] fieldArray;
	ArrayList<Field> fields = new ArrayList<Field>();
	ArrayList<Player> players = new ArrayList<Player>();
	
	public GameConfig() {
		this.setPlayers(2);
		this.setFieldArray();
	}
	
	private void setPlayers(int playerAmount) {
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
		player1.setNick("player1");
		player2.setNick("player2");
		player3.setNick("player3");
		player4.setNick("player4");
		player5.setNick("player5");
		player6.setNick("player6");
		
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

	private void setFieldArray() {
		int[] tempArray = {1,2,3,4,13,12,11,10,9,10,11,12,13,4,3,2,1};
		this.fieldArray = tempArray;
	}
}

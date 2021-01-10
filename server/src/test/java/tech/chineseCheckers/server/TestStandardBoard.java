package tech.chineseCheckers.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestStandardBoard {

	
	@Test
	void testMoves() {
		Game g = new StandardGame(new GameRules());
		g.addPlayer("A");
		g.addPlayer("B");
		
		// Try moving a pawn into another pawn
		assertFalse(g.interpretMove("A", "0 1"));
		// Try moving a pawn of another player
		assertFalse(g.interpretMove("A", "111 102"));
		
		// Try moving a pawn out of opponents start triangle
		//		Move one of opponents pawn out of starting zone
		assertTrue(g.interpretMove("B", "111 102"));
		//		Move a pawn into free spot
		assertTrue(g.interpretMove("A", "0 111"));
		//		Try to leave
		assertFalse(g.interpretMove("A", "111 0"));
	}
	@Test
	void testWinConditions() {
		// Check win, when opponents pwan counts towards own goal
		GameRules r = new GameRules();
		r.blockRule = GameRules.BlockRules.CountTowardsWin;
		Game g = new StandardGame(r);
		g.addPlayer("A");
		g.addPlayer("B");
		g.interpretMove("B", "111 102");
		g.interpretMove("A", "0 111");
		assertTrue(g.winner().equals("A"));
		
		// Check win when opponents pawns dont count towards own goal
		GameRules r2 = new GameRules();
		r2.blockRule = GameRules.BlockRules.Lose;
		Game g2 = new StandardGame(r2);
		g2.interpretMove("B", "111 102");
		g2.interpretMove("A", "0 111");
		assertTrue(g.winner().equals(""));
		
		
		
	}
}

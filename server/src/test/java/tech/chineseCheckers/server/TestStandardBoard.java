package tech.chineseCheckers.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestStandardBoard {

	
	@Test
	void testMoves() {
		GameRules r = new GameRules();
		r.canLeaveGoalZone = false;
		r.pawnsSwappable = true;
		Game g = new StandardGame(r,2);
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
		
		// Check if player can leave goal zone if rules allow it
		GameRules r2 = new GameRules(1, false, true, true, true);
		Game g2 = new StandardGame(r2,2);
		g2.addPlayer("A");
		g2.addPlayer("B");
		g2.interpretMove("A", "0 111");
		assertTrue(g2.interpretMove("A", "111 102"));
	}
	@Test
	void testWinConditions() {
		// Check win, when opponents pawns count towards own pawns
		GameRules r = new GameRules();
		r.opponentsPawnsCounts = true;
		Game g = new StandardGame(r,2);
		g.addPlayer("A");
		g.addPlayer("B");
		// No winners should be present
		assertTrue(g.winner().equals(""));
		
		
		g.interpretMove("B", "111 102");
		g.interpretMove("A", "0 111");
		assertTrue(g.winner().equals("A"));
		
		// Check win when opponents pawns dont count towards own goal
		GameRules r2 = new GameRules();
		r2.opponentsPawnsCounts = false;
		Game g2 = new StandardGame(r2,2);
		g2.interpretMove("B", "111 102");
		g2.interpretMove("A", "0 111");
		assertTrue(g.winner().equals(""));
	}	
}

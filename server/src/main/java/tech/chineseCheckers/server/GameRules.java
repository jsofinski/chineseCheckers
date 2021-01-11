package tech.chineseCheckers.server;

/**
 * Hold game rules. Currently supporter rules are:
 * - players can skip turns
 * - if the opponents pawns count toward owns when checking for win condition
 * - if the pawns can leave goal zone
 * - if player can swap pawns with another player
 * It also holds how many wins are necessary for a game to end.
 * @author Jakub
 *
 */
public class GameRules {
	
	public boolean turnSkippable;
	public int winsBeforeGameEnds;
	public boolean opponentsPawnsCounts;
	public boolean pawnsSwappable;
	public boolean canLeaveGoalZone;
	
	public GameRules() {
		opponentsPawnsCounts = false;
		pawnsSwappable = true;
		turnSkippable = false;
		winsBeforeGameEnds = 1;
		canLeaveGoalZone = false;
	}
	public GameRules(int winsBeforeGameEnds, boolean turnSkippable, boolean pawnsSwappable, boolean opponentsPawnsCounts, boolean canLeaveGoalZone) {
		this.opponentsPawnsCounts = opponentsPawnsCounts;
		this.pawnsSwappable = pawnsSwappable;
		this.turnSkippable = turnSkippable;
		this.winsBeforeGameEnds = winsBeforeGameEnds;
		this.canLeaveGoalZone = canLeaveGoalZone;
	}
}

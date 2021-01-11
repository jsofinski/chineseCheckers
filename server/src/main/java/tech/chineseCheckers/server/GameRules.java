package tech.chineseCheckers.server;

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

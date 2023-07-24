package patience;

/**
 * This is class keeps and updates the score.
 */

public class Score {
	
	private static final int CARD_PILE_TO_LANE = 5;
	private static final int CARD_PILE_TO_SUIT = 10;
	private static final int CARD_LANE_TO_LANE_WITH_REVEAL  = 5;
	private static final int CARD_SUIT_TO_LANE = -30;
	private static final int CARD_LANE_TO_SUIT = 20;
	
	private int score;
	private int numberOfTurns;
	
	Score () {
		score = 0;
		numberOfTurns = 0;
	}
	
	public int getScore () {
		return score;
	}
	
	public int getNumberOfTurns () {
		return numberOfTurns;
	}
	
	public void drawPlayed (Command command) {
		numberOfTurns++;
	}
	
	public void movePlayed (Command command, Board board) {
		numberOfTurns++;
		if (command.isMoveFromDeck() && command.isMoveToLane()) {
			score += CARD_PILE_TO_LANE;
		} else if (command.isMoveFromDeck() && command.isMoveToSuit()) {
			score += CARD_PILE_TO_SUIT;
		} else if (command.isMoveFromLane() && command.isMoveToLane() && board.wasCardReveal()) {
			score += CARD_LANE_TO_LANE_WITH_REVEAL;
		} else if (command.isMoveFromSuit() && command.isMoveToLane()) {
			score += CARD_SUIT_TO_LANE;			
		} else if (command.isMoveFromLane() && command.isMoveToSuit())
			score += CARD_LANE_TO_SUIT;
	}
}

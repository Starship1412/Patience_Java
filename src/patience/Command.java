package patience;

/**
 * This class stores and performs syntax checking on a command.
// Requires Eclipse 22.09 or later
 */

public class Command {
	
	private enum CommandType {
		
		DRAW,
		MOVE,
		QUIT
	}
	
	private CommandType commandType;
	private char moveFrom, moveTo;
	private String numberOfCards;
	
	Command (String input) {
		String inputFormatted = input.trim().toUpperCase();
		if (inputFormatted.equals("Q")) {
			commandType = CommandType.QUIT;
		} else if (inputFormatted.equals("D")) {
			commandType = CommandType.DRAW;
		} else {
			commandType = CommandType.MOVE;
			moveFrom = inputFormatted.charAt(0);
			moveTo = inputFormatted.charAt(1);
			if (inputFormatted.length()==2)
				numberOfCards = "";
			else
				numberOfCards = inputFormatted.substring(2);
		} 	
	}
	
	private int suitToIndex (char character) {
		return switch(character) {
			case 'D' -> 0;
			case 'H' -> 1;
			case 'C' -> 2;
			case 'S' -> 3;
			default -> 0;
		};
	}
	
	private Suit suitToSuit (char character) {
		return switch(character) {
			case 'D' -> Suit.DIAMOND;
			case 'H' -> Suit.HEART;
			case 'C' -> Suit.CLUB;
			case 'S' -> Suit.SPADE;
			default -> Suit.DIAMOND;
		};
	}
	
	
	
	public static boolean isValid (String input) {
		String inputFormatted = input.trim().toUpperCase();
		return  inputFormatted.equals("Q") || inputFormatted.equals("D") || inputFormatted.matches("[P1-7DHCS][1-7DHCS][0-9]*");
	}
	
	public boolean isQuit () {
		return commandType == CommandType.QUIT;
	}
	
	public boolean isDraw () {
		return commandType == CommandType.DRAW;
	}
	
	public boolean isMove () {
		return commandType == CommandType.MOVE;
	}

	public boolean isMoveFromDeck () {
		return moveFrom == 'P';
	}
	
	public boolean isMoveFromLane () {
		return Character.toString(moveFrom).matches("[1-7]");
	}
	
	public boolean isMoveFromSuit () {
		return Character.toString(moveFrom).matches("[DHCS]");
	}
	
	public boolean isMoveToLane () {
		return Character.toString(moveTo).matches("[1-7]");
	}
	
	public boolean isMoveToSuit () {
		return Character.toString(moveTo).matches("[DHCS]");
	}		
	
	public int getFromIndex () {
		if (isMoveFromLane())
			return Character.getNumericValue(moveFrom) - 1;
		else // isMoveFromSuit()
			return suitToIndex(moveFrom);
	}
	
	public int getToIndex () {
		if (isMoveToLane())
			return Character.getNumericValue(moveTo) - 1;
		else // isMoveToSuit()
			return suitToIndex(moveTo);
	}
	
	public Suit getToSuit () {
		return suitToSuit(moveTo);
	}

	public int getNumberOfCardsToMove () {
		if (numberOfCards.equals("") || numberOfCards.equals("1"))
			return 1;
		else
			return Integer.valueOf(numberOfCards);
	}
}

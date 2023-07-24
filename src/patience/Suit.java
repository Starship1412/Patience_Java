package patience;

/**
 * This class enumerates the suits in a deck of playing cards.
 */

public enum Suit {
	
	DIAMOND (DisplayColour.RED + "♦", SuitColour.RED),
	HEART (DisplayColour.RED + "♥︎", SuitColour.RED),
	CLUB (DisplayColour.BLACK + "♣︎", SuitColour.BLACK),
	SPADE (DisplayColour.BLACK + "♠︎", SuitColour.BLACK);
	
	private String symbol;
	private SuitColour colour;
	
	Suit (String symbol, SuitColour colour) {
		this.symbol = symbol;
		this.colour = colour;
	}
	
	public SuitColour getColour () {
		return colour;
	}
	
	public String toString () {
		return symbol;
	}
}

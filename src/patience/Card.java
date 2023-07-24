package patience;

/**
 * This class models a single card.
 */

public class Card {
	
	private Suit suit;
	private Rank value;
	private boolean isFaceUp;
	
	Card () {
		suit = Suit.HEART;
		value = Rank.QUEEN;
		isFaceUp = true;
	}
	
	Card (Suit suit, Rank value, boolean faceUp) {
		this.suit = suit;
		this.value = value;
		this.isFaceUp = faceUp;
	}
	
	private boolean isSameSuit (Card card) {
		return suit == card.getSuit();
	}
	
	private boolean isNextLowerValue (Card card) {
		return this.value.ordinal()-1 == card.value.ordinal();
	}
	
	private boolean isNextHigherValue (Card card) {
		return this.value.ordinal()+1 == card.value.ordinal();
	}
	
	private boolean isDifferentColour (Card card) {
		return this.suit.getColour() != card.suit.getColour();
	}
	
	
	
	public void turnFaceUp () {
		isFaceUp = true;
	}
	
	public void turnFaceDown () {
		isFaceUp = false;
	}
	
	public boolean isFaceUp () {
		return isFaceUp;
	}
	
	public boolean isFaceDown () {
		return !isFaceUp;
	}

	public Suit getSuit () {
		return suit;
	}
	
	public Rank getValue () {
		return value;
	}
	
	public boolean isNextInLane (Card card) {
		return this.isDifferentColour(card) && this.isNextLowerValue(card);
	}
	
	public boolean isNextInSuit (Card card) {
		return this.isSameSuit(card) && this.isNextHigherValue(card);
	}
	
	public String toString () {
		if (isFaceUp)
			return suit.toString() + value.toString() + DisplayColour.RESET;
		else
			return DisplayColour.PURPLE + "XXX" + DisplayColour.RESET;
	}
}

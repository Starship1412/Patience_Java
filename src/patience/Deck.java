package patience;

import java.util.*;

/**
 * This class models a full 52 card deck of cards.
 */

public class Deck extends Stack<Card> {
	
	Deck () {
		for (Suit suit : Suit.values())
			for (Rank cardValue : Rank.values())
				super.add(new Card(suit, cardValue, false));
	}
	
	public void shuffle () {
		Collections.shuffle(this);
	}
}

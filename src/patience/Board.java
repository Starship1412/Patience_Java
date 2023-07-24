package patience;

import java.util.*;

/**
 * This class models the board.
 */

public class Board {
	
	public static final int NUM_LANES = 7;
	public static final int NUM_SUITS = Suit.values().length; // = 4
	public static final int SUIT_SIZE = Rank.values().length; // = 13
	
	private Deck deck;
	private Stack<Card> pile;
	private List<Stack<Card>> lanes;
	private List<Stack<Card>> suits;
	// Could use arrays of Lists but arrays of generic types are messy
	// I used List and Stack from Collections but ArrayList and a programmer defined class CardStack are OK too
	// Arrays work well with CardStack
	private boolean wasCardReveal;
	
	Board () {
		deck = new Deck();
		pile = new Stack<>();
		lanes = new ArrayList<>(NUM_LANES);
		suits = new ArrayList<>(NUM_SUITS);  // This sentence can also be written as "suits = new ArrayList<Stack<Card>>(NUM_SUITS);"
		wasCardReveal = false;
		
		deck.shuffle();
		for (int i=0; i<NUM_LANES; i++) {
			lanes.add(new Stack<>());        // This sentence can also be written as "lanes.add(new Stack<Card>());"
			for (int j=0; j<i; j++) {
				Card deckCard = deck.pop();  // Do not write this sentence before this "for" loop.
				lanes.get(i).push(deckCard); // Do not write "push(deck.pop())".
			}
			deck.peek().turnFaceUp();
			lanes.get(i).push(deck.peek());  // Here "push" is interchangeable with "add".
			deck.pop();                      // In contrast to the approach to dealing cards just used, this approach does not require a new definition of a Card class.
		}
		for (int i=0; i<NUM_SUITS; i++)
			suits.add(new Stack<>());        // Here "add" cannot be replaced by "push".
	}
	
	public boolean drawIsPossible () {
		return !deck.empty() || !pile.empty();
	}
	
	public void draw () {
		if (!deck.empty() && deck.peek().isFaceDown()) {
			deck.peek().turnFaceUp();
		} else if (!deck.empty() && deck.peek().isFaceUp()) {
			pile.add(deck.peek());
			deck.pop();
			if (!deck.empty())
				deck.peek().turnFaceUp();
			else { // Need to refill deck, bottom of the pile becomes top of the deck. Do not use "peek()" etc. when the stack is empty.
				int numberOfCards = pile.size();
				for (int i=0; i<numberOfCards; i++) { // Do not replace "numberOfCards" with "pile.size()".
					pile.peek().turnFaceDown();
					Card pileCard = pile.pop();
					deck.add(pileCard);
				}
			}
		}
	}
	
	public boolean moveIsPossible (Command command) {
		boolean isPossible = false;
		if (command.isMoveFromDeck() && command.isMoveToLane()) {
			Stack<Card> lane = lanes.get(command.getToIndex());
			if (!deck.empty() && deck.peek().isFaceUp())
				if ((lane.empty() && deck.peek().getValue() == Rank.KING) || (!lane.empty() && lane.peek().isNextInLane(deck.peek())))
					isPossible = true;
		} else if (command.isMoveFromDeck() && command.isMoveToSuit()) {
			Stack<Card> suit = suits.get(command.getToIndex());
			if (!deck.empty() && deck.peek().isFaceUp())
				if ((suit.empty() && deck.peek().getValue() == Rank.ACE && deck.peek().getSuit() == command.getToSuit()) || (!suit.empty() && suit.peek().isNextInSuit(deck.peek())))
					isPossible = true;
		} else if (command.isMoveFromLane() && command.isMoveToSuit()) {
			Stack<Card> lane = lanes.get(command.getFromIndex());
			Stack<Card> suit = suits.get(command.getToIndex());
			if (!lane.empty() && lane.peek().isFaceUp())
				if ((suit.empty() && lane.peek().getValue() == Rank.ACE && lane.peek().getSuit() == command.getToSuit()) || (!suit.empty() && suit.peek().isNextInSuit(lane.peek())))
					isPossible = true;
		} else if (command.isMoveFromSuit() && command.isMoveToLane()) {
			Stack<Card> suit = suits.get(command.getFromIndex());
			Stack<Card> lane = lanes.get(command.getToIndex());
			if (!suit.empty() && suit.peek().isFaceUp())
				if ((lane.empty() && suit.peek().getValue() == Rank.KING) || (!lane.empty() && lane.peek().isNextInLane(suit.peek())))
					isPossible = true;
		} else if (command.isMoveFromLane() && command.isMoveToLane()) {
			List<Card> fromLane = lanes.get(command.getFromIndex()); // Use List interface to the Stack
			Stack<Card> toLane = lanes.get(command.getToIndex());
			int firstCardToMoveIndex = fromLane.size()-command.getNumberOfCardsToMove();
			if (fromLane.size() >= command.getNumberOfCardsToMove() && fromLane.get(firstCardToMoveIndex).isFaceUp()) {
				if ((toLane.empty() && fromLane.get(firstCardToMoveIndex).getValue() == Rank.KING) || (!toLane.empty() && toLane.peek().isNextInLane(fromLane.get(firstCardToMoveIndex))))
					isPossible = true;
			}
		}
		return isPossible;
	}
	
	public void move (Command command) {
		wasCardReveal = false;
		if (command.isMoveFromDeck() && command.isMoveToLane()) {
			Stack<Card> lane = lanes.get(command.getToIndex());
			Card deckCard = deck.pop();
			lane.push(deckCard);
			if(!pile.empty()) {
				Card pileCard = pile.pop();
				deck.add(pileCard);
			}
		} else if (command.isMoveFromDeck() && command.isMoveToSuit()) {
			Stack<Card> suit = suits.get(command.getToIndex());
			Card deckCard = deck.pop();
			suit.push(deckCard);
			if(!pile.empty()) {
				Card pileCard = pile.pop();
				deck.add(pileCard);
			}
		} else if (command.isMoveFromLane() && command.isMoveToSuit()) {
			Stack<Card> lane = lanes.get(command.getFromIndex());
			Stack<Card> suit = suits.get(command.getToIndex());
			Card laneCard = lane.pop();
			suit.push(laneCard);
			if (!lane.empty() && lane.peek().isFaceDown()) {
				lane.peek().turnFaceUp();
				wasCardReveal = true;
			}
		} else if (command.isMoveFromSuit() && command.isMoveToLane()) {
			Stack<Card> suit = suits.get(command.getFromIndex());
			Stack<Card> lane = lanes.get(command.getToIndex());
			Card suitCard = suit.pop();
			lane.push(suitCard);
		} else if (command.isMoveFromLane() && command.isMoveToLane()) {
			List<Card> fromLane = lanes.get(command.getFromIndex()); // Use List interface to the Stack
			Stack<Card> toLane = lanes.get(command.getToIndex());
			int firstCardToMoveIndex = fromLane.size()-command.getNumberOfCardsToMove();
			for (int i=0; i<command.getNumberOfCardsToMove(); i++) {
				Card cardFromLane = fromLane.remove(firstCardToMoveIndex); // Here the second approach: toLane.push(fromLane.get(firstCardToMoveIndex));
				toLane.push(cardFromLane);                                 //                           fromLane.remove(firstCardToMoveIndex);
			}
			if (!fromLane.isEmpty() && fromLane.get(fromLane.size()-1).isFaceDown()) {
				fromLane.get(fromLane.size()-1).turnFaceUp();
				wasCardReveal = true;
			}
		}
	}
	
	public boolean isGameOver () {
		for (Stack<Card> suit : suits)
			if (suit.size() != SUIT_SIZE)
				return false;
		return true;
	}
	
	public int maxLaneSize () {
		int maxLaneSize = 0;
		for (Stack<Card> lane : lanes)
			if (lane.size() > maxLaneSize)
				maxLaneSize = lane.size();
		return maxLaneSize;
	}
	
	public Deck getDeck () {
		return deck;
	}
	
	public Stack<Card> getPile () {
		return pile;
	}
	
	public Stack<Card> getLane (int index) {
		return lanes.get(index);
	}
	
	public Stack<Card> getSuit (int index) {
		return suits.get(index);
	}
	
	public boolean wasCardReveal () {
		return wasCardReveal;
	}
}

package patience;

import java.util.*;

/**
 * This class displays to the console and gets user input from the keyboard.
 */

public class View {
	
	private final static String BLANK = "   ";
	
	Scanner in;
	Command command;
	
	View () {
		in = new Scanner(System.in);
	}
	
	public void displayWelcome () {
		System.out.println("Welcome to Patience");
	}
	
	public void displayScore (Score scoreboard) {
		System.out.println("                Score " + scoreboard.getScore() + "    Turns " + scoreboard.getNumberOfTurns());
	}
	
	public void displayBoard (Board board) {
		int numberRows = Math.max(board.maxLaneSize(),2);
		for (int row=0; row<numberRows; row++) {
			if (row==0) {
				Deck deck = board.getDeck();
				if (deck.isEmpty()) {
					System.out.print(BLANK);
				} else
					System.out.print(deck.peek());
			} else if (row==1) {
				Stack<Card> pile = board.getPile();
				if (pile.isEmpty())
					System.out.print(BLANK);
				else
					System.out.print(pile.peek());
			} else
				System.out.print(BLANK);
			System.out.print(BLANK);
			for (int laneIndex=0; laneIndex<Board.NUM_LANES; laneIndex++) {
				List<Card> lane = board.getLane(laneIndex);
				if (row > lane.size()-1)
					System.out.print(BLANK);
				else
					System.out.print(lane.get(row));
				System.out.print(BLANK);
			}
			if (row==0) {
				for (int suitIndex=0; suitIndex<Board.NUM_SUITS; suitIndex++) {
					Stack<Card> suit = board.getSuit(suitIndex);
					if (suit.isEmpty())
						System.out.print(BLANK);
					else
						System.out.print(suit.peek());
					System.out.print(BLANK);
				}
			}
			System.out.println();
		}
		System.out.println("P     1     2     3     4     5     6     7     D     H     C     S");
		System.out.println();
	}
	
	public Command getCommand () {
		return command;
	}
	
	public Command getUserInput () {
		boolean commandEntered = false;
		do {
			System.out.print("Enter command: ");
			String input = in.nextLine();
			if (Command.isValid(input)) {
				command = new Command(input);
				commandEntered = true;
			} else
				System.out.println("The command is invalid. Try again.");
		} while (!commandEntered);
		return command;
	}
	
	public void displayCommandNotPossible () {
		System.out.println("That play is not possible. Try again.");
	}
	
	public void displayGameOver () {
		System.out.println("You win.");
		System.out.println("Game over.");
	}
	
	public void displayQuit () {
		System.out.println("Quit.");
	}
}

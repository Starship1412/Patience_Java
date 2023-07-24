package patience;

/**
 * This is the main class for playing the patience game.
 */

public class Patience {
		
	public static void main (String... args) {
		Board board = new Board();
		Score scoreboard = new Score();
		View view = new View();
		Command command;
		view.displayWelcome();
		do {
			view.displayScore(scoreboard);
			view.displayBoard(board);
			boolean commandDone = false;
			do {
				command = view.getUserInput();
				if (command.isDraw()) {
					if (board.drawIsPossible()) {
						board.draw();
						scoreboard.drawPlayed(command);
						commandDone = true;
					} else
						view.displayCommandNotPossible();
				} else if (command.isMove()) {
					if (board.moveIsPossible(command)) {
						board.move(command);
						scoreboard.movePlayed(command,board);
						commandDone = true;
					} else
						view.displayCommandNotPossible();
				} else if (command.isQuit())
					commandDone = true;
			} while (!commandDone);
		} while (!command.isQuit() && !board.isGameOver());
		if (board.isGameOver()) {
			view.displayScore(scoreboard);
			view.displayBoard(board);
			view.displayGameOver();
		} else
			view.displayQuit();
	}
}

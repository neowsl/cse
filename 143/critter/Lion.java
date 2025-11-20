import java.awt.*;
import java.util.*;

public class Lion extends Critter {
	private static final Color[] COLORS = { Color.RED, Color.GREEN, Color.BLUE };
	private Color currentColor;
	private int movesLeft = 0;
	private Random rand = new Random();

	public Lion() {
		pickNewColor();
	}

	private void pickNewColor() {
		currentColor = COLORS[rand.nextInt(COLORS.length)];
		movesLeft = 3; // keep this color for 3 moves
	}

	@Override
	public Color getColor() {
		if (movesLeft == 0)
			pickNewColor();
		movesLeft--;
		return currentColor;
	}

	@Override
	public String toString() {
		return "L";
	}

	@Override
	public Action getMove(CritterInfo info) {
		if (info.getFront() == Neighbor.OTHER)
			return Action.INFECT;
		if (info.getFront() == Neighbor.WALL || info.getRight() == Neighbor.WALL)
			return Action.LEFT;
		if (info.getFront() == Neighbor.SAME)
			return Action.RIGHT;
		return Action.HOP;
	}
}

import java.awt.*;
import java.util.*;

public class Wanderer extends Critter {
	private static final Random rand = new Random();
	private int moveCounter = 0;
	private Direction preferredDirection = Direction.NORTH;

	public Color getColor() {
		return Color.CYAN;
	}

	public String toString() {
		return "W";
	}

	public Action getMove(CritterInfo info) {
		moveCounter++;

		// Change preferred direction every 5 moves
		if (moveCounter % 5 == 0) {
			int choice = rand.nextInt(4);
			switch (choice) {
				case 0:
					preferredDirection = Direction.NORTH;
					break;
				case 1:
					preferredDirection = Direction.EAST;
					break;
				case 2:
					preferredDirection = Direction.SOUTH;
					break;
				case 3:
					preferredDirection = Direction.WEST;
					break;
			}
		}

		// Hop if empty in front
		if (info.getFront() == Neighbor.EMPTY) {
			return Action.HOP;
		}

		// Otherwise try to turn randomly left or right
		if (rand.nextBoolean()) {
			return Action.LEFT;
		} else {
			return Action.RIGHT;
		}
	}
}

import java.awt.*;
import java.util.*;

public class SneakyFox extends Critter {
	private static final Random rand = new Random();
	private boolean lastTurnLeft = true;

	public Color getColor() {
		return Color.ORANGE;
	}

	public String toString() {
		return "F";
	}

	public Action getMove(CritterInfo info) {
		// Avoid combat: hop if front is empty
		if (info.getFront() == Neighbor.EMPTY) {
			return Action.HOP;
		}
		// If blocked or enemy ahead, turn randomly
		if (rand.nextBoolean()) {
			return Action.LEFT;
		} else {
			return Action.RIGHT;
		}
	}
}

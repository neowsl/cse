import java.awt.*;
import java.util.*;

public class KamikazeAnt extends Critter {
	private static final Random rand = new Random();

	public Color getColor() {
		return Color.MAGENTA;
	}

	public String toString() {
		return "A";
	}

	public Action getMove(CritterInfo info) {
		// Aggressively infect if enemy detected in any direction
		if (info.frontThreat() || info.leftThreat() || info.rightThreat() || info.backThreat()) {
			return Action.INFECT;
		}
		// Hop forward if empty
		if (info.getFront() == Neighbor.EMPTY) {
			return Action.HOP;
		}
		// Otherwise turn towards a random direction
		if (rand.nextBoolean()) {
			return Action.LEFT;
		} else {
			return Action.RIGHT;
		}
	}
}

import java.awt.*;

public class Giant extends Critter {
	private static final String[] PHRASES = { "fee", "fie", "foe", "fum" };
	private int moveCounter = 0;
	private int phraseIndex = 0;

	public Giant() {
	}

	@Override
	public Color getColor() {
		return Color.GRAY;
	}

	@Override
	public String toString() {
		String s = PHRASES[phraseIndex];
		moveCounter++;
		if (moveCounter % 6 == 0) {
			phraseIndex = (phraseIndex + 1) % PHRASES.length;
		}
		return s;
	}

	@Override
	public Action getMove(CritterInfo info) {
		if (info.getFront() == Neighbor.OTHER)
			return Action.INFECT;
		if (info.getFront() == Neighbor.EMPTY)
			return Action.HOP;
		return Action.RIGHT;
	}
}

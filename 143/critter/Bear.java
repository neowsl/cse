import java.awt.*;

public class Bear extends Critter {
	private boolean polar;
	private boolean slash = true;

	public Bear(boolean polar) {
		this.polar = polar;
	}

	@Override
	public Color getColor() {
		return polar ? Color.WHITE : Color.BLACK;
	}

	@Override
	public String toString() {
		String s = slash ? "/" : "\\";
		slash = !slash;
		return s;
	}

	@Override
	public Action getMove(CritterInfo info) {
		if (info.getFront() == Neighbor.OTHER)
			return Action.INFECT;
		if (info.getFront() == Neighbor.EMPTY)
			return Action.HOP;
		return Action.LEFT;
	}
}

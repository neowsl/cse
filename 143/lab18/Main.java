public class Main {
	public static boolean explore(Grid g) {
		return explore(g, g.getUnassignedLocation());
	}

	private static boolean explore(Grid g, int cell) {
		if (cell == -1) {
			return true;
		}

		for (int i = 1; i <= 9; i++) {
			if (g.noConflicts(cell, i)) {
				g.place(cell, i);
				if (explore(g, g.getUnassignedLocation())) {
					return true;
				}
				g.remove(cell);
			}
		}

		return false;
	}
}

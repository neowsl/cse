public class Globals {
	public static final int MAX_WEIGHINGS = 3;
	private static int numWeighings = 0;

	public static void incrementWeighings() {
		assert numWeighings < MAX_WEIGHINGS : "Max weighings exceeded";
		numWeighings++;
	}

	public static void resetWeighings() {
		numWeighings = 0;
	}
}

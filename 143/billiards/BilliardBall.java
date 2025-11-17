public class BilliardBall {
	private final int weight;

	public BilliardBall(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		String caller = stack[2].getClassName();
		assert caller == "BilliardBallSet" || caller == "BilliardsTester";

		return weight;
	}

	public String toString() {
		return String.valueOf(this.weight);
	}
}

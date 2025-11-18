import java.util.List;
import java.util.ArrayList;

public class BilliardBallSet implements Comparable<BilliardBallSet> {
	private List<BilliardBall> balls;

	public BilliardBallSet(List<BilliardBall> list) {
		this.balls = list;
	}

	public BilliardBallSet() {
		this.balls = new ArrayList<>();
	}

	public BilliardBallSet(BilliardBall ball) {
		this(List.of(ball));
	}

	public int size() {
		return balls.size();
	}

	public List<BilliardBall> getBalls() {
		return balls;
	}

	public List<BilliardBall> getBalls(int fromIndex, int toIndex) {
		return balls.subList(fromIndex, toIndex);
	}

	public BilliardBall getBall(int index) {
		return balls.get(index);
	}

	public void add(BilliardBall ball) {
		this.balls.add(ball);
	}

	private int weight() {
		return balls.stream().mapToInt(b -> b.getWeight()).sum();
	}

	public int compareTo(BilliardBallSet other) {
		Globals.incrementWeighings();

		return weight() - other.weight();
	}

	public String toString() {
		return balls.toString();
	}
}

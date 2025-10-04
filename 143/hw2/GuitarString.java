import java.util.*;

public class GuitarString {
	private Queue<Double> ringBuffer;

	public static double MIN_DISPLACEMENT = -0.5;
	public static double MAX_DISPLACEMENT = 0.5;
	public static double ENERGY_DECAY_FACTOR = 0.996;

	public GuitarString(double frequency) {
		if (frequency <= 0) {
			throw new IllegalArgumentException("frequency must be positive");
		}

		long N = Math.round(StdAudio.SAMPLE_RATE / frequency);
		if (N < 2) {
			throw new IllegalArgumentException("ring buffer too small");
		}

		ringBuffer = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			ringBuffer.add(0.0);
		}
	}

	public GuitarString(double[] init) {
		ringBuffer = new LinkedList<>();
		for (double x : init) {
			ringBuffer.add(x);
		}
	}

	public void pluck() {
		int N = ringBuffer.size();
		ringBuffer.clear();

		Random r = new Random();
		for (int i = 0; i < N; i++) {
			double displacement = MIN_DISPLACEMENT + r.nextDouble() * (MAX_DISPLACEMENT - MIN_DISPLACEMENT);
			ringBuffer.add(displacement);
		}
	}

	public void tic() {
		double first = ringBuffer.remove();
		double second = ringBuffer.peek();

		double nextDisplacement = (first + second) * 0.5 * ENERGY_DECAY_FACTOR;
		ringBuffer.add(nextDisplacement);
	}

	public double sample() {
		return ringBuffer.peek();
	}
}

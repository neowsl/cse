import java.util.*;

/**
 * The {@link GuitarString} class is a guitar string synthesizer. It provides
 * methods to pluck the string, get the current sample, and step through the
 * simulation.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 2
 * @date 2025-10-08
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section BA
 */
public class GuitarString {
	// a ring buffer of size `StdAudio.SAMPLE_RATE / frequency` that stores the
	// displacement of the string at equal intervals of time
	private Queue<Double> ringBuffer;

	/**
	 * The minimum displacement when exciting the string.
	 */
	public static final double MIN_DISPLACEMENT = -0.5;
	/**
	 * The maximum displacement when exciting the string.
	 */
	public static final double MAX_DISPLACEMENT = 0.5;
	/**
	 * The energy dissipation multiplier as the wave traverses the string.
	 */
	public static final double ENERGY_DECAY_FACTOR = 0.996;

	/**
	 * Constructs a new {@link GuitarString} with frequency {@code frequency}.
	 * <p>
	 * Ensures that the ring buffer size is {@link StdAudio#SAMPLE_RATE} divided by
	 * {@code frequency} (rounded to the nearest integer).
	 * <p>
	 * Ensures that the string has zero energy (the ring buffer is all zeroes).
	 *
	 * @param frequency the fundamental frequency of the string.
	 * @throws IllegalArgumentException if {@code frequency} is nonpositive.
	 * @throws IllegalArgumentException if a string with frequency {@code frequency}
	 *                                  requires a ring buffer with size < 2.
	 */
	public GuitarString(double frequency) {
		if (frequency <= 0) {
			throw new IllegalArgumentException("frequency must be positive");
		}

		// N = length of ringBuffer
		long N = Math.round((double) StdAudio.SAMPLE_RATE / frequency);
		if (N < 2) {
			// N can't be < 2 since Karpus-Strong requires at least 2 values
			throw new IllegalArgumentException("ring buffer too small");
		}

		ringBuffer = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			ringBuffer.add(0.0);
		}
	}

	/**
	 * Constructs a new {@link GuitarString}, using the values in {@code init} to
	 * initialize the ring buffer.
	 * <p>
	 * Ensures that the ring buffer values match the values in {@code init}.
	 *
	 * @param init the values to initialize the ring buffer with.
	 * @throws IllegalArgumentException if {@code init.length < 2}.
	 * @apiNote This method is intended for testing purposes only.
	 */
	public GuitarString(double[] init) {
		if (init.length < 2) {
			throw new IllegalArgumentException("init array is too small");
		}

		ringBuffer = new LinkedList<>();
		for (double x : init) {
			ringBuffer.add(x);
		}
	}

	/**
	 * Plucks the string. This simulates the initial excitation of the string.
	 * <p>
	 * Ensures that the size of the ring buffer remains constant, and that it is
	 * filled with random displacement values between {@link #MIN_DISPLACEMENT}
	 * (inclusive) and {@link #MAX_DISPLACEMENT} (exclusive).
	 */
	public void pluck() {
		int N = ringBuffer.size();
		// clear ringBuffer first so we don't end up with 2N elements
		ringBuffer.clear();

		Random r = new Random();
		for (int i = 0; i < N; i++) {
			double displacement = MIN_DISPLACEMENT + r.nextDouble() * (MAX_DISPLACEMENT - MIN_DISPLACEMENT);
			ringBuffer.add(displacement);
		}
	}

	/**
	 * Applies the Karplus-Strong algorithm once using {@link #ENERGY_DECAY_FACTOR}.
	 * <p>
	 * Ensures that the size of the ring buffer remains constant, and that the last
	 * element is the average of the first two elements of the ring buffer
	 * multiplied by {@link #ENERGY_DECAY_FACTOR}.
	 * 
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Karplus%E2%80%93Strong_string_synthesis">
	 *      Karplus-Strong string synthesis
	 *      </a>
	 */
	public void tic() {
		double first = ringBuffer.remove();
		double second = ringBuffer.peek();

		double nextDisplacement = (first + second) * 0.5 * ENERGY_DECAY_FACTOR;
		ringBuffer.add(nextDisplacement);
	}

	/**
	 * Gets the current sample of the string.
	 *
	 * @return the value at the front of the ring buffer.
	 */
	public double sample() {
		return ringBuffer.peek();
	}
}

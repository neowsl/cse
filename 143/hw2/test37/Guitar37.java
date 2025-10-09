/**
 * The {@link Guitar37} class is a guitar synthesizer with 37 strings. It
 * provides methods to pluck strings, get the current sample, and step through
 * the simulation. {@link Guitar37} also defines a keyboard-to-string map,
 * {@link #KEYBOARD}, which makes it easier to be played on a computer keyboard
 * using a piano-like layout.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 2
 * @date 2025-10-08
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section BA
 */
public class Guitar37 implements Guitar {
	/**
	 * A keyboard-to-string map, where the {@code i}th character corresponds to the
	 * {@code i}th string. Note that concert A will be centered at
	 * {@code i = CONCERT_A_INDEX}.
	 */
	public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
	/**
	 * The total number of strings on the guitar.
	 */
	public static final int NUM_STRINGS = KEYBOARD.length();
	/**
	 * The index of the string that corresponds to concert A.
	 */
	public static final int CONCERT_A_INDEX = 24;

	private GuitarString[] strings;
	// the time in ticks (i.e. the number of times `tic()` has been called)
	private int time = 0;

	/**
	 * Constructs a new {@link Guitar37} with 37 strings. Initializes the strings in
	 * an ascending scale, with concert A centered at index
	 * {@link #CONCERT_A_INDEX}.
	 */
	public Guitar37() {
		strings = new GuitarString[NUM_STRINGS];
		for (int i = 0; i < NUM_STRINGS; i++) {
			double frequency = 440 * Math.pow(2, (i - CONCERT_A_INDEX) / 12.0);
			strings[i] = new GuitarString(frequency);
		}
	}

	/**
	 * Plucks the string with pitch {@code pitch}.
	 *
	 * @param pitch the pitch of the string to pluck, where {@code pitch = 0}
	 *              corresponds to concert A. Negative values are acceptable.
	 * @apiNote If {@code pitch} does not correspond with a string on the guitar, no
	 *          string is plucked.
	 * @see GuitarString#pluck()
	 */
	public void playNote(int pitch) {
		// concert A is at i = CONCERT_A_INDEX
		int index = pitch + CONCERT_A_INDEX;
		if (index >= 0 && index < NUM_STRINGS) {
			strings[index].pluck();
		}
	}

	/**
	 * Checks whether or not {@code key} corresponds with a string on the guitar.
	 *
	 * @param key the key to check.
	 * @return {@code true} if {@code key} corresponds with a string on the guitar,
	 *         {@code false} otherwise.
	 */
	public boolean hasString(char key) {
		return KEYBOARD.indexOf(key) != -1;
	}

	/**
	 * Plucks the string corresponding to key {@code key}.
	 *
	 * @param key the key corresponding to the string to pluck.
	 * @throws IllegalArgumentException if {@code key} does not correspond to a
	 *                                  string.
	 * @see GuitarString#pluck()
	 */
	public void pluck(char key) {
		int index = KEYBOARD.indexOf(key);
		if (index == -1) {
			throw new IllegalArgumentException("key does not correspond to a string");
		}

		strings[index].pluck();
	}

	/**
	 * Gets the current sample of the entire guitar.
	 *
	 * @return the sum of all the samples of the strings on the guitar.
	 * @see GuitarString#sample()
	 */
	public double sample() {
		double res = 0;
		for (GuitarString s : strings) {
			res += s.sample();
		}

		return res;
	}

	/**
	 * Calls {@link GuitarString#tic()} for every string.
	 * <p>
	 * Ensures that {@code time} is incremented by 1.
	 */
	public void tic() {
		for (GuitarString s : strings) {
			s.tic();
		}

		time++;
	}

	/**
	 * Gets the current time (in ticks).
	 *
	 * @return the current time in ticks.
	 */
	public int time() {
		return time;
	}
}

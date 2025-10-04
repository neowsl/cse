public class Guitar37 implements Guitar {
	public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
	public static final int NUM_STRINGS = KEYBOARD.length();

	private GuitarString[] strings;
	private int time = 0;

	public Guitar37() {
		strings = new GuitarString[NUM_STRINGS];
		for (int i = 0; i < NUM_STRINGS; i++) {
			double frequency = 440 * Math.pow(2, (i - 24) / 12.0);
			strings[i] = new GuitarString(frequency);
		}
	}

	public void playNote(int pitch) {
		// concert A is at i = 24
		int index = pitch + 24;
		if (index < 0 || index >= NUM_STRINGS) {
			return;
		}

		strings[index].pluck();
	}

	public boolean hasString(char key) {
		return KEYBOARD.indexOf(key) != -1;
	}

	public void pluck(char key) {
		int index = KEYBOARD.indexOf(key);
		if (index == -1) {
			throw new IllegalArgumentException("key is not designed to be played");
		}

		strings[index].pluck();
	}

	public double sample() {
		double res = 0;
		for (GuitarString s : strings) {
			res += s.sample();
		}

		return res;
	}

	public void tic() {
		for (GuitarString s : strings) {
			s.tic();
		}

		time++;
	}

	public int time() {
		return time;
	}
}

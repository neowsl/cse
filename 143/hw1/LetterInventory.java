/**
 * Keep track of an inventory of letters in the alphabet.
 *
 * @author Neal Wang
 */
public class LetterInventory {
	// a hash map that stores the frequency of each letter
	private int[] map;
	// a cached value of the sum of all letter counts in the inventory
	private int size;

	/*
	 * The number of unique letters to store counts for.
	 */
	public static final int CAPACITY = 26;

	/**
	 * Constructs a new {@code LetterInventory} from the given string.
	 * <p>
	 * <b>Note:</b> Non-alphabetic characters in {@code data} will be ignored. They
	 * will also not contribute to the {@code size} of the inventory.
	 *
	 * @param data the string to initialise the inventory with.
	 */
	public LetterInventory(String data) {
		map = new int[CAPACITY];
		size = 0;

		for (char letter : data.toCharArray()) {
			try {
				map[hash(letter)]++;
				// `size` isn't necessarily the same as `data.length()` if chars are ignored
				size++;
			} catch (IllegalArgumentException e) {
				// `letter` was not alphabetic; ignore according to spec
				continue;
			}
		}
	}

	/**
	 * Constructs a new empty {@code LetterInventory}.
	 */
	private LetterInventory() {
		this("");
	}

	/**
	 * Gets the count of how many of {@code letter} exist in the inventory.
	 * 
	 * @param letter the lowercase or uppercase alphabetic character count.
	 * @return the count of how many of {@code letter} exist in the inventory.
	 * @throws IllegalArgumentException if {@code letter} is nonalphabetic.
	 */
	public int get(char letter) {
		if (!Character.isAlphabetic(letter)) {
			throw new IllegalArgumentException("letter must be alphabetic");
		}

		return map[hash(letter)];
	}

	/**
	 * Sets the count of {@code letter} in the inventory.
	 * 
	 * @param letter the lowercase or uppercase alphabetic character to set the
	 *               count of.
	 * @param value  the new count of {@code letter}.
	 * @throws IllegalArgumentException if {@code letter} is nonalphabetic.
	 * @throws IllegalArgumentException if {@code value} is negative.
	 */
	public void set(char letter, int value) {
		if (!Character.isAlphabetic(letter)) {
			throw new IllegalArgumentException("letter must be alphabetic");
		}
		if (value < 0) {
			throw new IllegalArgumentException("value must be nonnegative");
		}

		// first decrease `size` by the current count of `letter`,
		size -= get(letter);
		// then increase `size` by the future count of `letter`
		size += value;

		map[hash(letter)] = value;
	}

	/**
	 * Gets the sum of all letter counts in the inventory
	 */
	public int size() {
		return size;
	}

	/**
	 * Gets whether or not the inventory is empty.
	 *
	 * @return {@code true} if the inventory is empty (all counts are 0),
	 *         {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Converts the inventory into a {@code String} representation. The letters will
	 * be in lowercase, in sorted order, and surrounded by square brackets. The
	 * number of occurrences of each letter will match its count in the inventory.
	 *
	 * @returns the string representation of the inventory.
	 */
	public String toString() {
		// use `StringBuilder` to avoid excessive copying
		StringBuilder sb = new StringBuilder("[");

		for (int i = 0; i < CAPACITY; i++) {
			// ignore 0-count letters
			if (map[i] == 0) {
				continue;
			}

			// convert `letter` to a char, then repeat by the count of `letter`
			String repeated = Character.toString(unhash(i)).repeat(map[i]);
			sb.append(repeated);
		}

		sb.append("]");

		return sb.toString();
	}

	/**
	 * Computes the hash of a letter. Uppercase letters will be converted to
	 * lowercase. The resulting hash value will be between 0 and 25.
	 * 
	 * @param letter the lowercase or uppercase alphabetic character to be hashed.
	 * @return a unique hash value between 0 and 25 based on {@code letter}.
	 * @throws IllegalArgumentException if {@code letter} is nonalphabetic.
	 */
	private int hash(char letter) {
		if (!Character.isAlphabetic(letter)) {
			throw new IllegalArgumentException("letter must be alphabetic");
		}

		return Character.toLowerCase(letter) - 'a';
	}

	/**
	 * Computes the letter from a hash. This is an inverse function of {@code hash}.
	 * I.e. given a lowercase letter {@code letter},
	 * {@code unhash(hash(letter)) = letter}.
	 * 
	 * @param hashed the hashed value to unhash.
	 * @return the unhashed value of {@code hashed}.
	 * @throws IllegalArgumentException if {@code hashed} is < 0 or > 25.
	 */
	private char unhash(int hashed) {
		// used `25` instead of `CAPACITY` because unhash should work on any character,
		// regardless of `CAPACITY`
		if (hashed < 0 || hashed > 25) {
			throw new IllegalArgumentException("hashed must be between 0 and 25");
		}

		return (char) (hashed + 'a');
	}

	/**
	 * Constructs and returns a new {@code LetterInventory} that represents the sum
	 * of this {@code LetterInventory} and the other {@code LetterInventory}. Each
	 * {@code letter} count will be the sum of the respective {@code letter} counts
	 * of {@code this} and {@code other}.
	 *
	 * @param other the other {@code LetterInventory} to add.
	 * @return a new {@code LetterInventory} representing {@code this + other}.
	 */
	LetterInventory add(LetterInventory other) {
		LetterInventory res = new LetterInventory();

		for (int i = 0; i < CAPACITY; ++i) {
			// convert `i` back to a `char`
			char letter = unhash(i);
			res.set(letter, get(letter) + other.get(letter));
		}

		return res;
	}

	/**
	 * Constructs and returns a new {@code LetterInventory} that represents the
	 * difference of this {@code LetterInventory} and the other
	 * {@code LetterInventory}.
	 * <p>
	 * <b>Note:</b> Counts will be computed as {@code this - other}, not
	 * {@code other - this}.
	 *
	 * @param other the other {@code LetterInventory} to subtract.
	 * @return a new {@code LetterInventory} representing {@code this - other}, or
	 *         {@code null} if any resulting count is negative.
	 */
	LetterInventory subtract(LetterInventory other) {
		LetterInventory res = new LetterInventory();

		for (int i = 0; i < CAPACITY; ++i) {
			// convert `i` back to a `char`
			char letter = unhash(i);

			int count = get(letter) - other.get(letter);
			if (count < 0) {
				return null;
			}

			res.set(letter, count);
		}

		return res;
	}
}

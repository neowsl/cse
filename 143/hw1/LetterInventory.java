/**
 * The {@code LetterInventory} class keeps track of an inventory of alphabetic
 * letters. It provides methods such as {@code get}, {@code set}, {@code add},
 * and {@code subtract} to manipulate the internal data. {@code ListInventory}
 * is designed to manipulate the inventory efficiently and provide easy access
 * to letter data.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @date 2025-09-30
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section BA
 */
public class LetterInventory {
	// a hash map that stores the frequency of each letter
	private int[] letterMap;
	// a cached value of the sum of all letter counts in the inventory
	private int size;

	/*
	 * The number of unique letters to store counts for.
	 */
	public static final int ALPHABET_LENGTH = 26;

	/**
	 * Constructs a new {@code LetterInventory} from the given string.
	 * <p>
	 * <b>Note:</b> Non-alphabetic characters in {@code data} will be ignored. They
	 * will also not contribute to the {@code size} of the inventory.
	 *
	 * @param data the string to initialise the inventory with.
	 */
	public LetterInventory(String data) {
		letterMap = new int[ALPHABET_LENGTH];
		size = 0;

		// ideally would use `data.toCharArray()` + `Iterable`
		for (int i = 0; i < data.length(); i++) {
			char letter = data.charAt(i);
			// ignore non-alphabetic letters
			if (Character.isAlphabetic(letter)) {
				letterMap[hash(letter)]++;
				// `size` isn't necessarily the same as `data.length()` if chars are ignored
				size++;
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
	 * @param letter the lowercase or uppercase alphabetic character to get the
	 *               count of.
	 * @return the count of how many of {@code letter} exist in the inventory.
	 * @throws IllegalArgumentException if {@code letter} is nonalphabetic.
	 */
	public int get(char letter) {
		if (!Character.isAlphabetic(letter)) {
			throw new IllegalArgumentException("letter must be alphabetic");
		}

		return letterMap[hash(letter)];
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

		letterMap[hash(letter)] = value;
	}

	/**
	 * Gets the sum of all letter counts in the inventory
	 *
	 * @return the sum of all letter counts in the inventory.
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
	 * @return the string representation of the inventory.
	 */
	public String toString() {
		// ideally would use a `StringBuilder`
		String res = "[";

		for (int i = 0; i < ALPHABET_LENGTH; i++) {
			// ignore 0-count letters
			if (letterMap[i] >= 0) {
				char letter = unhash(i);
				for (int j = 0; j < letterMap[i]; j++) {
					res += letter;
				}
			}
		}

		return res + "]";
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
	public LetterInventory add(LetterInventory other) {
		LetterInventory res = new LetterInventory();

		for (int i = 0; i < ALPHABET_LENGTH; ++i) {
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
	public LetterInventory subtract(LetterInventory other) {
		LetterInventory res = new LetterInventory();

		for (int i = 0; i < ALPHABET_LENGTH; ++i) {
			// convert `i` back to a `char`
			char letter = unhash(i);

			int count = get(letter) - other.get(letter);
			if (count < 0) {
				// return `null` if count is negative
				return null;
			}

			res.set(letter, count);
		}

		return res;
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
	 * <p>
	 * I.e. given a lowercase letter {@code letter},
	 * {@code unhash(hash(letter)) = letter}.
	 * 
	 * @param hashed the hashed value to unhash.
	 * @return the unhashed value of {@code hashed}.
	 * @throws IllegalArgumentException if {@code hashed} is < 0 or > 25.
	 */
	private char unhash(int hashed) {
		if (hashed < 0 || hashed >= ALPHABET_LENGTH) {
			throw new IllegalArgumentException("hashed must be between 0 and 25");
		}

		return (char) (hashed + 'a');
	}
}

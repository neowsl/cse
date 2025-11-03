import java.util.*;

/**
 * The {@link HangmanManager} class manages an "evil" game of hangman. All the
 * rules of standard Hangman apply, though {@link HangmanManager} intentionally
 * delays picking a word until it is forced to. It provides methods to display
 * the current pattern and record guesses.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 4
 * @date 2025-10-21
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section AB
 */
public class HangmanManager {
	/**
	 * All the words that are possible within {@link #currPattern}.
	 */
	private Set<String> remainingWords;
	/**
	 * The number of guesses the player has left.
	 */
	private int remainingGuesses;
	/**
	 * All the characters the player has guessed so far.
	 */
	private Set<Character> guessedChars;
	/**
	 * The current pattern which will be displayed to the player.
	 */
	private String currPattern;

	/**
	 * Constructs a new {@link HangmanManager} using only the words in
	 * {@code dictionary} that have length {@code length}. Also initializes the
	 * maximum number of guesses the player has.
	 * <p>
	 * Requires: All strings in dictionary are lowercase and nonempty.
	 *
	 * @param dictionary the collection of words to start with.
	 * @param length     the length of the target word.
	 * @param max        the maximum number of guesses the player has.
	 * @throws IllegalArgumentException if {@code length} < 1.
	 * @throws IllegalArgumentException if {@code max} < 0.
	 */
	public HangmanManager(Collection<String> dictionary, int length, int max) {
		if (length < 1) {
			throw new IllegalArgumentException("length must be >= 1 (length = " + length + ")");
		}

		if (max < 0) {
			throw new IllegalArgumentException("max must be >= 0 (max = " + max + ")");
		}

		remainingWords = new TreeSet<>();
		for (String word : dictionary) {
			if (word.length() == length) {
				remainingWords.add(word);
			}
		}

		remainingGuesses = max;
		guessedChars = new TreeSet<>();

		currPattern = "";
		for (int i = 0; i < length; i++) {
			// notice that pattern will be "----" rather than "- - - -" (no spaces)
			// this makes `currPattern` easier to work with
			// spaces will be added when `pattern()` is called
			currPattern += "-";
		}
	}

	/**
	 * Gets the current set of words being considered by the hangman manager.
	 * <p>
	 * Note that an internal reference is returned, not a defensive copy.
	 *
	 * @return the current set of words being considered.
	 */
	public Set<String> words() {
		// as per spec, do not create defensive copy
		return remainingWords;
	}

	/**
	 * Gets how many guesses the player has left.
	 *
	 * @return the number of guesses left.
	 */
	public int guessesLeft() {
		return remainingGuesses;
	}

	/**
	 * Gets the current set of letters that have been guessed by the player.
	 * <p>
	 * Note that an internal reference is returned, not a defensive copy.
	 *
	 * @return the current set of letters that have been guessed.
	 */
	public Set<Character> guesses() {
		// as per spec, do not create defensive copy
		return guessedChars;
	}

	/**
	 * Gets the current pattern in accordance to the letters guessed. Characters
	 * are space-separated, with a dash masking letters that have not been guessed
	 * yet.
	 *
	 * @return the current pattern.
	 * @throws IllegalStateException if {@link #words()} is empty.
	 */
	public String pattern() {
		if (words().isEmpty()) {
			throw new IllegalStateException("words() must not be empty");
		}

		String res = Character.toString(currPattern.charAt(0));
		for (int i = 1; i < currPattern.length(); i++) {
			res += " " + currPattern.charAt(i);
		}

		return res;
	}

	/**
	 * Records a player's guess, selecting the pattern of the optimal set of words
	 * (the largest word family) to proceed with and returning the number of
	 * occurrences of {@code guess} in the new optimal pattern.
	 * <p>
	 * In the event of a tie in the word family sizes, the alphabetically-first
	 * pattern is used.
	 * <p>
	 * Ensures:
	 * <ul>
	 * <li>{@code guess} is added to {@link #guesses()}.</li>
	 * <li>{@link #pattern()} is updated to the optimal pattern (the pattern that
	 * preserves the largest family of words).</li>
	 * <li>{@link #words()} is updated to the set of all words that match
	 * {@link #pattern()}.</li>
	 * <li>{@link #guessesLeft()} is decremented by 1 if {@code guess} does not
	 * appear in the new {@link #pattern()}.</li>
	 * </ul>
	 *
	 * @param guess the player's guess.
	 * @return the number of occurrences of {@code guess} in the new pattern.
	 * @throws IllegalStateException    if {@link #guessesLeft()} < 1 or if
	 *                                  {@link #words()} is empty.
	 * @throws IllegalArgumentException if {@code guess} has already been guessed.
	 */
	public int record(char guess) {
		if (guessesLeft() < 1 || words().isEmpty()) {
			throw new IllegalStateException("guessesLeft() must be >= 1 and words() must not be empty");
		}

		if (guesses().contains(guess)) {
			throw new IllegalArgumentException("guess must not be previously guessed");
		}

		guessedChars.add(guess);

		Map<String, Set<String>> families = makeFamilies(remainingWords, guessedChars);
		currPattern = findBestPattern(families);

		// update remaining words to family of best pattern
		remainingWords = families.get(currPattern);

		int count = 0;
		for (int i = 0; i < currPattern.length(); i++) {
			if (currPattern.charAt(i) == guess) {
				count++;
			}
		}

		if (count == 0) {
			// only decrement `remainingGuesses` if guess was incorrect
			remainingGuesses--;
		}

		return count;
	}

	/**
	 * Makes a map of patterns to word families using {@code words}, only
	 * considering characters in {@code visibleChars}. Any word inside a family is
	 * guaranteed to fit that family's pattern.
	 *
	 * @param words        the set of words to sort into families.
	 * @param visibleChars the set of characters to consider (i.e. the families
	 *                     ignore non-visible characters).
	 * @return a map of patterns to words.
	 */
	private static Map<String, Set<String>> makeFamilies(Set<String> words, Set<Character> visibleChars) {
		Map<String, Set<String>> families = new TreeMap<>();
		for (String word : words) {
			String pattern = wordIntoPattern(word, visibleChars);

			if (!families.containsKey(pattern)) {
				families.put(pattern, new TreeSet<>());
			}
			families.get(pattern).add(word);
		}

		return families;
	}

	/**
	 * Finds the pattern containing the most words, given a map of patterns to word
	 * families {@code families}.
	 * <p>
	 * In the event of a tie in the word family sizes, the alphabetically-first
	 * pattern is returned.
	 *
	 * @param families a map of patterns to word families (hint: can be generated
	 *                 using {@link #makeFamilies()}).
	 * @return the pattern of the largest word family.
	 */
	private static String findBestPattern(Map<String, Set<String>> families) {
		String res = "";
		int bestSize = 0;
		for (String key : families.keySet()) {
			int size = families.get(key).size();
			if (size > bestSize) {
				bestSize = size;
				res = key;
			}
		}

		return res;
	}

	/**
	 * Converts a word into a pattern. The converted pattern masks non-visible
	 * characters with dashes. Note that the converted pattern does not add spaces.
	 *
	 * @param word         the word to convert.
	 * @param visibleChars the set of visible characters.
	 * @return the converted pattern.
	 */
	private static String wordIntoPattern(String word, Set<Character> visibleChars) {
		String res = "";
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			res += visibleChars.contains(c) ? c : "-";
		}

		return res;
	}
}

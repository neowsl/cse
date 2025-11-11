import java.util.*;

/**
 * The {@link AnagramSolver} class allows for the discovery of anagrams. It can
 * be constructed with a dictionary, and provides a method {@link #print()} to
 * print anagrams of a given word.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 5
 * @date 2025-11-13
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section AB
 */
public class AnagramSolver {
	/**
	 * A reference to the original dictionary to maintain print order.
	 */
	private final List<String> dictionary;
	/**
	 * A precomputed map of words to {@link LetterInventory}s to make future
	 * computations more efficient.
	 */
	private final Map<String, LetterInventory> inventories;

	/**
	 * Constructs a new {@link AnagramSolver} using {@code list} as the
	 * dictionary.
	 * <p>
	 * Requires: {@code list} is a nonempty list of unique, nonempty strings
	 * that doesn't change in state as the program executes.
	 * <p>
	 * Ensures: {@code list} is not modified.
	 */
	public AnagramSolver(List<String> list) {
		dictionary = list;
		inventories = new HashMap<>();

		// preprocess the dictionary by computing `LetterInventory`s in advance
		for (String word : list) {
			inventories.put(word, new LetterInventory(word));
		}
	}

	/**
	 * Prints all combinations of words from the dictionray that are anagrams of
	 * {@code s} to {@link System#out}, one per line. Ignores combinations that
	 * include more than {@code max} words (or an unlimited number of words if
	 * {@code max = 0}).
	 *
	 * @param s   the word to print anagrams of.
	 * @param max the maximum size of the anagram combinations (unlimited if 0).
	 * @throws IllegalArgumentException if {@code max < 0}.
	 */
	public void print(String s, int max) {
		if (max < 0) {
			throw new IllegalArgumentException("max must be >= 0 (max = "
					+ max + ")");
		}

		LetterInventory reference = new LetterInventory(s);
		List<String> prunedDictionary = new ArrayList<>();
		for (String word : dictionary) {
			if (reference.subtract(inventories.get(word)) != null) {
				prunedDictionary.add(word);
			}
		}

		// if `max` = 0, then we can use unlimited words (i.e. the size of the
		// entire inventory)
		print(reference, prunedDictionary, new Stack<>(),
				max > 0 ? max : inventories.size());
	}

	/**
	 * Prints all combinations of words from {@code dictionary} that are
	 * anagrams of {@code current}. Ignores combinations that include more than
	 * {@code remaining} words.
	 *
	 * @param current    the current word to consider anagrams of.
	 * @param dictionary the words to consider in the anagrams.
	 * @param trace      the words that led up to the current state.
	 * @param remaining  the maximum number of words remaining that can be
	 *                   appended to create an anagram.
	 */
	private void print(LetterInventory current, List<String> dictionary,
			Stack<String> trace, int remaining) {
		if (current.isEmpty()) {
			System.out.println(trace);
		} else if (remaining > 0) {
			for (String word : dictionary) {
				LetterInventory li = inventories.get(word);
				LetterInventory difference = current.subtract(li);
				if (difference != null) {
					trace.push(word);
					print(difference, dictionary, trace, remaining - 1);
					trace.pop();
				}
			}
		}
	}
}

import java.util.*;

/**
 * An even more evil Hangman Manager that extends {@link HangmanManager}.
 * <p>
 * Differences from the base class:
 * <ul>
 * <li>If the player has only one guess left and any remaining word does not
 * contain the guessed letter, the manager immediately selects that word,
 * ensuring an instant loss.</li>
 * <li>{@link #words()} and {@link #guesses()} return unmodifiable views of the
 * superclass’s internal sets, updated only when those sets are replaced,
 * ensuring efficiency.</li>
 * </ul>
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 4 Bonus
 * @date 2025-11-14
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section AB
 */
public class HangmanManager2 extends HangmanManager {
	/**
	 * An unmodifiable view of {@link HangmanManager#words()}.
	 */
	private Set<String> unmodifiableWords;
	/**
	 * An unmodifiable view of {@link HangmanManager#guesses()}.
	 */
	private Set<Character> unmodifiableGuesses;

	public HangmanManager2(Collection<String> dictionary, int length, int max) {
		super(dictionary, length, max);

		// create unmodifiable views of superclass's internal sets
		unmodifiableWords = Collections.unmodifiableSet(super.words());
		unmodifiableGuesses = Collections.unmodifiableSet(super.guesses());
	}

	@Override
	public int record(char guess) {
		// keep track of an original superclass references to detect changes
		Set<String> ogWordsRef = super.words();
		Set<Character> ogGuessesRef = super.guesses();

		// more evil: force a loss if only 1 guess left
		if (guessesLeft() == 1) {
			// search for a word that doesn't contain `guess`
			String oneWord = "";
			for (String word : words()) {
				if (oneWord.isEmpty() && word.indexOf(guess) == -1) {
					oneWord = word;
				}
			}

			if (!oneWord.isEmpty()) {
				// if a word that doesn't contain `guess` exists,
				// make that the only word possible
				super.words().clear();
				super.words().add(oneWord);
			}
		}

		int res = super.record(guess);

		// if either references have changed, update the unmodifiable version
		if (super.words() != ogWordsRef) {
			unmodifiableWords = Collections.unmodifiableSet(super.words());
		}
		if (super.guesses() != ogGuessesRef) {
			unmodifiableGuesses = Collections.unmodifiableSet(super.guesses());
		}

		return res;
	}

	@Override
	public Set<String> words() {
		return unmodifiableWords;
	}

	@Override
	public Set<Character> guesses() {
		return unmodifiableGuesses;
	}
}

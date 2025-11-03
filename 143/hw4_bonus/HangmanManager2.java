import java.util.*;

public class HangmanManager2 extends HangmanManager {
	private Set<String> unmodifiableWords;
	private Set<Character> unmodifiableGuesses;

	public HangmanManager2(Collection<String> dictionary, int length, int max) {
		super(dictionary, length, max);

		// create unmodifiable "defensive" copies of `words` and ``
		unmodifiableWords = Collections.unmodifiableSet(super.words());
		unmodifiableGuesses = Collections.unmodifiableSet(super.guesses());
	}

	@Override
	public int record(char guess) {
		// keep track of an original reference for `words` and `guesses`
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

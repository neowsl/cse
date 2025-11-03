import java.util.*;

/**
 * The {@link GrammarSolver} class manipulates a Backus-Naur Form (BNF) grammar.
 * It provides methods to randomly derive elements of the grammar from given
 * symbols.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 5
 * @date 2025-10-30
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section AB
 */
public class GrammarSolver {
	/**
	 * A sorted mapping from nonterminals to their corresponding production
	 * rules. The values are 2-D lists: the outer list contains rules, while the
	 * inner list contains the symbols for each rule.
	 * <p>
	 * Uses a {@link List} instead of a {@link Set} for the outer layer since
	 * there may be duplicate rules.
	 */
	private SortedMap<String, List<List<String>>> grammar;

	/**
	 * A random number generator used in {@link #generateRec()} to select random
	 * rules.
	 */
	private final Random random;

	/**
	 * Constructs a new {@link GrammarSolver} using the rules provided in
	 * {@code grammar}.
	 * <p>
	 * Requires:
	 * <ul>
	 * <li>{@code grammar} is in Backus-Naur Form (BNF), with each line as an
	 * entry in the list</li>
	 * <li>Each entry in {@code grammar} begins with a nonterminal, followed
	 * immediately by ::=</li>
	 * <li>The right-hand side of ::= is nonempty</li>
	 * </ul>
	 * <p>
	 * Note: Terminal strings are not required to be surrounded by parentheses.
	 *
	 * @param grammar a list of strings, each representing a BNF rule line in
	 *                the format <nonterminal>::=...
	 * @throws IllegalArgumentException if {@code grammar} is empty.
	 * @throws IllegalArgumentException if {@code grammar} contains duplicate
	 *                                  nonterminal definitions.
	 */
	public GrammarSolver(List<String> grammar) {
		if (grammar.isEmpty()) {
			throw new IllegalArgumentException("grammar must not be empty");
		}

		this.grammar = new TreeMap<>();
		for (String entry : grammar) {
			// limit `split()` to 2 in case ::= appears in the rules
			String[] split = entry.split("::=", 2);
			String symbol = split[0];
			String rawRules = split[1];

			if (grammarContains(symbol)) {
				throw new IllegalArgumentException(
						"grammar must not contain duplicate nonterminal definitions (symbol = "
								+ symbol + ")");
			}

			List<List<String>> rules = new ArrayList<>();
			for (String rawRule : rawRules.split("[|]")) {
				// can't simply convert `String[]` to `List<String>`
				// so manually create an `ArrayList<String>`
				List<String> rule = new ArrayList<>();
				// `trim()` to handle leading and trailing whitespace
				for (String ruleSymbol : rawRule.trim().split("[ \t]+")) {
					rule.add(ruleSymbol);
				}
				rules.add(rule);
			}
			this.grammar.put(symbol, rules);
		}

		random = new Random();
	}

	/**
	 * Checks whether or not {@code symbol} is a nonterminal of the
	 * {@link #grammar}.
	 *
	 * @return {@code true} if {@code symbol} is a nonterminal of the
	 *         {@link #grammar}, {@code false} otherwise.
	 */
	public boolean grammarContains(String symbol) {
		return grammar.containsKey(symbol);
	}

	/**
	 * Derives a {@code times}-length array of random strings of terminal
	 * symbols from nonterminal {@code symbol}. May include empty strings if a
	 * rule of {@code symbol} contains no symbols.
	 * <p>
	 * Requires: There are no empty lists in the rules (values) of the
	 * {@link #grammar}. This is already satisfied if the constructor succeeded.
	 *
	 * @param symbol the nonterminal symbol to begin the derivations from.
	 * @param times  the number of derivations to generate.
	 * @return a {@code times}-length array of random derivations from the given
	 *         symbol.
	 * @throws IllegalArgumentException if {@code times} < 0.
	 * @throws IllegalArgumentException if {@code symbol} is not contained in the
	 *                                  {@link #grammar}.
	 */
	public String[] generate(String symbol, int times) {
		if (times < 0) {
			throw new IllegalArgumentException(
					"times must not be < 0 (times = " + times + ")");
		}
		if (!grammarContains(symbol)) {
			throw new IllegalArgumentException(
					"symbol must be contained in the grammar (symbol = " + symbol + ")");
		}

		String[] res = new String[times];
		for (int i = 0; i < times; i++) {
			String derivation = generateRec(symbol);
			res[i] = derivation;
		}

		return res;
	}

	/**
	 * Gets a string representation of the nonterminal symbols in the
	 * {@code grammar}. The returned string contains all nonterminal symbols as
	 * a sorted, comma-separated list enclosed in square brackets.
	 * <p>
	 * Example: {@code [<np>, <s>, <vp>]}
	 *
	 * @return a string representation of the nonterminal symbols.
	 */
	public String getSymbols() {
		// as per spec, `toString()` already exists!
		return grammar.keySet().toString();
	}

	/**
	 * A private helper function for {@link #generate()}.
	 * <p>
	 * Recursively derives a random string of terminal symbols from
	 * {@code symbol}. May return an empty string if a rule of {@code symbol}
	 * contains no symbols.
	 * <p>
	 * Requires: There are no empty lists in the rules (values) of the
	 * {@link #grammar}.
	 *
	 * @param symbol the symbol to begin the derivation from.
	 * @return a random derivation from the given symbol.
	 * @see #generate()
	 */
	private String generateRec(String symbol) {
		if (!grammarContains(symbol)) {
			// base case: `symbol` is terminal
			return symbol;
		}
		// recursive case: `symbol` is nonterminal

		List<List<String>> rules = grammar.get(symbol);
		// select a random rule from `rules`
		// use `get` instead of `[]` since rules is a `List`, not an `ArrayList`
		List<String> rule = rules.get(random.nextInt(rules.size()));
		if (rule.isEmpty()) {
			return "";
		}

		// the first symbol doesn't have a space to the left of it
		String res = generateRec(rule.get(0));
		for (int i = 1; i < rule.size(); i++) {
			res += " " + generateRec(rule.get(i));
		}

		return res;
	}
}

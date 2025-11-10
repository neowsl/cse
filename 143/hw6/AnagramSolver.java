import java.util.*;

public class AnagramSolver {
	private final Map<String, LetterInventory> inventories;

	public AnagramSolver(List<String> list) {
		inventories = new HashMap<>();

		// preprocess the dictionary by computing `LetterInventory`s in advance
		for (String word : list) {
			inventories.put(word, new LetterInventory(word));
		}
	}

	public void print(String s, int max) {
		if (max < 0) {
			throw new IllegalArgumentException("max must be >= 0 (max = "
					+ max + ")");
		}

		LetterInventory reference = new LetterInventory(s);
		SortedSet<String> prunedDictionary = new TreeSet<>();
		for (String word : inventories.keySet()) {
			if (reference.subtract(inventories.get(word)) != null) {
				prunedDictionary.add(word);
			}
		}

		// if `max` = 0, then we can use unlimited words (i.e. the size of the
		// entire inventory)
		print(reference, prunedDictionary, new Stack<>(),
				max > 0 ? max : inventories.size());
	}

	private void print(LetterInventory current, SortedSet<String> dictionary,
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

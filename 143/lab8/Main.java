import java.util.*;

public class Main {
	public static Map<Integer, Integer> counts(List<Integer> l, Set<Integer> s) {
		Map<Integer, Integer> res = new TreeMap<>();

		for (int x : l) {
			if (s.contains(x)) {
				if (!res.containsKey(x)) {
					res.put(x, 0);
				}
				res.put(x, res.get(x) + 1);
			}
		}

		return res;
	}

	public static Map<String, String> convert(Set<String> phones) {
		Map<String, Set<String>> res = new TreeMap<>();

		for (String s : phones) {
			String[] split = s.split("-");
			String prefix = split[0], suffix = split[1];

			if (!res.containsKey(prefix)) {
				res.put(prefix, new TreeSet<>());
			}
			res.get(prefix).add(suffix);
		}

		return res;
	}
}

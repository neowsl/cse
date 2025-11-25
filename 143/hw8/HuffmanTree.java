import java.util.*;
import java.io.*;

public class HuffmanTree {
	private HuffmanNode overallRoot;

	public HuffmanTree(int[] count) {
		Queue<HuffmanNode> frontier = new PriorityQueue<>();

		for (int i = 0; i < count.length; i++) {
			if (count[i] > 0) {
				frontier.add(new HuffmanNode(i, count[i]));
			}
		}

		// insert pseudo-eof character
		frontier.add(new HuffmanNode(count.length, 1));

		while (frontier.size() > 1) {
			HuffmanNode a = frontier.remove();
			HuffmanNode b = frontier.remove();

			HuffmanNode next = new HuffmanNode(
					-1, // internal node; symbol doesn't matter
					a.frequency + b.frequency,
					a,
					b);
			frontier.add(next);
		}

		overallRoot = frontier.remove();
	}

	/**
	 * Requires that {@code root} is not null (satisfied by constructor).
	 */
	public void write(PrintStream output) {
		write(output, overallRoot, "");
	}

	/**
	 * Requires that {@code root} is not null.
	 */
	private void write(PrintStream output, HuffmanNode root, String trace) {
		if (root.left == null && root.right == null) {
			System.out.println(root.symbol);
			System.out.println(trace);
		} else {
			write(output, root.left, trace + "0");
			write(output, root.right, trace + "1");
		}
	}
}

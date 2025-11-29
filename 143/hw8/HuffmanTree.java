import java.util.*;
import java.io.*;

/**
 * The {@link HuffmanTree} class allows for compression and decompression of
 * files. A {@link HuffmanTree} is either constructed from a list of frequencies
 * (for compression) or a {@link Scanner} (for decompression). It provides two
 * methods, {@link #write(PrintStream)} and {@link #decode(BitInputStream,
 * PrintStream, int)} to compress and decompress files, respectively.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 8
 * @date 2025-12-04
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section AB
 */
public class HuffmanTree {
	/**
	 * The root of this {@link HuffmanTree}.
	 */
	private HuffmanNode overallRoot;

	/**
	 * Constructs a new {@link HuffmanTree} given an array of frequencies.
	 *
	 * @param count An array of frequencies, where {@code count[i]} is the
	 *              number of occurences of the character with integer value
	 *              {@code i}.
	 */
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
			// internal node; symbol doesn't matter
			HuffmanNode next = new HuffmanNode(-1, a.frequency + b.frequency, a, b);
			frontier.add(next);
		}

		overallRoot = frontier.remove();
	}

	/**
	 * Reconstructs a {@link HuffmanTree} given a {@link Scanner}.
	 * <p>
	 * Requires that the input tree is in standard format. I.e., each line
	 * contains a integer symbol code, followed by a line containing that
	 * symbol's path in the tree.
	 *
	 * @param input A {@link Scanner} that contains a tree stored in standard
	 *              format.
	 */
	public HuffmanTree(Scanner input) {
		while (input.hasNext()) {
			int symbol = Integer.parseInt(input.nextLine());
			String code = input.nextLine();

			overallRoot = construct(overallRoot, symbol, code, 0);
		}
	}

	/**
	 * Recursive helper method for {@link #HuffmanTree(Scanner)}. Inserts a new
	 * {@link HuffmanNode} with symbol {@code symbol} relative to {@code root},
	 * following the path in {@code code}.
	 *
	 * @param root   The root of the tree to insert into.
	 * @param symbol The symbol to insert.
	 * @param code   The path relative to {@code root} to insert at.
	 */
	private HuffmanNode construct(HuffmanNode root, int symbol, String code,
			int depth) {
		if (depth == code.length()) {
			// frequency doesn't matter; set to 0
			return new HuffmanNode(symbol, 0);
		}

		if (root == null) {
			root = new HuffmanNode(-1, 0);
		}

		if (code.charAt(depth) == '0') {
			root.left = construct(root.left, symbol, code, depth + 1);
		} else {
			root.right = construct(root.right, symbol, code, depth + 1);
		}

		return root;
	}

	/**
	 * Writes the tree to {@code output} in standard format. I.e., each line
	 * contains a integer symbol code, followed by a line containing that
	 * symbol's path in the tree.
	 * <p>
	 * Requires that {@code root} is not null (satisfied by constructor).
	 *
	 * @param output The {@link PrintStream} to write the tree to.
	 */
	public void write(PrintStream output) {
		write(output, overallRoot, "");
	}

	/**
	 * Recursive helper method for {@link #write(PrintStream)}. Writes the tree
	 * to {@code output} in standard format.
	 * <p>
	 * Requires that {@code root} is not null.
	 *
	 * @param output The {@link PrintStream} to write the tree to.
	 * @param root   The root of the tree currently being written.
	 * @param trace  The path from the overall root to {@code root}.
	 */
	private void write(PrintStream output, HuffmanNode root, String trace) {
		if (root.left == null && root.right == null) {
			output.println(root.symbol);
			output.println(trace);
		} else {
			write(output, root.left, trace + "0");
			write(output, root.right, trace + "1");
		}
	}

	/**
	 * Decodes, or decompresses, the {@code input}, writing into {@code output}.
	 * Stops reading when it encounters a character with value {@code eof}
	 * (does not write the pseudo-eof character).
	 * <p>
	 * Requires that {@code input} contains a legal encoding of characters for
	 * this tree's code.
	 *
	 * @param input  The {@link BitInputStream} to read from.
	 * @param output The {@link PrintStream} to write to.
	 * @param eof    The value of the pseudo-eof character.
	 */
	public void decode(BitInputStream input, PrintStream output, int eof) {
		HuffmanNode curr = overallRoot;

		while (curr.symbol != eof) {
			if (curr.left == null && curr.right == null) {
				output.write(curr.symbol);
				curr = overallRoot;
			}

			int bit = input.readBit();

			curr = bit == 0 ? curr.left : curr.right;
		}
	}
}

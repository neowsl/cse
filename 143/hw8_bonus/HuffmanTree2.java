import java.util.*;
import java.io.*;

public class HuffmanTree2 {
	/**
	 * The root of this {@link HuffmanTree2}.
	 */
	private HuffmanNode overallRoot;

	/**
	 * Constructs a new {@link HuffmanTree2} given an array of frequencies.
	 *
	 * @param count An array of frequencies, where {@code count[i]} is the
	 *              number of occurences of the character with integer value
	 *              {@code i}.
	 */
	public HuffmanTree2(int[] count) {
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
	 * Reconstructs a {@link HuffmanTree2} given a {@link Scanner}.
	 * <p>
	 * Requires that the input tree is in standard format. I.e., each line
	 * contains a integer symbol code, followed by a line containing that
	 * symbol's path in the tree.
	 *
	 * @param input A {@link Scanner} that contains a tree stored in standard
	 *              format.
	 */
	public HuffmanTree2(Scanner input) {
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
	 * Reconstructs a {@link HuffmanTree2} given a {@link BitInputStream}.
	 * <p>
	 * Requires that the input tree is in standard format. I.e., {@code input}
	 * contains a preorder traversal of a Huffman Tree, where branch nodes are
	 * a 0 and leaf nodes are a 1 followed by 9 bits containing the node's
	 * symbol.
	 * <p>
	 * Ensures that only the header is consumed from the input. I.e., reads no
	 * further than necessary to construct the tree.
	 */
	public HuffmanTree2(BitInputStream input) {
		overallRoot = construct(input);
	}

	/**
	 * Recursive helper method for {@link #HuffmanTree2(BitInputStream)}. Reads
	 * a single node from {@code input} and returns the root of the subtree.
	 * <p>
	 * Requires that {@code input} is legal (as per
	 * {@link #HuffmanTree2(BitInputStream)}).
	 *
	 * @param input The input stream to read from.
	 * @return the root of the new subtree.
	 */
	private HuffmanNode construct(BitInputStream input) {
		HuffmanNode root = new HuffmanNode(-1, 0);

		if (input.readBit() == 1) {
			// base case: leaf node (read 9 bits for symbol)
			root.symbol = read9(input);
			return root;
		}

		// recursive case: internal node (must construct both children)
		root.left = construct(input);
		root.right = construct(input);
		return root;
	}

	/**
	 * Assigns codes for each character of this tree to {@code codes}.
	 * <p>
	 * Requires that {@code codes} has all null values.
	 * <p>
	 * Ensures that for each {@code i} in the set of symbols,
	 * {@code code[i]} is set to symbol {@code i}'s code.
	 *
	 * @param codes The array to assign into.
	 */
	public void assign(String[] codes) {
		assign(overallRoot, codes, "");
	}

	/**
	 * Recursive helper method for {@link #assign(String[])}.
	 * <p>
	 * Ensures that all codes of subtree {@code root} are assigned into
	 * {@code codes}.
	 *
	 * @param root  The root of the subtree to assign.
	 * @param codes The array to assign into.
	 * @param trace The path from the overall root to {@code root}.
	 */
	private void assign(HuffmanNode root, String[] codes, String trace) {
		if (root.left == null && root.right == null) {
			// base case: leaf node
			codes[root.symbol] = trace;
		} else {
			// recursive case: internal node
			assign(root.left, codes, trace + "0");
			assign(root.right, codes, trace + "1");
		}
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
	 * Writes this tree to {@code output} using the standard representation.
	 * I.e., a preorder traversal this tree, where branch nodes are a 0 and leaf
	 * nodes are a 1 followed by 9 bits containing the node's symbol.
	 * <p>
	 * Ensures that this tree is written to {@code output}.
	 *
	 * @param output The output stream to write to.
	 */
	public void writeHeader(BitOutputStream output) {
		writeHeader(overallRoot, output);
	}

	/**
	 * Recursive helper method for {@link #writeHeader(BitOutputStream)}.
	 * <p>
	 * Ensures that subtree {@code root} is written to {@code output}.
	 *
	 * @param root   The root of the subtree to write.
	 * @param output The output stream to write to.
	 */
	private void writeHeader(HuffmanNode root, BitOutputStream output) {
		if (root.left == null && root.right == null) {
			output.writeBit(1);
			write9(output, root.symbol);
		} else {
			output.writeBit(0);
			writeHeader(root.left, output);
			writeHeader(root.right, output);
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

	// pre : an integer n has been encoded using write9 or its equivalent
	// post: reads 9 bits to reconstruct the original integer
	private int read9(BitInputStream input) {
		int multiplier = 1;
		int sum = 0;
		for (int i = 0; i < 9; i++) {
			sum += multiplier * input.readBit();
			multiplier = multiplier * 2;
		}
		return sum;
	}

	// pre : 0 <= n < 512
	// post: writes a 9-bit representation of n to the given output stream
	private void write9(BitOutputStream output, int n) {
		for (int i = 0; i < 9; i++) {
			output.writeBit(n % 2);
			n = n / 2;
		}
	}
}

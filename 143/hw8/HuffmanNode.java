/**
 * The {@link HuffmanNode} class represents a node in the {@link HuffmanTree}.
 * A node is either an internal node or a leaf node. Each leaf node keeps
 * information about a single symbol and its frequency for the
 * {@link HuffmanTree} construction process.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 8
 * @date 2025-12-04
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section AB
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
	/**
	 * The integer code for this node's symbol.
	 */
	public int symbol;
	/**
	 * The frequency of the symbol in the text, used to compare nodes.
	 */
	public int frequency;
	/**
	 * The left child of this node, represented by a "0" in the code.
	 */
	public HuffmanNode left;
	/**
	 * The right child of this node, represented by a "1" in the code.
	 */
	public HuffmanNode right;

	/**
	 * Constructs a new {@link HuffmanNode} given a {@code symbol},
	 * {@code frequency}, and references to both children.
	 *
	 * @param symbol    The integer code for this node's symbol.
	 * @param frequency The frequency of the symbol in the text.
	 * @param left      The left child of this node.
	 * @param right     The right child of this node.
	 */
	public HuffmanNode(int symbol, int frequency, HuffmanNode left,
			HuffmanNode right) {
		this.symbol = symbol;
		this.frequency = frequency;
		this.left = left;
		this.right = right;
	}

	/**
	 * Constructs a new {@link HuffmanNode} given a {@code symbol} and
	 * {@code frequency}. Initializes both children to {@code null}.
	 *
	 * @param symbol    The integer code for this node's symbol.
	 * @param frequency The frequency of the symbol in the text.
	 */
	public HuffmanNode(int symbol, int frequency) {
		this(symbol, frequency, null, null);
	}

	/**
	 * Compares this node with {@code other} based on their frequencies.
	 *
	 * @return A negative number if this node comes before {@code other}, 0 if
	 *         this node is equal to {@code other}, or a positive number if this
	 *         node comes after {@code other}.
	 */
	public int compareTo(HuffmanNode other) {
		return frequency - other.frequency;
	}
}

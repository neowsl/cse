public class HuffmanNode implements Comparable<HuffmanNode> {
	public int symbol;
	public int frequency;
	public HuffmanNode left;
	public HuffmanNode right;

	public HuffmanNode(int symbol, int frequency, HuffmanNode left, HuffmanNode right) {
		this.symbol = symbol;
		this.frequency = frequency;
		this.left = left;
		this.right = right;
	}

	public HuffmanNode(int symbol, int frequency) {
		this(symbol, frequency, null, null);
	}

	public int compareTo(HuffmanNode other) {
		return frequency - other.frequency;
	}
}

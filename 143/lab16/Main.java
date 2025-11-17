import java.util.*;

class IntTreeNode {
	public int data;
	public IntTreeNode left;
	public IntTreeNode right;

	public IntTreeNode(int data) {
		this(data, null, null);
	}

	public IntTreeNode(int data, IntTreeNode left, IntTreeNode right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}
}

class IntTree {
	private IntTreeNode overallRoot;

	public void writeTree() {
		writeTree(overallRoot);
	}

	private void writeTree(IntTreeNode root) {
		if (root != null) {
			int code = 0;
			if (root.left != null)
				code += 1;
			if (root.right != null)
				code += 2;
			System.out.println(code + " " + root.data);

			writeTree(root.left);
			writeTree(root.right);
		}
	}

	public void readTree(Scanner input) {
		overallRoot = readTree(overallRoot, input);
	}

	private IntTreeNode readTree(IntTreeNode root, Scanner input) {
		int code = input.nextInt();
		int data = input.nextInt();
		IntTreeNode curr = new IntTreeNode(data);
		if ((code & 1 << 0) > 0) {
			curr.left = readTree(curr.left, input);
		}
		if ((code & 1 << 1) > 0) {
			curr.right = readTree(curr.right, input);
		}
		return curr;
	}
}

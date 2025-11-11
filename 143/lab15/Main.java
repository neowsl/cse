class IntTreeNode {
	public int data;
	public IntTreeNode left;
	public IntTreeNode right;

	public IntTreeNode(int data, IntTreeNode left, IntTreeNode right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public IntTreeNode(int data) {
		this(data, null, null);
	}
}

class IntTree {
	private IntTreeNode overallRoot;

	public int numNodes() {
		return numNodes(overallRoot);
	}

	private int numNodes(IntTreeNode root) {
		if (root == null) {
			return 0;
		}

		return numNodes(root.left) + numNodes(root.right) + 1;
	}

	public int numLeaves() {
		return numLeaves(overallRoot);
	}

	private int numLeaves(IntTreeNode root) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return 1;
		}

		return numLeaves(root.left) + numLeaves(root.right);
	}

	public void printLevel(int level) {
		if (level < 1) {
			throw new IllegalArgumentException();
		}

		printLevel(overallRoot, level, 1);
	}

	private void printLevel(IntTreeNode root, int target, int level) {
		if (root != null) {
			if (level == target) {
				System.out.println(root.data);
			} else {
				printLevel(root.left, target, level + 1);
				printLevel(root.right, target, level + 1);
			}
		}
	}

	public void writeTree() {
		writeTree(overallRoot);
	}

	private void writeTree(IntTreeNode root) {
		if (root != null) {
			int code = 0;
			if (root.left != null) {
				code += 1;
			}
			if (root.right != null) {
				code += 2;
			}
			System.out.println(code + " " + root.data);

			writeTree(root.left);
			writeTree(root.right);
		}
	}

	public boolean contains(int n) {
		return contains(overallRoot, n);
	}

	private boolean contains(IntTreeNode root, int n) {
		if (root == null) {
			return false;
		}
		if (root.data == n) {
			return true;
		}

		// short-circuit!
		return contains(root.left, n) || contains(root.right, n);
	}
}

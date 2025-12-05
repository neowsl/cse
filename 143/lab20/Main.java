class IntTreeNode {
	public int data;
	public IntTreeNode left;
	public IntTreeNode right;
}

class IntTree {
	private IntTreeNode overallRoot;

	public void tighten() {
		overallRoot = tighten(overallRoot);
	}

	private IntTreeNode tighten(IntTreeNode root) {
		if (root == null) {
			return root;
		}

		root.right = tighten(root.right);
		root.left = tighten(root.left);

		if (root.right != null && root.left == null) {
			return root.right;
		}
		if (root.left != null && root.right == null) {
			return root.left;
		}

		return root;
	}
}

class ListNode {
	public int data;
	public ListNode next;

	public ListNode(int data, ListNode next) {
		this.data = data;
		this.next = next;
	}

	public ListNode(int data) {
		this(data, null);
	}
}

class LinkedIntList {
	private ListNode front;

	public LinkedIntList(ListNode front) {
		this.front = front;
	}

	public String toString() {
		if (front == null) {
			return "[]";
		}

		String res = "[" + front.data;
		ListNode curr = front.next;
		while (curr != null) {
			res += ", " + curr.data;
			curr = curr.next;
		}
		return res + "]";
	}

	public int shiftLastOf3() {
		ListNode newFront = null;
		ListNode newBack = null;

		int res = 0;
		int i = 0;
		ListNode prev = null;
		ListNode curr = front;
		while (curr != null) {
			i++;

			if (i % 3 == 0) {
				res++;
				
				if (newFront == null) {
					newFront = curr;
					newBack = curr;
				} else {
					newBack.next = curr;
					newBack = curr;
				}
				
				prev.next = curr.next;
				curr = curr.next;
			} else {
				prev = curr;
				curr = curr.next;
			}
		}

		newBack.next = front;
		front = newFront;

		return res;
	}
}

public class Main {
	public static void main(String[] args) {
		ListNode front = new ListNode(3, new ListNode(19, new ListNode(7, new ListNode(45, new ListNode(-2, new ListNode(8, new ListNode(6, new ListNode(18, new ListNode(42, new ListNode(5, new ListNode(12)))))))))));
		LinkedIntList list = new LinkedIntList(front);

		System.out.println(list);
		int res = list.shiftLastOf3();
		System.out.println(res);
		System.out.println(list);
	}
}

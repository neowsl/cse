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

	public void mergeFrom(LinkedIntList other) {
		if (front == null) {
			front = other.front;
		} else if (other.front != null) {
			ListNode newFront;
			if (front.data < other.front.data) {
				newFront = front;
				front = front.next;
			} else {
				newFront = other.front;
				other.front = other.front.next;
			}
			ListNode curr = newFront;
			while (front != null && other.front != null) {
				if (front.data < other.front.data) {
					curr.next = front;
					front = front.next;
				} else {
					curr.next = other.front;
					other.front = other.front.next;
				}
				curr = curr.next;
			}
			if (front != null) {
				curr.next = front;
			} else {
				curr.next = other.front;
			}
			front = newFront;
		}
	}

	public boolean bubble() {
		if (front == null || front.next == null) {
			return false;
		}

		boolean changed = false;
		if (front.next.data < front.data) {
			ListNode tmp = front;
			front = front.next;
			tmp.next = front.next;
			front = tmp;
			changed = true;
		}
		ListNode curr = front;
		while (curr.next.next != null) {
			if (curr.next.next.data < curr.next.data) {
				ListNode tmp = curr.next;
				curr.next = curr.next.next;
				tmp.next = curr.next.next;
				curr.next.next = tmp;
				changed = true;
			} else {
				curr = curr.next;
			}
		}
		return changed;
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
		System.out.println();

		LinkedIntList list1 = new LinkedIntList(new ListNode(-3, new ListNode(0, new ListNode(9, new ListNode(12)))));
		LinkedIntList list2 = new LinkedIntList(new ListNode(9, new ListNode(9, new ListNode(15))));
		list1.mergeFrom(list2);
		System.out.println(list1);
		System.out.println();

		boolean sorted = false;
		while (!sorted) {
			System.out.println(list);
			sorted = !list.bubble();
		}
	}
}

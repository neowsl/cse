class ListNode {
	public int data;
	public ListNode next;

	public ListNode() {
		this(0, null);
	}

	public ListNode(int data) {
		this(data, null);
	}

	public ListNode(int data, ListNode next) {
		this.data = data;
		this.next = next;
	}
}

public class LinkedIntList {
	private ListNode front;

	public LinkedIntList() {
		front = null;
	}

	public void add(int value) {
		if (front == null) {
			front = new ListNode(value);
		} else {
			ListNode current = front;
			while (current.next != null) {
				current = current.next;
			}
			current.next = new ListNode(value);
		}
	}

	public int size() {
		int res = 0;
		ListNode curr = front;
		while (curr != null) {
			res++;
			curr = curr.next;
		}
		return res;
	}

	public int get(int index) {
		ListNode curr = front;
		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}
		return curr.data;
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

	public int indexOf(int value) {
		ListNode curr = front;
		int i = 0;
		while (curr != null) {
			if (curr.data == i) {
				return i;
			}
			curr = curr.next;
		}

		return -1;
	}

	public void add(int index, int value) {
		if (index == 0) {
			if (front == null) {
				front = new ListNode(value);
			} else {
				front = new ListNode(value, front.next);
			}
		} else {
			ListNode curr = front;
			for (int i = 0; i < index - 1; i++) {
				curr = curr.next;
			}

			curr.next = new ListNode(value, curr.next);
		}
	}

	public void remove(int index) {
		if (index == 0) {
			front = front.next;
		} else {
			ListNode curr = front;
			for (int i = 0; i < index - 1; i++) {
				curr = curr.next;
			}
			curr.next = curr.next.next;
		}
	}
}

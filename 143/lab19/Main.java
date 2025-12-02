class ListNode {
	public int data;
	public ListNode next;
}

class LinkedIntList {
	private ListNode front;

	public int evenSum() {
		int res = 0;

		ListNode curr = front;
		while (curr != null) {
			res += curr.data;
			
			curr = curr.next;
			if (curr != null) curr = curr.next;
		}

		return res;
	}

	public void removeDuplicates() {
		ListNode curr = front;
		while (curr != null) {
			ListNode prev = curr;
			ListNode next = curr.next;
			while (next != null) {
				if (next.data == curr.data) {
					next = next.next;
					prev.next = next;
				} else {
					prev = next;
					next = next.next;
				}
			}
			curr = curr.next;
		}
	}

	public void switchPairs() {
		if (front != null && front.next != null) {
			ListNode curr = front.next;
			front.next = curr.next;
			curr.next = front;
			front = curr;

			curr = curr.next;
			while (curr.next != null && curr.next.next != null) {
				ListNode tmp = curr.next.next;
				curr.next.next = tmp.next;
				tmp.next = curr.next;
				curr.next = tmp;

				curr = curr.next.next;
			}
		}
	}
}

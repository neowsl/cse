import java.util.*;

public class Main {
	public void mirrorSplit(Stack<Integer> s) {
		int ogSize = s.size();
		Queue<Integer> q = new LinkedList<>();
		while (!s.isEmpty()) {
			int x = s.pop();
			int a = x / 2;
			int b = x - a;
			q.add(b);
			q.add(a);
		}
		for (int i = 0; i < ogSize; i++) {
			s.push(q.remove());
			q.add(q.remove());
		}
		while (!s.isEmpty()) {
			q.add(s.pop());
		}
		for (int i = 0; i < ogSize; i++) {
			q.add(q.remove());
		}
		while (!q.isEmpty()) {
			s.push(q.remove());
		}
	}
}

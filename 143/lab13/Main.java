import java.util.*;

public class Main {
	public static void solve(int x, int y) {
		System.out.println("solutions:");
		solve(0, 1, x, y, "N");
		solve(1, 1, x, y, "NE");
		solve(1, 0, x, y, "E");
	}

	public static void solve(int x, int y, int tx, int ty, String path) {
		if (x <= tx && y <= ty) {
			solve(x, y + 1, tx, ty, path + " N");
			solve(x + 1, y + 1, tx, ty, path + " NE");
			solve(x + 1, y, tx, ty, path + " E");
		}
		if (x == tx && y == ty) {
			System.out.println("moves: " + path);
		}
	}

	public static void printNumbers2() {
		printNumbers2(0, 0, 0, "");
	}

	public static void printNumbers2(int numOnes, int numTwos, int numThrees, String curr) {
		if (numOnes == 1 && numTwos == 2 && numThrees == 5) {
			System.out.println(curr);
		} else {
			if (numOnes < 1) {
				printNumbers2(numOnes + 1, numTwos, numThrees, curr + "1");
			}
			if (numTwos < 2) {
				printNumbers2(numOnes, numTwos + 1, numThrees, curr + "2");
			}
			if (numThrees < 5) {
				printNumbers2(numOnes, numTwos, numThrees + 1, curr + "3");
			}
		}
	}
}

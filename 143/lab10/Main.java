public class Main {
	public static void writeSquares(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}

		if (n == 1) {
			System.out.print(1);
		} else {
			if (n % 2 == 0) {
				writeSquares(n - 1);
				System.out.print(", " + (n * n));
			} else {
				System.out.print((n * n) + ", ");
				writeSquares(n - 1);
			}
		}
	}

	public static void writeSequence(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}

		if (n == 1) {
			System.out.print("1");
		} else if (n == 2) {
			System.out.print("1 1");
		} else {
			System.out.print((n + 1) / 2 + " ");
			writeSequence(n - 2);
			System.out.print(" " + (n + 1) / 2);
		}
	}
}

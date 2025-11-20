public class USCurrency implements Comparable<USCurrency> {
	private int cents;

	public USCurrency(int dollars, int cents) {
		this(100 * dollars + cents);
	}

	private USCurrency(int cents) {
		this.cents = cents;
	}

	public int cents() {
		return cents % 100;
	}

	public int dollars() {
		return cents / 100;
	}

	public String toString() {
		String res = "";
		if (cents < 0) {
			res += "-";
		}
		String centsStr = "" + Math.abs(cents);
		if (centsStr.length() == 1) {
			centsStr = "0" + centsStr;
		}
		res += "$" + Math.abs(dollars()) + "." + centsStr;
		return res;
	}

	public USCurrency add(USCurrency other) {
		return new USCurrency(cents + other.cents);
	}

	public USCurrency subtract(USCurrency other) {
		return new USCurrency(cents - other.cents);
	}

	public int compareTo(USCurrency other) {
		return cents - other.cents;
	}
}

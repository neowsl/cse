import java.util.*;
import java.io.PrintStream;

public class QuestionTree {
	private QuestionNode overallRoot;
	private final Scanner console;

	public QuestionTree() {
		overallRoot = new QuestionNode("computer");
		console = new Scanner(System.in);
	}

	public void read(Scanner input) {
		overallRoot = null;
		overallRoot = read(overallRoot, input);
	}

	private QuestionNode read(QuestionNode root, Scanner input) {
		if (!input.hasNext()) {
			return root;
		}

		if (root == null) {
			String code = input.nextLine();
			String content = input.nextLine();

			root = new QuestionNode(content);

			if (code.equals("A:")) {
				return root;
			}
		}

		root.yes = read(root.yes, input);
		root.no = read(root.no, input);

		return root;
	}

	public void write(PrintStream output) {
		write(overallRoot, output);
	}

	private void write(QuestionNode root, PrintStream output) {
		if (root != null) {
			String code = root.isAnswer() ? "A:" : "Q:";
			output.println(code);
			output.println(root.content);

			write(root.yes, output);
			write(root.no, output);
		}
	}

	public void askQuestions() {
		overallRoot = askQuestions(overallRoot);
	}

	private QuestionNode askQuestions(QuestionNode root) {
		if (root.isAnswer()) {
			if (yesTo("Would your object happen to be " + root.content + "?")) {
				System.out.println("Great, I got it right!");
				return root;
			}

			System.out.print("What is the name of your object? ");
			String name = console.nextLine().trim();

			System.out.println("Please give me a yes/no question that");
			System.out.println("distinguishes between your object");
			System.out.print("and mine--> ");
			String question = console.nextLine().trim();

			if (yesTo("And what is the answer for your object?")) {
				return new QuestionNode(question, new QuestionNode(name), root);
			}
			return new QuestionNode(question, root, new QuestionNode(name));
		}

		if (yesTo(root.content)) {
			root.yes = askQuestions(root.yes);
		} else {
			root.no = askQuestions(root.no);
		}
		return root;
	}

	// post: asks the user a question, forcing an answer of "y " or "n";
	// returns true if the answer was yes, returns false otherwise
	public boolean yesTo(String prompt) {
		System.out.print(prompt + " (y/n)? ");
		String response = console.nextLine().trim().toLowerCase();
		while (!response.equals("y") && !response.equals("n")) {
			System.out.println("Please answer y or n.");
			System.out.print(prompt + " (y/n)? ");
			response = console.nextLine().trim().toLowerCase();
		}
		return response.equals("y");
	}
}

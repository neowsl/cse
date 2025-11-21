import java.util.*;
import java.io.*;

/**
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 7
 * @date 2025-11-20
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section AB
 */
public class QuestionTree {
	/**
	 * The root of the question tree. Must not be {@code null}.
	 */
	private QuestionNode overallRoot;
	/**
	 * A {@link Scanner} for reading from the console to avoid shared reads.
	 */
	private final Scanner console;

	/**
	 * Constructs a new {@link QuestionTree} with the root node being an answer
	 * node with content "computer". Also initializes the {@link #console}.
	 */
	public QuestionTree() {
		overallRoot = new QuestionNode("computer");
		console = new Scanner(System.in);
	}

	/**
	 * Reads an entire question tree from {@code input}.
	 * <p>
	 * Requires:
	 * <ul>
	 * <li>{@code input} is not {@code null}.</li>
	 * <li>{@code input} is not empty.</li>
	 * <li>{@code input} contains nodes in preorder traversal order.</li>
	 * <li>Every two lines of {@code input} is a single node, where the first
	 * line is either "Q:" or "A:" to denote a question or answer node,
	 * respectively, and the second line is the content of the node.</li>
	 * <li>The content of {@code input} is a full binary tree (i.e. a node has
	 * either 0 or 2 children).</li>
	 * </ul>
	 *
	 * @param input The input stream to read from.
	 */
	public void read(Scanner input) {
		overallRoot = readHelper(input);
	}

	/**
	 * A recursive helper method for {@link #read(Scanner)}. Reads a single node
	 * from {@code input} and returns the root of the subtree. Assumes that
	 * {@code input} is legal (as per {@link #read(Scanner)}).
	 *
	 * @param input The input stream to read from.
	 * @return The root of the (possibly modified) subtree.
	 * @see #read(Scanner)
	 */
	private QuestionNode readHelper(Scanner input) {
		String code = input.nextLine().trim();
		String content = input.nextLine().trim();

		QuestionNode root = new QuestionNode(content);

		if (code.equals("A:")) {
			return root;
		}

		root.yes = readHelper(input);
		root.no = readHelper(input);

		return root;
	}

	/**
	 * Writes the entire question tree to {@code output} in the same format as
	 * {@link #read(Scanner)}.
	 * <p>
	 * Ensures:
	 * <ul>
	 * <li>The output contains nodes in preorder traversal order.</li>
	 * <li>Every two lines of {@code output} is a single node, where the first
	 * line is either "Q:" or "A:" to denote a question or answer node, and the
	 * second line is the content of the node.</li>
	 * <li>The content of the output is a full binary tree (i.e. a node has
	 * either 0 or 2 children).</li>
	 * </ul>
	 *
	 * @param output The output stream to write to.
	 */
	public void write(PrintStream output) {
		write(overallRoot, output);
	}

	/**
	 * A recursive helper method for {@link #write(PrintStream)}. Writes a
	 * single node to {@code output}.
	 *
	 * @param root   The root of the subtree to write.
	 * @param output The output stream to write to.
	 * @see #write(PrintStream)
	 */
	private void write(QuestionNode root, PrintStream output) {
		if (root != null) {
			String code = isAnswer(root) ? "A:" : "Q:";
			output.println(code);
			output.println(root.content);

			write(root.yes, output);
			write(root.no, output);
		}
	}

	/**
	 * Asks the user a series of yes/no questions from the question tree until
	 * an answer node is reached.
	 * <p>
	 * If the answer is incorrect, the user is prompted for what he/she is
	 * thinking of. The new object is then inserted into the question tree,
	 * along with a question that distinguishes the new object from the others.
	 */
	public void askQuestions() {
		overallRoot = askQuestions(overallRoot);
	}

	/**
	 * A recursive helper method for {@link #askQuestions()}.
	 * <p>
	 * If {@code root} is a question node, asks the user a yes/no question to
	 * determine how to proceed.
	 * <p>
	 * If {@code root} is an answer node, asks the user if the answer is
	 * correct. If the answer is incorrect, asks the user for what he/she is
	 * thinking of. The new object is then inserted into the question tree.
	 *
	 * @param root The root of the subtree to ask questions from.
	 * @return The root of the (possibly modified) question tree.
	 */
	private QuestionNode askQuestions(QuestionNode root) {
		if (isAnswer(root)) {
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

	/**
	 * Returns whether {@code node} is an answer node. An answer node is
	 * equivalent to a leaf node (i.e. a node with neither a yes nor a no
	 * branch).
	 *
	 * @return {@code true} if this node is an answer, {@code false} otherwise.
	 */
	private boolean isAnswer(QuestionNode node) {
		return node.yes == null && node.no == null;
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

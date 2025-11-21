/**
 * The {@link QuestionNode} class represents a node in the {@link QuestionTree}.
 * A node can either be a question or an answer, where an answer node is
 * equivalent to a leaf node.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 7
 * @date 2025-11-20
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section AB
 */
public class QuestionNode {
	/**
	 * A reference to the yes branch of the question tree.
	 */
	public QuestionNode yes;
	/**
	 * A reference to the no branch of the question tree.
	 */
	public QuestionNode no;
	/**
	 * The content of the node. This is either a question for a question node or
	 * an object for an answer node.
	 *
	 * @see QuestionTree#isAnswer(QuestionNode)
	 */
	public final String content;

	/**
	 * Constructs a new {@link QuestionNode} with the given content and
	 * children.
	 *
	 * @param content The content of the node.
	 * @param yes     The yes branch of the question.
	 * @param no      The no branch of the question.
	 */
	public QuestionNode(String content, QuestionNode yes, QuestionNode no) {
		this.yes = yes;
		this.no = no;
		this.content = content;
	}

	/**
	 * Constructs a new {@link QuestionNode} with the given content. Sets both
	 * children to {@code null}.
	 *
	 * @param content The content of the node.
	 */
	public QuestionNode(String content) {
		this(content, null, null);
	}
}

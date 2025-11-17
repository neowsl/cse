public class QuestionNode {
	public QuestionNode yes;
	public QuestionNode no;
	public final String content;

	public QuestionNode(String content, QuestionNode yes, QuestionNode no) {
		this.yes = yes;
		this.no = no;
		this.content = content;
	}

	public QuestionNode(String content) {
		this(content, null, null);
	}

	public boolean isAnswer() {
		return yes == null && no == null;
	}
}

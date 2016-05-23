package beans;


public class Question {
	private int questionId;
	private String subject;
	private String  questionText;
	private boolean isActive;
	
	public Question() {
		this.setQuestionId(0);
		this.setSubject("");
		this.setQuestionText("");
		this.activate();		
	}

	public Question(int questionId, String subject, String questionText) throws Exception {
		if (this.setQuestionId(questionId) && this.setSubject(subject) && this.setQuestionText(questionText)){
			this.activate();
		}else{
			throw new Exception("Invalid parameters");
		}
	}
	
	public int getQuestionId(){
		return questionId;
	}
	
	public String getSubject(){
		return subject;
	}
	
	public String getQuestionText(){
		return questionText;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public boolean setQuestionId(int questionId) {
		this.questionId = questionId;
		return true;
	}
	
	public boolean setSubject(String subject) {
		this.subject = subject;
		return true;
	}
	
	public boolean setQuestionText(String questionText) {
		this.questionText = questionText;
		return true;
	}
	
	public boolean activate() {
		this.isActive = true;
		return true;
	}
	public boolean desactivate() {
		this.isActive = false;
		return true;
	}

}

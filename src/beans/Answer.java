package beans;

public class Answer {
	private int answerId;
	private int questionId;
	private String answerText;
	private boolean isActive;
	private boolean isTrue;
	
	public Answer(){
		this.setAnswerId(0);
		this.setQuestionId(0);
		this.setAnswerText();
		this.answerTrue();
		this.activate();		
	}
	
	public Answer(int answerId, int questionId, String answerText, boolean answerIsTrue) throws Exception {
		if (this.setAnswerId(answerId) && this.setQuestionId(questionId) && this.setAnswerText(answerText) && this.setIsTrue(isTrue)){
			this.activate();
		}else{
			throw new Exception("Invalid parameter");
		}
	}
	
	public int getAnswerId(){
		return answerId;
	}
	
	public int getQuestionId(){
		return questionId;
	}
	
	public String getAnswerText(){
		return answerText;
	}
	
	public boolean isTrue(){
		return isTrue;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public boolean setAnswerId(int answerId){
		this.answerId = answerId;
		return true;
	}
	
	public boolean setQuestionId(int questionId){
		this.questionId = questionId;
		return true;
	}
	
	public boolean setAnswerText(){
		this.answerText = answerText;
		return true;
	}
	
	public boolean answerTrue(){
		this.isTrue = true;
		return true;
	}
	
	public boolean answerFalse(){
		this.isTrue = false;
		return true;
	}
	
	public boolean activate(){
		this.isActive = true;
		return true;	
	}
	
	public boolean deactivate(){
		this.isActive = false;
		return true;
	}
	

}

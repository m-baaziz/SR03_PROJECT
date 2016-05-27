package beans;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private int questionId;
	private String subject;
	private String  questionText;
	private boolean isActive;
	private List<Answer> answers;
	
	public Question() {
		this.setQuestionId(0);
		this.setSubject("");
		this.setQuestionText("");
		this.activate();
		this.answers = new ArrayList<Answer>();
	}
	
	public Question(String questionText, String subject) throws Exception {
		if (this.setSubject(subject) && this.setQuestionText(questionText)){
			this.setQuestionId(0);
			this.activate();
			this.answers = new ArrayList<Answer>();
		}else{
			throw new Exception("Invalid parameters");
		}
	}

	public Question(int questionId, String subject, String questionText) throws Exception {
		if (this.setQuestionId(questionId) && this.setSubject(subject) && this.setQuestionText(questionText)){
			this.activate();
			this.answers = new ArrayList<Answer>();
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
	
	public List<Answer> getAnswers() {
		return this.answers;
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
	
	public boolean hasAnswer() {
		return !this.getAnswers().isEmpty();
	}
	
	public boolean addAnswers(List<Answer> answers) {
		return this.answers.addAll(answers);
	}
	
	public boolean addAnswer(Answer answer) {
		return this.answers.add(answer);
	}
	
	public Answer getGoodAnswer() {
		if (this.hasAnswer()) {
			for (int i=0; i<this.getAnswers().size(); i++) {
				Answer tmp = this.getAnswers().get(i);
				if (tmp.isTrue()) {
					return tmp;
				}
			}
		}
		return null;
	}
	
	public List<Answer> getBadAnswers() {
		if (this.hasAnswer()) {
			List<Answer> tmp = new ArrayList<Answer>();
			for (int i=0; i<this.getAnswers().size(); i++) {
				if (!this.getAnswers().get(i).isTrue()) {
					tmp.add(this.getAnswers().get(i));
				}
			}
			return tmp;
		}
		return null;
	}
}

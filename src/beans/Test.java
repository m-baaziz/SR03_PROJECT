package beans;

import java.util.ArrayList;
import java.util.List;

public class Test {
	private String subject;
	private boolean isActive;
	private List<Question> questions;
	
	public Test(){
		this.setSubject("");
		this.activate();	
		this.questions = new ArrayList<Question>();
	}
	
	public Test(String subject) throws Exception {
		if (this.setSubject(subject)){
			this.activate();
			this.questions = new ArrayList<Question>();
		}else{
			throw new Exception("Invalid parameters");
		}
	}
	
	public Test(String subject, boolean isActive) throws Exception {
		if (this.setSubject(subject)){
			if (isActive) {
				this.activate();
			} else {
				this.desactivate();
			}
			this.questions = new ArrayList<Question>();
		}else{
			throw new Exception("Invalid parameters");
		}
	}
	
	public String getSubject(){
		return subject;
	}
	
	
	public boolean isActive() {
		return isActive;
	}
	
	public List<Question> getQuestions() {
		return this.questions;
	}
	
	public boolean setSubject(String subject) {
		this.subject = subject;
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
	
	public boolean hasQuestion() {
		return !this.getQuestions().isEmpty();
	}
	
	public boolean addQuestions(List<Question> questions) {
		return this.questions.addAll(questions);
	}
	
	public boolean addQuestion(Question question) {
		return this.questions.add(question);
	}
}

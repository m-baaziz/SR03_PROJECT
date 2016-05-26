package beans;

import java.util.Date;

public class Records {

	private int recordId = -1;
	private Date date;
	private int duration; // in seconds
	private int score;
	private String email;
	private String subject;
	
	public Records(int recordId, Date date, int duration, int score, String email, String subject) {
		super();
		this.recordId = recordId;
		this.date = date;
		this.duration = duration;
		this.score = score;
		this.email = email;
		this.subject = subject;
	}

	public Records() {
		
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}

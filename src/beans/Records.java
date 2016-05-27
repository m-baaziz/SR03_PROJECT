package beans;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Records {

	private int recordId = -1;
	private Time duration; // in seconds
	private int score;
	private String email;
	private String subject;
	private Date startTime;
	private Date endTime;
	private List<RecordAnswers> recordAnswers; 
	
	public Records(int recordI, Time duration, int score, String email, String subject) {
		super();
		this.recordId = recordId;
		this.duration = duration;
		this.score = score;
		this.email = email;
		this.subject = subject;
		this.recordAnswers = new ArrayList<RecordAnswers>();
	}

	public Records() {
		this.recordAnswers = new ArrayList<RecordAnswers>();
	}
	
	public Records(String email, String subject) {
		this.email = email;
		this.subject = subject;
		this.score = 0;
		this.duration = new Time(0);
		this.recordAnswers = new ArrayList<RecordAnswers>();
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public int getScore() {
		return score;
	}
	
	public String scoreToString() {
		if (this.hasRecordAnswers()) {
			return this.score+"/"+this.recordAnswers.size();
		} else {
			return "";
		}
	}
	
	public String scoreToStringOverTwenty() {
		if (this.hasRecordAnswers()) {
			int tmp = this.score*20/this.recordAnswers.size();
			return tmp+"/20";
		} else {
			return "";
		}
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
	
	public List<RecordAnswers> getRecordAnswers() {
		return this.recordAnswers;
	}
	
	public boolean hasRecordAnswers() {
		return !this.recordAnswers.isEmpty();
	}
	
	public boolean addRecordAnswers(List<RecordAnswers> recordAnswers) {
		return this.recordAnswers.addAll(recordAnswers);
	}
	
	public boolean addRecordAnswer(RecordAnswers recordAnswer) {
		return this.recordAnswers.add(recordAnswer);
	}
	
	public void start() {
		this.startTime = new Date(Calendar.getInstance().getTimeInMillis());
	}
	
	public void finish() {
		this.endTime = new Date(Calendar.getInstance().getTimeInMillis());
		this.duration = new Time((endTime.getTime() - startTime.getTime()));
		if (this.hasRecordAnswers()) {
			int score = 0;
			List<RecordAnswers> recordAnswers = this.getRecordAnswers();
			for (int i=0; i<recordAnswers.size(); i++) {
				if (recordAnswers.get(i).getChoice()) {
					score++;
				}
			}
			this.score = score;
		}
	}

}

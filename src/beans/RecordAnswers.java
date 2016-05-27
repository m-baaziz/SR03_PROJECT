package beans;

public class RecordAnswers {

	private int recordId;
	private int answerId;
	private boolean choice;

	public RecordAnswers(int recordId, int answerId, boolean choice) {
		this.recordId = recordId;
		this.answerId = answerId;
		this.choice = choice;
	}
	
	public RecordAnswers() {
		
	}

	public RecordAnswers(int answerId, boolean choice) {
		this.answerId = answerId;
		this.choice = choice;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public boolean getChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}
	
}

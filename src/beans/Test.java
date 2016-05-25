package beans;

public class Test {
	private String subject;
	private boolean isActive;
	
	public Test(){
		this.setSubject("");
		this.activate();		
	}
	
	public Test(String subject) throws Exception {
		if (this.setSubject(subject)){
			this.activate();
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
}

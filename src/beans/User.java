package beans;

public class User {
	
	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	
	public boolean setEmail(String email) {
		this.email = email;
		return true;
	}
	public boolean setPassword(String password) {
		this.password = password;
		return true;
	}
}

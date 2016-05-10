package beans;

import java.sql.Date;

public class User {
	
	private String email;
	private String password;
	private String type;
	private Date creationDate;
	private String name;
	private String company;
	private String phone;
	private boolean isActive;
	
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getType() {
		return type;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public String getName() {
		return name;
	}
	public String getCompany() {
		return company;
	}
	public boolean isActive() {
		return isActive;
	}
	
	public boolean setEmail(String email) {
		this.email = email;
		return true;
	}
	public boolean setPassword(String password) {
		this.password = password;
		return true;
	}
	public boolean setType(String type) {
		if (type == "administrator" || type == "intern") {
			this.type = type;
			return true;
		} else {
			return false;
		}
	}
}

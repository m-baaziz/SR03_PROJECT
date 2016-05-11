package beans;

import java.sql.Date;
import java.util.Calendar;

public class User {
	
	private String email;
	private String password;
	private String type;
	private Date creationDate; // database ceationDate is initialized when the user is inserted
	private String name;
	private String company;
	private Integer phone;
	private boolean isActive;
	
	public User() {
		this.creationDate = new Date(Calendar.getInstance().getTime().getTime());
		this.activate();
	}
	public User(String email, String password, String type, Date creationDate, String name, String company, Integer phone) throws Exception {
		if (this.setEmail(email) && this.setPassword(password) && this.setType(type) 
				&& this.setName(name) && this.setCompany(company) && this.setPhone(phone)) {
			this.creationDate = creationDate;
			this.activate();
		} else {
			throw new Exception("Invalid parameters");
		}
	}
	
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
	public Integer getPhone() {
		return phone;
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
	public boolean setName(String name) {
		this.name = name;
		return true;
	}
	public boolean setCompany(String company) {
		this.company = company;
		return true;
	}
	public boolean setPhone(Integer phone) {
		this.phone = phone;
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

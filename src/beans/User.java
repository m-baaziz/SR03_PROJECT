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
	private String phone;
	private boolean isActive;
	
	public User() {
		this.creationDate = new Date(Calendar.getInstance().getTime().getTime());
		this.setEmail("");
		this.setCompany("");
		this.setName("");
		this.setPhone("");
		this.setType("intern");
		this.activate();
	}
	public User(String email, String password, String type, Date creationDate, String name, String company, String phone) throws Exception {
		if (this.setEmail(email) && this.setPassword(password) && this.setType(type) 
				&& this.setName(name) && this.setCompany(company) && this.setPhone(phone)) {
			this.setCreationDate(creationDate);
			this.activate();
		} else {
			throw new Exception("Invalid parameters");
		}
	}
	public User(String email, String password, String type, String name) throws Exception {
		if (this.setEmail(email) && this.setPassword(password) && this.setType(type) && this.setName(name)) {
			this.setCompany("");
			this.setPhone("");
			this.creationDate = new Date(Calendar.getInstance().getTime().getTime());
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
	public String getPhone() {
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
		if (password.length() >= 6) {
			this.password = password;
			return true;
		}
		return false;
	}
	public boolean setType(String type) {
		if (type.equals("administrator") || type.equals("intern")) {
			this.type = type;
			return true;
		} else {
			return false;
		}
	}
	public boolean setCreationDate(Date date) {
		this.creationDate = date;
		return true;
	}
	public boolean setName(String name) {
		this.name = name;
		return true;
	}
	public boolean setCompany(String company) {
		this.company = company;
		return true;
	}
	public boolean setPhone(String phone) {
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
	
	public boolean isAdmin() {
		return this.getType().equals("administrator");
	}
	public boolean matchPassword(String password) {
		return this.getPassword().equals(password);
	}
}

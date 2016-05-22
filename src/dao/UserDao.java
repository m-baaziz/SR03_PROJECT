package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import utils.Db;

public class UserDao {
	
	private Connection connection;
	private static final int USERS_PER_PAGE = 3;
	
	public class Pagination {
		public List<User> users;
		public int pageCount;
		
		public Pagination() {
			this.users = new ArrayList<User>();
			this.pageCount = 0;
		}
	}
	
	public UserDao() {
		connection = Db.getConnection();
	}
	
	public int usersCount() {
		try {
			String sqlQuery = "SELECT COUNT(email) AS count FROM user";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public boolean addUser(User user) {
		try {
			String sqlQuery = "INSERT INTO user (email, password, type, name, company, phone, isActive) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getType());
			preparedStatement.setString(4, user.getName());
			preparedStatement.setString(5, user.getCompany());
			preparedStatement.setString(6, user.getPhone());
			preparedStatement.setBoolean(7, user.isActive());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateUser(User user) {
		try {
			String sqlQuery = "UPDATE user SET name=?, type=?, company=?, phone=?, isActive=? "
					+ "WHERE email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getType());
			preparedStatement.setString(3, user.getCompany());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setBoolean(5, user.isActive());
			preparedStatement.setString(6, user.getEmail());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User getUserByEmail(String email) {
		User user = new User();
		try {
			String sqlQuery = "SELECT * FROM user WHERE email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setType(rs.getString("type"));
				user.setCreationDate(rs.getDate("creationDate"));
				user.setName(rs.getString("name"));
				user.setCompany(rs.getString("company"));
				user.setPhone(rs.getString("phone"));
				if (rs.getBoolean("isActive")) {
					user.activate();
				} else {
					user.desactivate();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			String sqlQuery = "SELECT * FROM user";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				User tmp = new User(rs.getString("email"), rs.getString("password"), rs.getString("type"), 
						rs.getDate("creationDate"), rs.getString("name"), rs.getString("company"), rs.getString("phone"));
				users.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public Pagination getAllUsers(int page, String userEmail) { // userEmail : user not to be shown
		Pagination pagination = new Pagination();
		try {
			String sqlQuery = "SELECT * FROM user WHERE NOT email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, userEmail);
			ResultSet rs = preparedStatement.executeQuery();
			int index = 0;
			pagination.pageCount = (int) Math.ceil(((double) this.usersCount() - 1)/USERS_PER_PAGE);
			if (page < 1 || page > pagination.pageCount) {page = 1;}
			int startIndex = USERS_PER_PAGE*(page - 1);
			int lastIndex = USERS_PER_PAGE*(page) - 1;
			while (rs.next() && index <= lastIndex) {
				if (index >= startIndex && index <= lastIndex) {
					User tmp = new User(rs.getString("email"), rs.getString("password"), rs.getString("type"), 
							rs.getDate("creationDate"), rs.getString("name"), rs.getString("company"), rs.getString("phone"));
					pagination.users.add(tmp);
				}
				index++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	public boolean deleteUser(String email) {
		try {
			String sqlQuery = "DELETE FROM user WHERE email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, email);
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}

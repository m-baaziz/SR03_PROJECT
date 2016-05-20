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
	
	public UserDao() {
		connection = Db.getConnection();
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
			System.out.println("dans catch");
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

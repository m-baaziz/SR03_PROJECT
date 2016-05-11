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
			String sqlQuery = "INSERT INTO USER ('email', 'password', 'type', 'name', 'company', 'phone', 'isActive') "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getType());
			preparedStatement.setString(4, user.getName());
			preparedStatement.setString(5, user.getCompany());
			preparedStatement.setInt(6, user.getPhone());
			preparedStatement.setBoolean(7, user.isActive());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User getUserByEmail(String email) {
		try {
			String sqlQuery = "SELECT * FROM users WHERE email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				User user = new User(rs.getString("email"), rs.getString("password"), rs.getString("type"), 
						rs.getDate("creationDate"), rs.getString("name"), rs.getString("company"), new Integer(rs.getInt("phone")));
				return user;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			String sqlQuery = "SELECT * FROM users";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				User tmp = new User(rs.getString("email"), rs.getString("password"), rs.getString("type"), 
						rs.getDate("creationDate"), rs.getString("name"), rs.getString("company"), new Integer(rs.getInt("phone")));
				users.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return users;
	}
}

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
			String sqlQuery = "INSERT INTO USER ('email', 'password') VALUES (?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
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
			String sqlQuery = "SELECT * FROM users WHERE email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				user.setEmail(rs.getString("email"));
				user.setPassword((rs.getString("password")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			String sqlQuery = "SELECT * FROM users";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				User tmp = new User();
				tmp.setEmail(rs.getString("email"));
				tmp.setPassword((rs.getString("password")));
				users.add(tmp);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}

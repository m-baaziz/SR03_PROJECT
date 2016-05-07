package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.User;
import utils.Db;

public class UserDao {
	
	private Connection connection;
	
	public UserDao() {
		connection = Db.getConnection();
	}
	
	public boolean addUser(User user) {
		try {
			String sqlQuery = "INSERT INTO USERS ('email', 'password') VALUES (?, ?)";
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
}

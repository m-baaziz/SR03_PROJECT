package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Db;
import beans.Question;
import beans.Test;

public class TestDao {
	private Connection connection;
	
	public TestDao() {
		connection = Db.getConnection();
	}
	
	public boolean addTest(Test test){
		try{
			String sqlQuery = "INSERT INTO test (subject, isActive) "
					+ "VALUES (?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);	
			preparedStatement.setString(1, test.getSubject());
			preparedStatement.setBoolean(2, test.isActive());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateTest(Test test){
		try{
			String sqlQuery = "UPDATE test SET subject = ?, isActive = ? "+"WHERE subject = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);	
			preparedStatement.setString(1, test.getSubject());
			preparedStatement.setBoolean(2, test.isActive());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteTest(Test test){
		try{
			String sqlQuery = "DELETE FROM test" + "WHERE subject = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(0,test.getSubject());
			ResultSet rs = preparedStatement.executeQuery();
			return true;
		}catch(Exception e) {
			System.out.println("dans catch");
			e.printStackTrace();
			return false;
		}
	}
	
	public Test getTestBySubject(String subject){
		Test test = new Test();
		try{
			String sqlQuery = "SELECT * FROM test WHERE subject=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, subject);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {				
				test.setSubject(rs.getString("subject"));
				}
		}catch(Exception e) {
			System.out.println("dans catch");
			e.printStackTrace();
		}
		return test;
	}
	
	public List<Test> getAllTests() {
		List<Test> tests = new ArrayList<Test>();
		try {
			String sqlQuery = "SELECT * FROM Test";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Test tmp = new Test(rs.getString("subject"));
				tests.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tests;
	}
}

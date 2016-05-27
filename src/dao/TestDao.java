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
	private QuestionDao questionDao;
	
	public TestDao() {
		connection = Db.getConnection();
		questionDao = new QuestionDao();
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
			String sqlQuery = "UPDATE test SET isActive = ? WHERE subject = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);	
			preparedStatement.setBoolean(1, test.isActive());
			preparedStatement.setString(2, test.getSubject());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteTest(String subject){
		try{
			String sqlQuery = "DELETE FROM test WHERE subject=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, subject);
			preparedStatement.executeUpdate();
			return true;
		}catch(Exception e) {
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
				if (rs.getBoolean("isActive")) {
					test.activate();
				} else {
					test.desactivate();
				}
				List<Question> questions = questionDao.getQuestionsBySubject(rs.getString("subject"));
				test.addQuestions(questions);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return test;
	}
	
	public List<Test> getAllTests() {
		List<Test> tests = new ArrayList<Test>();
		try {
			String sqlQuery = "SELECT * FROM test";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String subject = rs.getString("subject");
				Test tmp = new Test(subject, rs.getBoolean("isActive"));
				List<Question> questions = questionDao.getQuestionsBySubject(subject);
				tmp.addQuestions(questions);
				tests.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tests;
	}
	
	public List<Test> getAllActiveTests() {
		List<Test> tests = new ArrayList<Test>();
		try {
			String sqlQuery = "SELECT * FROM test WHERE isActive=true";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String subject = rs.getString("subject");
				Test tmp = new Test(subject, true);
				List<Question> questions = questionDao.getQuestionsBySubject(subject);
				tmp.addQuestions(questions);
				tests.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tests;
	}
}

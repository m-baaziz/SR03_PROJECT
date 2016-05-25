package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Db;
import beans.Question;
import beans.User;

public class QuestionDao {
	
	private Connection connection;
	
	public QuestionDao() {
		connection = Db.getConnection();
	}
	
	public boolean addQuestion(Question question){
		try{
			String sqlQuery = "INSERT INTO question (questionId, subject, questionText, isActive) "
					+ "VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, question.getQuestionId());
			preparedStatement.setString(2, question.getSubject());
			preparedStatement.setString(3, question.getQuestionText());
			preparedStatement.setBoolean(4, question.isActive());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateQuestion(Question question){
		try{
			String sqlQuery = "UPDATE question SET subject = ?, questionText = ?, isActive = ? "+"WHERE questionId = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);		
			preparedStatement.setString(1, question.getSubject());
			preparedStatement.setString(2, question.getQuestionText());
			preparedStatement.setBoolean(3, question.isActive());
			preparedStatement.setInt(4, question.getQuestionId());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteQuestion(Question question){
		try{
			String sqlQuery = "DELETE FROM question" + "WHERE questionId = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1,question.getQuestionId());
			ResultSet rs = preparedStatement.executeQuery();
			return true;
		}catch(Exception e) {
			System.out.println("dans catch");
			e.printStackTrace();
			return false;
		}
	}
	
	public Question getQuestionById(int questionId) {
		Question question = new Question();
		try {
			String sqlQuery = "SELECT * FROM question WHERE id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1,question.getQuestionId());
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
			question.setQuestionText(rs.getString("questionText"));
			question.setSubject(rs.getString("subject"));
			}
		} catch(Exception e) {
			System.out.println("dans catch");
			e.printStackTrace();
		}
		return question;
	}
	
	public List<Question> getQuestionsBySubject(String subject) {
		List<Question> questions = new ArrayList<Question>();
		try {
			String sqlQuery = "SELECT * FROM question WHERE subject=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1,subject);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Question tmp = new Question(rs.getInt("questionId"), rs.getString("subject"), rs.getString("questionText"));
				questions.add(tmp);
			}
		} catch(Exception e) {
			System.out.println("dans catch");
			e.printStackTrace();
		}
		return questions;
	}
	
	public List<Question> getAllQuestions() {
		List<Question> questions = new ArrayList<Question>();
		try {
			String sqlQuery = "SELECT * FROM question";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Question tmp = new Question(rs.getInt("questionId"), rs.getString("subject"), rs.getString("questionText"));
				questions.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return questions;
	}
}

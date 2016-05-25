package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Db;
import beans.Question;
import beans.Answer;

public class AnswerDao {
	private Connection connection;
	
	public AnswerDao {
		connection = Db.getConnection();
	}
	
	public boolean addAnswer(Answer answer){
		try {
			String sqlQuery = "INSERT INTO answer (answerId, questionId, answerText, isActive, isTrue) "
					+ "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, answer.getAnswerId());
			preparedStatement.setInt(2, answer.getQuestionId());		
			preparedStatement.setString(3, answer.getAnswerText());
			preparedStatement.setBoolean(5, answer.answerTrue());
			preparedStatement.setBoolean(5, answer.isTrue());
			preparedStatement.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateAnswer(Answer answer){
		try{
			String sqlQuery = "UPDATE Answer SET answerText = ?, isActive = ?, isTrue = ? "+"WHERE answerId = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);		
			preparedStatement.setString(1, answer.getAnswerText());
			preparedStatement.setBoolean(2, answer.active());
			preparedStatement.setBoolean(3, answer.isTrue());
			preparedStatement.setInt(4, answer.getAnswerId());
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteAnswer(Answer answer){
		try{
			String sqlQuery = "DELETE FROM Answer" + "WHERE answerId = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt("answerId");
			ResultSet rs = preparedStatement.executeQuery();
			return true;
		}catch(Exception e) {
			System.out.println("dans catch");
			e.printStackTrace();
			return false;
		}
	}
	
	public Answer getAnswerById(int answerId) {
		Answer answer = new Answer();
		try {
			String sqlQuery = "SELECT * FROM Answer WHERE id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(answerId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				answer.setQuestionText(rs.getString("answerText"));			
			}
		} catch(Exception e) {
			System.out.println("dans catch");
			e.printStackTrace();
		}
		return answer;
	}
	
	public List<Answer> getAllAnswersByQuestion(int questionId){
		Answer answer = new Answer();
		try{
			String sqlQuery = "SELECT * FROM Answer WHERE questionId = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(questionId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Answer tmp = new Answer(rs.getString("subject"));
				answer.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return answers;	
	}
	
	public deleteAnswer(int answerId){
		try {
			String sqlQuery = "DELETE FROM answer WHERE answerid=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, answerId);
			preparedStatement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}

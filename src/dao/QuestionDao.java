package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import beans.Answer;
import beans.Question;
import utils.Db;

public class QuestionDao {
	
	private Connection connection;
	private AnswerDao answerDao;
	
	public QuestionDao() {
		connection = Db.getConnection();
		answerDao = new AnswerDao();
	}
	
	public boolean addQuestion(Question question){
		try{
			String sqlQuery = "INSERT INTO question (subject, questionText, isActive) "
					+ "VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, question.getSubject());
			preparedStatement.setString(2, question.getQuestionText());
			preparedStatement.setBoolean(3, question.isActive());
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (question.hasAnswer() && generatedKeys.next()) {
				List<Answer> answers = question.getAnswers();
				for (int i=0; i<answers.size(); i++) {
					Answer answer = answers.get(i);
					answer.setQuestionId(generatedKeys.getInt(1));
					answerDao.addAnswer(answer);
				}
			}
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
	
	public boolean deleteQuestion(int id){
		try{
			String sqlQuery = "DELETE FROM question WHERE questionId=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Question getQuestionById(int questionId) {
		Question question = new Question();
		try {
			String sqlQuery = "SELECT * FROM question WHERE questionId=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1,questionId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				question.setQuestionText(rs.getString("questionText"));
				question.setSubject(rs.getString("subject"));
				List<Answer> answers = answerDao.getAllAnswersByQuestion(questionId);
				question.addAnswers(answers);
			}
		} catch(Exception e) {
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

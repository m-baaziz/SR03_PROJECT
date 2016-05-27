package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Answer;
import beans.Question;
import beans.Test;
import beans.User;
import dao.QuestionDao;

public class QuestionController extends HttpServlet {

	private QuestionDao dao;
	
	public QuestionController(){
		super();
		dao = new QuestionDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("question/list.jsp");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		int questionId = Integer.parseInt(request.getParameter("id"));
		if (currentUser == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if (request.getParameter("action") != null){
//				if (request.getParameter("action").equals("edit")){
//					view = request.getRequestDispatcher("question/edit.jsp");
//					view.forward(request, response);
//					return;
//				}
				if (request.getParameter("action").equals("show")){
					if (questionId >= 0 && currentUser.isAdmin()) {
						Question question = dao.getQuestionById(questionId);
						request.setAttribute("question", question);
						view = request.getRequestDispatcher("question/show.jsp");
					}
					view.forward(request, response);
					return;
				}
				if (request.getParameter("action").equals("delete")) {
					if (questionId >= 0 && currentUser.isAdmin()) {
						dao.deleteQuestion(questionId);
					}
					Test test = (Test) request.getSession().getAttribute("test");
					response.sendRedirect("test?action=show&subject="+test.getSubject());
					return;
				}
			}
			request.setAttribute("questions", dao.getAllQuestions());
			view.forward(request, response);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String questionText = (String) request.getParameter("questionText");
		String subject = (String) request.getParameter("subject");
		String answer1 = (String) request.getParameter("answer1");
		String answer2 = (String) request.getParameter("answer2");
		String answer3 = (String) request.getParameter("answer3");
		int trueAnswerId = Integer.parseInt(request.getParameter("trueQuestion"));
		try{
			if (request.getParameter("_method") != null && request.getParameter("_method").equals("put")){
				Question currentQuestion = (Question) request.getSession().getAttribute("currentQuestion");
				currentQuestion.setQuestionText(request.getParameter("questionText"));
				currentQuestion.setSubject(request.getParameter("subject"));
				boolean isActive = Boolean.valueOf(request.getParameter("isActive"));
				if (isActive) {
					currentQuestion.activate();
				} else {
					currentQuestion.desactivate();
				}
				dao.updateQuestion(currentQuestion);
				response.sendRedirect("question?action=show");
				return;
			}
			if (questionText != null && subject != null && !questionText.isEmpty() && !subject.isEmpty()) {
				Question question = new Question(questionText, subject);
				List<Answer> answerList = new ArrayList<Answer>();
				if (answer1 != null && !answer1.isEmpty()) {
					Answer tmp = new Answer(answer1, trueAnswerId == 1);
					answerList.add(tmp);
				}
				if (answer2 != null && !answer2.isEmpty()) {
					Answer tmp = new Answer(answer2, trueAnswerId == 2);
					answerList.add(tmp);
				}
				if (answer3 != null && !answer3.isEmpty()) {
					Answer tmp = new Answer(answer3, trueAnswerId == 3);
					answerList.add(tmp);
				}
				question.addAnswers(answerList);
				dao.addQuestion(question);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		response.sendRedirect("test?subject="+subject+"&action=show");
	}
	
}

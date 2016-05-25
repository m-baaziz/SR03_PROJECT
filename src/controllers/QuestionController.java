package controllers;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Question;
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
		if (currentUser == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if (request.getParameter("action") != null){
				if (request.getParameter("action").equals("create")){
					view = request.getRequestDispatcher("question/create.jsp");
					view.forward(request, response);
					return;
				}
				if (request.getParameter("action").equals("edit")){
					view = request.getRequestDispatcher("question/edit.jsp");
					view.forward(request, response);
					return;
				}
				if (request.getParameter("action").equals("show")){
					view = request.getRequestDispatcher("question/show.jsp");
					view.forward(request, response);
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
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
}

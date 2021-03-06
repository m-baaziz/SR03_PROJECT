package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Answer;
import beans.User;
import dao.AnswerDao;


public class AnswerController extends HttpServlet {
	private AnswerDao dao;
	
	public AnswerController(){
		super();
		dao = new AnswerDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("answer/list.jsp");
		int questionId = (int) request.getAttribute("id");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (currentUser == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if (request.getParameter("action")!= null){
				if (request.getParameter("action").equals("create")){
					view = request.getRequestDispatcher("answer/create.jsp");
					view.forward(request, response);
					return;
				}
				if (request.getParameter("action").equals("edit")){
					view = request.getRequestDispatcher("answer/edit.jsp");
					view.forward(request, response);
					return;
				}
				if (request.getParameter("action").equals("show")){
					view = request.getRequestDispatcher("answer/show.jsp");
					view.forward(request, response);
					return;
				}
			}
			request.setAttribute("answers", dao.getAllAnswersByQuestion(questionId));
			view.forward(request, response);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			if (request.getParameter("_method") != null && request.getParameter("_method").equals("put")){
				Answer currentAnswer= (Answer) request.getSession().getAttribute("currentAnswer");
				currentAnswer.setAnswerText(request.getParameter("questionText"));
				boolean isActive = Boolean.valueOf(request.getParameter("isActive"));
				if (isActive) {
					currentAnswer.activate();
				} else {
					currentAnswer.desactivate();
				}
				dao.updateAnswer(currentAnswer);
				response.sendRedirect("question?action=show");
				return;
			}			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

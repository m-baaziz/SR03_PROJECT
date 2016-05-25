package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Question;
import dao.AnswerDao;


public class AnswerController {
	private AnswerDao dao;
	
	public AnswerController(){
		super();
		dao = new AnswerDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("answer/list.jsp");
		try {
			if (request.getParameter("action")!= null){
				if (request.getParameter("action").equals("create")){
					view = request.getRequestDispatcher("answer/create.js");
					view.forward(request, response);
				}
				if (request.getParameter("action").equals("edit")){
					view = request.getRequestDispatcher("answer/edit.js");
					view.forward(request, response);
				}
				if (request.getParameter("action").equals("show")){
					view = request.getRequestDispatcher("answer/show.js");
					view.forward(request, response);
				}
			}
			request.setAttribute("answers", dao.getAllAnswersByQuestion());
			view.forward(request, response);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			if (request.getParameter("_method") != null && request.getParameter("_method").equals("put")){
				Question currentAnswer= (Question) request.getSession().getAttribute("currentAnswer");
				currentAnswer.setQuestionText(request.getParameter("questionText"));
				currentAnswer.setSubject(request.getParameter("subject"));
				boolean isActive = Boolean.valueOf(request.getParameter("isActive"));
				if (isActive) {
					currentAnswer.activate();
				} else {
					currentAnswer.desactivate();
				}
				dao.updateQuestion(currentAnswer);
				response.sendRedirect("question?action=show");
				return;
			}			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Test;
import beans.User;
import dao.TestDao;

public class TestController extends HttpServlet {
	private TestDao dao;
	
	public TestController(){
		super();
		dao = new TestDao();
	}
	
	protected void redirectWithTest(HttpServletRequest request, HttpServletResponse response, String toPath, String onErrorPath) {
		try {
			String subject = (String) request.getParameter("subject");
			if (subject != null && !subject.isEmpty()) {
				RequestDispatcher view = request.getRequestDispatcher(toPath);
				Test test = dao.getTestBySubject(subject);
				HttpSession session = request.getSession(true);
				session.setAttribute("test", test);
				session.setAttribute("questions", test.getQuestions());
				view.forward(request, response);
			} else {
				response.sendRedirect(onErrorPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("test/index.jsp");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (currentUser == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if (request.getParameter("action") != null){
				if (request.getParameter("action").equals("edit")){
					String subject = (String) request.getParameter("subject");
					redirectWithTest(request, response, "test/edit.jsp", "test?subject="+subject+"&action=show");
					return;
				}
				if (request.getParameter("action").equals("show")){
					redirectWithTest(request, response, "test/show.jsp", "test");
					return;
				}
				if (request.getParameter("action").equals("delete")){
					String subject = (String) request.getParameter("subject");
					if (currentUser.isAdmin() && subject != null && !subject.isEmpty()) {
						dao.deleteTest(subject);
					}
					response.sendRedirect("test");
					return;
				}
			}
			if (currentUser.isAdmin()) {
				request.setAttribute("tests", dao.getAllTests());
			} else {
				request.setAttribute("tests", dao.getAllActiveTests());
			}
			view.forward(request, response);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subject = (String) request.getParameter("subject");
		try{
			if (subject != null && !subject.isEmpty()) {
				// PUT
				if (request.getParameter("_method") != null && request.getParameter("_method").equals("put")){
					Test test = dao.getTestBySubject(subject);	
					boolean isActive = Boolean.valueOf(request.getParameter("isActive"));
					if (isActive) {
						test.activate();
					} else {
						test.desactivate();
					}
					dao.updateTest(test);
					response.sendRedirect("test?subject="+subject+"&action=show");
					return;
				}
				// POST
				Test test = new Test(subject);
				dao.addTest(test);
				response.sendRedirect("test?subject="+subject+"&action=show");
				return;
			} else {
				response.sendRedirect("test");
				return;
			}
		}catch (Exception e){
			e.printStackTrace();
			response.sendRedirect("test");
		}
		
	}
}

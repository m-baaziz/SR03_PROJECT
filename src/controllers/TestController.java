package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Test;
import dao.TestDao;

public class TestController {
	private TestDao dao;
	
	public TestController(){
		super();
		dao = new TestDao();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("test/list.jsp");
		try {
			if (request.getParameter("action") != null){
				if (request.getParameter("action").equals("create")){
					view = request.getRequestDispatcher("test/create.js");
					view.forward(request, response);
				}
				if (request.getParameter("action").equals("edit")){
					view = request.getRequestDispatcher("test/edit.js");
					view.forward(request, response);
				}
				if (request.getParameter("action").equals("show")){
					view = request.getRequestDispatcher("test/show.js");
					view.forward(request, response);
				}
			}
			request.setAttribute("tests", dao.getAllTests());
			view.forward(request, response);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			if (request.getParameter("_method") != null && request.getParameter("_method").equals("put")){
				Test currentTest = (Test) request.getSession().getAttribute("currentTest");			
				currentTest.setSubject(request.getParameter("subject"));
				boolean isActive = Boolean.valueOf(request.getParameter("isActive"));
				if (isActive) {
					currentTest.activate();
				} else {
					currentTest.desactivate();
				}
				dao.updateTest(currentTest);
				response.sendRedirect("test?action=show");
				return;
			}			
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}

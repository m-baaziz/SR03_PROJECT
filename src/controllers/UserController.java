package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.UserDao;

public class UserController extends HttpServlet {
	
	private UserDao dao;
	
	public UserController() {
		super();
		dao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("users/index.jsp");
		if (request.getSession().getAttribute("currentUser") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if (request.getParameter("action") != null && request.getParameter("action").equals("logout")) {
				request.getSession().removeAttribute("currentUser");
				response.sendRedirect("index.jsp");
				return;
			}
			request.setAttribute("users", dao.getAllUsers());
		} catch (Exception e) {
			e.printStackTrace();
		}
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = new User(request.getParameter("email"), request.getParameter("password"), request.getParameter("type"), request.getParameter("name"));
			dao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("users", dao.getAllUsers());
		RequestDispatcher view = request.getRequestDispatcher("users/index.jsp");
		view.forward(request, response);
	}
}

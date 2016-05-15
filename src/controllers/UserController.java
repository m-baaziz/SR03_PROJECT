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
		RequestDispatcher view = request.getRequestDispatcher("user/index.jsp");
		if (request.getSession().getAttribute("currentUser") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if (request.getParameter("action") != null) {
				if (request.getParameter("action").equals("logout")) {
					request.getSession().removeAttribute("currentUser");
					response.sendRedirect("index.jsp");
					return;
				}
				if (request.getParameter("action").equals("show")) {
					view = request.getRequestDispatcher("user/show.jsp");
					view.forward(request, response);
					return;
				}
			}
			request.setAttribute("users", dao.getAllUsers());
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = new User(request.getParameter("email"), request.getParameter("password"), request.getParameter("type"), request.getParameter("name"));
			dao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("users", dao.getAllUsers());
		RequestDispatcher view = request.getRequestDispatcher("user/index.jsp");
		view.forward(request, response);
	}
}

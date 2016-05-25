package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.UserDao;

public class AuthenticationController extends HttpServlet {
	
	private UserDao dao;
	
	public AuthenticationController() {
		super();
		dao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (currentUser != null) {
			response.sendRedirect("user");
			return;
		}
		request.getRequestDispatcher("authentication/index.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		List<String> errors = new ArrayList<String>();
		try {
			User currentUser = dao.getUserByEmail(email);
			if (currentUser != null && !password.isEmpty() && currentUser.matchPassword(password)) {
				if (currentUser.isActive()) {
					HttpSession session = request.getSession(true);
					session.setAttribute("currentUser", currentUser);
				} else {
					errors.add("User is not active, please ask an admin (sr03project@outlook.com) to activate your account");
				}
			} else {
				errors.add("Invalid credentials");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("errors", errors);
		if (errors.isEmpty()) {
			response.sendRedirect("user");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("authentication/index.jsp");
			view.forward(request, response);
		}
	}
}

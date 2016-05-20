package controllers;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.UserDao;
import utils.Mailer;

public class UserController extends HttpServlet {
	
	private UserDao dao;
	
	public UserController() {
		super();
		dao = new UserDao();
	}
	
	private User getUserToProcess(HttpServletRequest request) throws Exception {
		User userToShow = (User) request.getSession().getAttribute("currentUser");
		if (request.getParameter("email") != null  && !request.getParameter("email").isEmpty()) {
			if (!userToShow.isAdmin() && !userToShow.getEmail().equals(request.getParameter("email"))) {
				throw new Exception("access forbiden");
			}
			userToShow = dao.getUserByEmail(request.getParameter("email"));
		}
		return userToShow;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("user/index.jsp");
		if (request.getSession().getAttribute("currentUser") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if (request.getParameter("action") != null) {
				try{
					if (request.getParameter("action").equals("logout")) {
						request.getSession().removeAttribute("currentUser");
						response.sendRedirect("index.jsp");
						return;
					}
					if (request.getParameter("action").equals("show")) {
						User userToShow = getUserToProcess(request);
						view = request.getRequestDispatcher("user/show.jsp");
						request.setAttribute("user", userToShow);
						view.forward(request, response);
						return;
					}
					if (request.getParameter("action").equals("edit")) {
						User userToShow = getUserToProcess(request);
						view = request.getRequestDispatcher("user/edit.jsp");
						request.setAttribute("user", userToShow);
						view.forward(request, response);
						return;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					response.sendRedirect("user");
					return;
				}
			}
			request.setAttribute("users", dao.getAllUsers());
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("index.jsp");
			return;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			//  ------ PUT METHODS -------------
			
			if (request.getParameter("_method") != null && request.getParameter("_method").equals("put")) {
				User currentUser = (User) request.getSession().getAttribute("currentUser");
				currentUser.setName(request.getParameter("name"));
				currentUser.setType(request.getParameter("type"));
				currentUser.setCompany(request.getParameter("company"));
				currentUser.setPhone(request.getParameter("phone"));
				boolean isActive = Boolean.valueOf(request.getParameter("isActive"));
				if (isActive) {
					currentUser.activate();
				} else {
					currentUser.desactivate();
				}
				dao.updateUser(currentUser);
				response.sendRedirect("user?action=show");
				return;
			}
			
			//  ------ POST METHODS -------------
			if (request.getParameter("email") != null && !request.getParameter("email").isEmpty()) {
				User user = new User(request.getParameter("email"), request.getParameter("type"), request.getParameter("name"));
				if (dao.addUser(user)) {
					String body = "Dear " + request.getParameter("name") + ","
									+ "\n\n " + "A new sr03-project account has been created for your, "
									+ "here is your account credentials : \n\n"
									+ "   email : " + user.getEmail() + "\n"
									+ "   password : " + user.getPassword();
					Mailer.sendEmail(request.getParameter("email"), "SR03-project credentials", body);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("users", dao.getAllUsers());
		RequestDispatcher view = request.getRequestDispatcher("user/index.jsp");
		view.forward(request, response);
	}
}

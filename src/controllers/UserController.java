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
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (currentUser == null) {
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
					if (request.getParameter("action").equals("administrate") && currentUser.isAdmin()) {
						int page = 1;
						if (request.getParameter("page") != null) {
							try {
								page = Integer.parseInt(request.getParameter("page"));
							} catch (Exception e) {
								page = 1;
							}
						}
						UserDao.Pagination pagination = dao.getAllUsers(page, currentUser.getEmail());
						request.setAttribute("pageCount", pagination.pageCount);
						request.setAttribute("users", pagination.users);
						view = request.getRequestDispatcher("user/list.jsp");
						view.forward(request, response);
						return;
					}
					User user = getUserToProcess(request);
					if (request.getParameter("action").equals("show")) {
						view = request.getRequestDispatcher("user/show.jsp");
						request.setAttribute("user", user);
						view.forward(request, response);
						return;
					}
					if (request.getParameter("action").equals("edit")) {
						view = request.getRequestDispatcher("user/edit.jsp");
						request.setAttribute("user", user);
						view.forward(request, response);
						return;
					}
					if (request.getParameter("action").equals("delete")) {
						if (currentUser.isAdmin() && !currentUser.getEmail().equals(user.getEmail())) {
							dao.deleteUser(user.getEmail());
						}
						response.sendRedirect("user?action=administrate");
						return;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					response.sendRedirect("user");
					return;
				}
			}
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
			User currentUser = (User) request.getSession().getAttribute("currentUser");
			if (request.getParameter("_method") != null && request.getParameter("_method").equals("put")) {
				User user = (User) dao.getUserByEmail(request.getParameter("email"));
				user.setName(request.getParameter("name"));
				if (currentUser.isAdmin()) {
					user.setType(request.getParameter("type"));
					boolean isActive = Boolean.valueOf(request.getParameter("isActive"));
					if (isActive) {
						user.activate();
					} else {
						user.desactivate();
					}
				}
				user.setCompany(request.getParameter("company"));
				user.setPhone(request.getParameter("phone"));
				dao.updateUser(user);
				if (currentUser.getEmail().equals(user.getEmail())) { // if the user updated is the current user, update the session variable currentUser
					request.getSession().setAttribute("currentUser", user);
				}
				response.sendRedirect("user?email=" + user.getEmail() + "action=show");
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
		response.sendRedirect("user?action=administrate");
	}
}

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
				if (request.getParameter("action").equals("edit")) {
					view = request.getRequestDispatcher("user/edit.jsp");
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
					Session session = Mailer.getSession();
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("sr03project@outlook.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(request.getParameter("email")));
					message.setSubject("SR03 password information");
					message.setText("Dear " + request.getParameter("name") + ","
						+ "\n\n " + "Here is your account credentials : \n\n"
								+ "   email : " + user.getEmail() + "\n"
								+ "   password : " + user.getPassword());

					Transport.send(message);
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

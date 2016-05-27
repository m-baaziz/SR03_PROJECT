package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Records;
import beans.User;
import dao.RecordsDao;

public class RecordsController extends HttpServlet {
	private RecordsDao dao;
	
	public RecordsController(){
		super();
		dao = new RecordsDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("record/index.jsp");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (currentUser == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		if (currentUser.isAdmin()) {
			response.sendRedirect("user");
			return;
		}
		List<Records> records = dao.getByEmail(currentUser.getEmail());
		List<String> subjects = new ArrayList<String>();
		for (int i=0; i<records.size(); i++) {
			if (!subjects.contains(records.get(i).getSubject())) {
				subjects.add(records.get(i).getSubject());
			}
		}
		List<Records> bestRecords = new ArrayList<Records>();
		for (int i=0; i<subjects.size(); i++) {
			bestRecords.add(dao.getBestRecordBySubject(subjects.get(i)));
		}
		request.setAttribute("records", records);
		request.setAttribute("bestRecords", bestRecords);
		view.forward(request, response);
	}
}
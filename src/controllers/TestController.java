package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Answer;
import beans.Question;
import beans.RecordAnswers;
import beans.Records;
import beans.Test;
import beans.User;
import dao.QuestionDao;
import dao.RecordsDao;
import dao.TestDao;

public class TestController extends HttpServlet {
	private TestDao dao;
	private QuestionDao questionDao;
	private RecordsDao recordsDao;
	
	public TestController(){
		super();
		dao = new TestDao();
		questionDao = new QuestionDao();
		recordsDao = new RecordsDao();
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
		String subject = request.getParameter("subject");
		if (currentUser == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if (request.getParameter("action") != null){
				if (request.getParameter("action").equals("edit")){
					redirectWithTest(request, response, "test/edit.jsp", "test?subject="+subject+"&action=show");
					return;
				}
				if (request.getParameter("action").equals("show")){
					redirectWithTest(request, response, "test/show.jsp", "test");
					return;
				}
				if (request.getParameter("action").equals("delete")){
					if (currentUser.isAdmin() && subject != null && !subject.isEmpty()) {
						dao.deleteTest(subject);
					}
					response.sendRedirect("test");
					return;
				}
			}
			if (!currentUser.isAdmin() && request.getParameter("action") == null && subject != null && !subject.isEmpty()){
				HttpSession session = request.getSession(true);
				int step = 0;
				if (session.getAttribute("step") != null) {
					step = (int) session.getAttribute("step") + 1;
				} else {
					Records records = new Records(currentUser.getEmail(), subject);
					records.start();
					session.setAttribute("records", records);
				}
				session.setAttribute("step", step);
				Test test = dao.getTestBySubject(subject);
				List<Question> questions = test.getActiveQuestions();
				if (step >= questions.size()) {
					session.removeAttribute("step");
					if (session.getAttribute("records") != null) {
						Records records = (Records) session.getAttribute("records");
						records.finish();
						recordsDao.insertWithRecordAnswers(records);
					}
					response.sendRedirect("test");
					return;
				}
				Question question = questions.get(0);
				if (step > 0) {
					question = questions.get(step);
				}
				request.setAttribute("question", question);
				view = request.getRequestDispatcher("test/step.jsp");
				view.forward(request, response);
				return;
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
			if (request.getParameter("_method") != null && request.getParameter("_method").equals("test")){
				HttpSession session = request.getSession(true);
				String questionId = request.getParameter("questionId");
				String answerNum = request.getParameter("answer");
				Question question = questionDao.getQuestionById(Integer.parseInt(questionId));
				Answer answer = question.getAnswers().get(Integer.parseInt(answerNum));
				if (session.getAttribute("records") != null) {
					Records records = (Records) session.getAttribute("records");
					RecordAnswers recordAnswer = new RecordAnswers(answer.getAnswerId(), answer.isTrue());
					records.addRecordAnswer(recordAnswer);
					session.setAttribute("records", records);
				}
				response.sendRedirect("test?subject="+question.getSubject());
				return;
			}
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

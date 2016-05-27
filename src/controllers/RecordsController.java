package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.QuestionDao;
import dao.RecordsDao;
import dao.TestDao;

public class RecordsController extends HttpServlet {
	private RecordsDao dao;
	
	public RecordsController(){
		super();
		dao = new RecordsDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>  
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  

<html>
	<head>
		<meta charset='UTF-8' />
		<title> SR03 Project Website </title>
	</head>
	<body>
		<h1> Question </h1>
		<br/>
		<a href="question?action=edit">Edit</a>
		<br/>
		<table>
			<tbody>
				<tr><td>Subject : </td><td><c:out value="${currentQuestion.getSubject()}" /></td></tr>
				<tr><td>Entitled : </td><td><c:out value="${currentQuestion.getQuestionText()}" /></td></tr>
				<tr><td>Is Active : </td><td><c:out value="${currentQuestion.isActive() ? 'True': 'False'}" /></td></tr>
			</tbody>
		</table>
		<br/>
		<a href="question">Return</a>
	</body>
</html>
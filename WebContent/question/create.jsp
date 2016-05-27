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
		<h1> Add a new question :  </h1>
		<br/>
		<br/>
		<c:if test="${sessionScope.currentUser.isAdmin()}">			
			<form method="post" action="question">
				<div>Entitled : <input required type="text" name="questionText" /></div>
				<div>Subject : <input required type="text" name="subject" /></div>
				<table>
					<thead>
						<tr>					
							<th>Answer</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${answer}" var="question">
							<tr>
								<td><c:out value="${answer.getAnswerText()}" /></td>
								<td><a href="answer?action=edit">Edit</a></td>
								<td><a href="answer?action=delete">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div><input type="submit" value="Send" /></div>
			</form>
		</c:if>
		<br/>
		<a href="question">Return</a>
	</body>
</html>
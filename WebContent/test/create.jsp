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
		<h1> Add a new test :  </h1>
		<br/>
		<br/>
		<c:if test="${sessionScope.currentUser.isAdmin()}">			
			<form method="post" action="test">
				<div>Subject : <input required type="text" name="subject"/></div>							
				<h3>Questions: </h3>		
				<table>
					<thead>
						<tr>					
							<th>Entitled</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${questions}" var="question">
							<tr>
								<td><c:out value="${question.getQuestionText()}" /></td>
								<td><a href="question?action=edit">Edit</a></td>
								<td><a href="question?action=delete">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>	
				<a href="question?action=create">Add a question</a>
				<div><input type="submit" value="Send" /></div>
			</form>
		</c:if>
		<br/>
		<a href="question">Return</a>
	</body>
</html>
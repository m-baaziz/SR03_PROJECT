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
		<h1>Test details</h1>
		<a href="test?subject=${test.getSubject()}&action=edit">Edit</a>
		<br/>
		<table>
			<tbody>
				<tr><td>Subject : </td><td><c:out value="${test.getSubject()}" /></td></tr>
				<tr><td>Active : </td><td><c:out value="${test.isActive() ? 'True': 'False'}" /></td></tr>
			</tbody>
		</table>			
		<br/>
		<c:if test="${test.hasQuestion()}">
			<h3>Questions: </h3>		
			<table>
				<thead>
					<tr>					
						<th>Entitled</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${questions}" var="question">
						<tr>
							<td><c:out value="${question.getQuestionText()}" /></td>
							<td><a href="question?id=${question.getQuestionId()}&action=show"> Show</a> 
							<a href="question?id=${question.getQuestionId()}&action=delete"> Remove</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>	
		</c:if>
		<h3>Add a question :</h3> 
		<br/>
		<form method="post" action="question">
			<input type="hidden" value="${test.getSubject()}" name="subject" />
			<div>Entitled : <input required type="text" name="questionText" /></div>
			<table>
				<thead>
					<th></th><th></th><th>True</th>
				</thead>
				<tbody>
					<tr><td>Answer 1 : </td><td><input type="text" name="answer1" /></td><td><input type="radio" name="trueQuestion" value="1" /></td></tr>
					<tr><td>Answer 2 : </td><td><input type="text" name="answer2" /></td><td><input type="radio" name="trueQuestion" value="2" /></td></tr>
					<tr><td>Answer 3 : </td><td><input type="text" name="answer3" /></td><td><input type="radio" name="trueQuestion" value="3" /></td></tr>
				</tbody>
			</table>
			<div><input type="submit" value="Send" /></div>
		</form>
		<br/>
		<a href="test">Back</a>
	</body>
</html>
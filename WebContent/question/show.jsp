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
		<h1> <c:out value="${question.getQuestionText()}" /> </h1>
		<br/>
		<table>
			<tbody>
				<tr><td>Is Active : </td><td><c:out value="${question.isActive() ? 'True': 'False'}" /></td></tr>
				<tr><td>Good answer : </td><td><c:out value="${question.getGoodAnswer().getAnswerText()}"></c:out></td></tr>
				<tr><td>Bad answers : </td><td></td></tr>
				<c:forEach items="${question.getBadAnswers()}" var="badAnswer">
					<tr><td></td><td><c:out value="${badAnswer.getAnswerText()}"></c:out></td></tr>
				</c:forEach>
			</tbody>
		</table>
		<br/>
		<a href="test?&subject=${question.getSubject()}&action=show">Return</a>
	</body>
</html>
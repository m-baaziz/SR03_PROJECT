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
		<h2><c:out value="${question.getQuestionText()}" /></h2>
		<br/>
		<form action="test" method="post">
			<input type="hidden" name="_method" value="test">
			<input type="hidden" name="questionId" value="${question.getQuestionId()}">
			<table border="1">
				<tbody>
					<tr>
						<c:forEach items="${question.getAnswers()}" var="answer" varStatus="loop">
							<td><c:out value="${answer.getAnswerText()}"></c:out> <input type="radio" value="${loop.index}" name="answer" /></td>
						</c:forEach>
					</tr>
				</tbody>
			</table>
			<input type="submit" value="Submit answer" />
		</form>
	</body>
</html>
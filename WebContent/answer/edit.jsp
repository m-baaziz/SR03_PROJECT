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
		<h1> Edit answer of: ${currentQuestion.getQuestionText()}   </h1>
		<br/>
		<br/>
		<c:if test="${sessionScope.currentUser.isAdmin()}">			
			<form method="post" action="user">
				<div>Answer : <input required type="text" name="subject" value="${currentAnswer.getAnswerText()}" /></div>											
				<div><input type="submit" value="Send" /></div>
			</form>
		</c:if>		
		<br/>
		<a href="question">Return</a>
	</body>
</html>
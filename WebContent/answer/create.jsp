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
		<h1> Add a new answer :  </h1>
		<br/>
		<br/>
		<c:if test="${sessionScope.currentUser.isAdmin()}">			
			<form method="post" action="answer">				
				<div>Answer : <input required type="text" name="Anwser" /></div>
				<div>Is true :
					<select name="isTrue">
						<option ${currentQuestion.isTrue() ? 'selected' : ''} value="true">True</option>
						<option ${currentQuestion.isTrue() ? '' : 'selected'}  value="false">False</option>
					</select>
				</div>
				<div>Active:
					<select name="isActive">
						<option ${currentQuestion.isActive() ? 'selected' : ''} value="true">True</option>
						<option ${currentQuestion.isActive() ? '' : 'selected'}  value="false">False</option>
					</select>
				</div>
				<div><input type="submit" value="Send" /></div>
			</form>
		</c:if>
		<br/>
		<a href="question">Return</a>
	</body>
</html>
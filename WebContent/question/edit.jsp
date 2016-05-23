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
		<h1> Question edition </h1>
		<br/>
		<br/>
		<form action="question" method="post">
			<input type="hidden" name="_method" value="put" />
			<table>
				<tbody>
					<tr><td>Entitled: </td><td><input name="name" type="text" value="${currentQuestion.getQuestionText()}" /></td></tr>					           
					<tr><td>Subject : </td><td><input name="company" type="text" value="${currentQuestion.getSubject()}" /></td></tr>               					        
					<tr><td>Active : </td>
					<td>
						<select name="isActive">
							<option ${currentQuestion.isActive() ? 'selected' : ''} value="true">True</option>
							<option ${currentQuestion.isActive() ? '' : 'selected'}  value="false">False</option>
						</select>
					</td></tr>
				</tbody>
			</table>
			<input type="submit" value="Edit" />
		</form>
		<br/>
		<a href="question?action=show">Return</a>
	</body>
</html>
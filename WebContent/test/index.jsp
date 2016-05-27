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
		<h1> Tests administration </h1>
		<table>
			<thead>
				<tr>					
					<th>Subject</th>
					<c:if test="${sessionScope.currentUser.isAdmin()}"><th>Active</th></c:if>
					<th>action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tests}" var="test">
					<tr>
						<td><c:out value="${test.getSubject()}" /></td>
						<c:if test="${sessionScope.currentUser.isAdmin()}"><td><c:out value="${test.isActive() ? 'True': 'False'}" /></td></c:if>				
						<td>
							<c:if test="${sessionScope.currentUser.isAdmin()}">
								<a href="test?subject=${test.getSubject()}&action=show"> Show</a> <a href="test?subject=${test.getSubject()}&action=delete"> Remove</a>
							</c:if>
							<c:if test="${!sessionScope.currentUser.isAdmin()}">
								<a href="test?subject=${test.getSubject()}"> Start</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>		
		<br/>
		<c:if test="${sessionScope.currentUser.isAdmin()}">
			Add a new test :
			<br/>
			<br/>			
			<form method="post" action="test">
				<div>Subject : <input required type="text" name="subject"/></div>							
				<div><input type="submit" value="Send" /></div>
			</form>
		</c:if>
		<a href="user">Back</a>		
	</body>
</html>
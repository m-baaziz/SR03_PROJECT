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
		<h1> Welcome <c:out value="${currentUser.getName()}" /> ! </h1>
		<table>
			<thead>
				<tr>
					<th>Email</th>
					<th>Password</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><c:out value="${user.getEmail()}" /></td>
						<td><c:out value="${user.getPassword()}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${sessionScope.currentUser.isAdmin()}">
			<br/>
			Add a new user : 
			<br/>
			<form method="post" action="users">
				<div>Name : <input type="text" name="name" /></div>
				<div>E-mail : <input type="text" name="email" /></div>
				<div>Password : <input type="password" name="password" /></div>
				<div>Type : 
					<select name="type">
						<option value="administrator">Administrator</option>
						<option value="intern">Intern</option>
					</select>
				</div>
				<div><input type="submit" value="Send" /></div>
			</form>
		</c:if>
		<br/>
		<a href="users?action=logout">Logout</a>
	</body>
</html>
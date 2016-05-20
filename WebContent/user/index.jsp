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
		<h1> Dashboard </h1>
		<h2> Welcome <c:out value="${currentUser.getName()}" /> ! </h1>
		<h3><a href="user?action=show">Profile</a></h2>

		<c:if test="${sessionScope.currentUser.isAdmin()}">
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Email</th>
						<th>Type</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<td><a href="user?email=${user.getEmail()}&action=show"><c:out value="${user.getName()}" /></a></td>
							<td><c:out value="${user.getEmail()}" /></td>
							<td><c:out value="${user.getType()}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<br/>
			
			Add a new user : 
			<br/>
			<form method="post" action="user">
				<div>Name : <input required type="text" name="name" /></div>
				<div>E-mail : <input required type="text" name="email" /></div>
				<div>Type : 
					<select required name="type">
						<option value="administrator">Administrator</option>
						<option value="intern">Intern</option>
					</select>
				</div>
				<div><input type="submit" value="Send" /></div>
			</form>
		</c:if>
		<br/>
		<a href="user?action=logout">Logout</a>
	</body>
</html>
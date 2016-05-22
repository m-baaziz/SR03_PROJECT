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

		<c:if test="${currentUser.isAdmin()}">
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Email</th>
						<th>Type</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<c:if test="${!user.getEmail().equals(currentUser.getEmail())}">
							<tr>
								<td><c:out value="${user.getName()}" /></td>
								<td><c:out value="${user.getEmail()}" /></td>
								<td><c:out value="${user.getType()}" /></td>
								<td><a href="user?email=${user.getEmail()}&action=show"> Show</a><a href="user?email=${user.getEmail()}&action=delete"> Remove</a></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			<c:forEach var="i" begin="1" end="${pageCount}">
				<a href="user?page=${i}"><c:out value="${i}" /></a>
			</c:forEach>
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
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
		<h1> <c:out value="${currentUser.getName()}" /> profile </h1>
		<br/>
		<br/>
		<table>
			<tbody>
				<tr><td>Name : </td><td><c:out value="${currentUser.getName()}" /></td></tr>
				<tr><td>Email : </td><td><c:out value="${currentUser.getEmail()}" /></td></tr>                   
				<tr><td>Type : </td><td><c:out value="${currentUser.getType()}" /></td></tr>                     
				<tr><td>Creation Date : </td><td><c:out value="${currentUser.getCreationDate()}" /></td></tr>    
				<tr><td>Company : </td><td><c:out value="${currentUser.getCompany()}" /></td></tr>               
				<tr><td>Phone : </td><td><c:out value="${currentUser.getPhone()}" /></td></tr>                   
				<tr><td>Active : </td><td><c:out value="${currentUser.isActive() ? 'True': 'False'}" /></td></tr>
			</tbody>
		</table>
		<br/>
		<a href="user">Return</a>
	</body>
</html>
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
		<h1> <c:out value="${user.getName()}" /> profile </h1>
		<br/>
		<a href="user?email=${user.getEmail()}&action=edit">Edit</a>
		<br/>
		<table>
			<tbody>
				<tr><td>Name : </td><td><c:out value="${user.getName()}" /></td></tr>
				<tr><td>Email : </td><td><c:out value="${user.getEmail()}" /></td></tr>                   
				<tr><td>Type : </td><td><c:out value="${user.getType()}" /></td></tr>                     
				<tr><td>Creation Date : </td><td><c:out value="${user.getCreationDate()}" /></td></tr>    
				<tr><td>Company : </td><td><c:out value="${user.getCompany()}" /></td></tr>               
				<tr><td>Phone : </td><td><c:out value="${user.getPhone()}" /></td></tr>                   
				<tr><td>Active : </td><td><c:out value="${user.isActive() ? 'True': 'False'}" /></td></tr>
			</tbody>
		</table>
		<br/>
		<a href="user?action=administrate">Back</a>
	</body>
</html>
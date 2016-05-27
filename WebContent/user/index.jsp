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
		<h2> Welcome <c:out value="${currentUser.getName()}" /> ! </h2>
		<h3><a href="user?action=show">My profile</a></h3>
		<c:if test="${currentUser.isAdmin()}">
			<h3><a href="user?action=administrate">Users</a></h3>
		</c:if>
		<h3><a href="test">Tests</a></h3>
		<c:if test="${!currentUser.isAdmin()}">
			<h3><a href="record">My Results</a></h3>
		</c:if>
		<br/>
		<a href="user?action=logout">Logout</a>
	</body>
</html>
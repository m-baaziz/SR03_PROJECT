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
		<h1> Login </h1>
		<c:forEach items="${errors}" var="error">
			adaz
			<div><c:out value="${error}" /></div>
		</c:forEach>
		<form method="post" action="authentication">
			<input type="text" name="email" />
			<input type="password" name="password" />
			<input type="submit" />
		</form>
	</body>
</html>